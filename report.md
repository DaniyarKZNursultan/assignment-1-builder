**1. Проблема читаемости и вызова (Readability & Usability Issue)**
При вызове конструктора с 11 параметрами код становится абсолютно нечитаемым. Клиентский код выглядит так:
```java
SmartHomeSystem home = new SmartHomeSystem(
    "SYS-101", "John", "192.168.1.1", "4321",
    true, false, 21.5, 4000, 60, true, netConfig
); 
```

Без подсказок IDE невозможно понять, за что отвечают подряд идущие аргументы `true, false` или числа `21.5, 4000, 60`.

**2. Высокий риск ошибок позиционирования (Type Confusion Risk)**
Наличие нескольких параметров одного и того же типа (например, подряд идущие `boolean` или `int`) легко приводит к ошибкам. Если случайно перепутать местами `enableCameras` и `enableFireAlarm`, компилятор Java не выдаст ошибку, так как оба типа — `boolean`, но бизнес-логика системы будет нарушена.

**3. Проблема комбинаторного взрыва конструкторов (Telescoping Constructor Anti-pattern)**
Если клиенту нужно задать только `systemId`, `ownerName` и `targetTemperature`, придется либо создавать новый конструктор, либо передавать множество значений по умолчанию в один гигантский конструктор. При росте количества опциональных полей количество необходимых конструкторов растет экспоненциально.


**Clean Code: Before $\rightarrow$ After**

Пример 1: Маленькие функции и один уровень абстракции (Small Functions & Single Level of Abstraction)

BEFORE:Логика сборки объекта и проверка правил находились в одном громоздком методе.
```java
Javapublic SmartHomeSystem build() {
if (systemId == null || systemId.isBlank()) throw new IllegalStateException();
if (targetTemperature < 10.0 || targetTemperature > 35.0) throw new IllegalArgumentException();
if (securityPin == null || securityPin.length() < 4) throw new IllegalArgumentException();
if (enableCameras && backupBatteryMinutes < 60) throw new IllegalStateException();
if (enableCameras && !cloudSyncEnabled) throw new IllegalStateException();
return new SmartHomeSystem(this);
}
```
AFTER:Выделен отдельный приватный метод validate(). Метод build() теперь отвечает только за координацию шагов сборки и состоит из двух понятных строк.
```Java
Javapublic SmartHomeSystem build() {
validate();
return new SmartHomeSystem(this);
}

private void validate() {
validateSingleFields();
validateCrossFields();
}
```
Объяснение:Что было не так: Метод build() выполнял две разные задачи — валидацию полей и создание объекта.Принцип Clean Code: One Level of Abstraction per Function и Single Responsibility Principle (SRP).  Почему лучше: Метод build() стал короткой высокоуровневой функцией, а низкоуровневая проверка условий вынесена в специализированный метод.


Пример 2: Отказ от флаговых аргументов (Avoiding Flag Arguments)

BEFORE:Передача boolean флагов в сеттеры или конструктор, из-за чего из вызова функции не понятен её смысл.

```Java
// Клиентский код: не понятно, что означает true и false
builder.setCameraState(true);
builder.setSync(false);
```
AFTER:Замена флаговых аргументов экспрессивными доменными методами Fluent API без параметров.
```java
Javapublic Builder withCameras() {
this.enableCameras = true;
return this;
}

public Builder enableCloudSync() {
this.cloudSyncEnabled = true;
return this;
}
```
Объяснение:Что было не так: Передача boolean в аргументы (Flag Arguments) заставляет функцию выполнять разную логику в зависимости от значения (if (flag) ... else ...), а вызов .setCameraState(true) малопонятен.  Принцип Clean Code: Flag Arguments (Глава 3: «Flag arguments are ugly. Passing a boolean into a function is a truly terrible practice»).  Почему лучше: Метод .withCameras() явно говорит о своем намерении и не требует передачи true/false.  


Пример 3: Понятные и описательные имена (Descriptive Names)

BEFORE:Использование обобщенных имен методов и параметров.
```java
Javapublic Builder setBattery(int b) {
this.backupBatteryMinutes = b;
return this;
}
```
AFTER:Использование содержательных доменных имен, указывающих единицы измерения.
```java
Javapublic Builder withBackupBattery(int minutes) {
this.backupBatteryMinutes = minutes;
return this;
}
```
Объяснение:Что было не так: Имя setBattery(int b) не дает информации о том, в чем измеряется значение (проценты, ватты, минуты, ампер-часы).Принцип Clean Code: Descriptive Naming & Use Intention-Revealing Names.  Почему лучше: Имя withBackupBattery(int minutes) сразу объясняет разработчику, что значение передается в минутах автономной работы.





**Design Decision: Вынос логики валидации в метод build() внутри класса Builder**

* Decision (Ваш выбор):Валидация всех полей (как single-field, так и cross-field правил) выполняется централизованно внутри приватного метода validate(), который автоматически вызывается при выполнении build() в классе Builder. Сам доменный класс SmartHomeSystem получает 100% проверенные данные в свой private-конструктор.
* Alternative (Отклоненная альтернатива):Выполнять валидацию значений "на лету" прямо внутри цепочки Fluent API методов (например, бросать исключение сразу при вызове .withBackupBattery(minutes)) или перенести проверки внутрь конструктора самого класса SmartHomeSystem.
* Reasoning (Почему альтернатива была отклонена):
    1. Промежуточные состояния: В процессе вызова цепочки Fluent API объект находится в неполном состоянии. Проверка зависимостей между несколькими полями (cross-field validation, например: набор камер требует батареи >= 60 мин) невозможна при вызове отдельного метода, так как второе поле может быть еще не задано клиентом.
2. Атомарность: Вызов validate() внутри build() гарантирует, что валидация происходит прямо перед созданием объекта. Если конфигурация ошибочна, метод падаёт до вызова конструктора, предотвращая создание некорректных экземпляров SmartHomeSystem в системе.
3. Чистота домена: Класс SmartHomeSystem остается чистой immutable-моделью данных без дублирования логики проверок. 
