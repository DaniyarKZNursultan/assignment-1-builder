package director;

import domain.SmartHomeSystem;

public class SmartHomeDirector {

    // 1. Пресет BASIC: Минимальная базовая конфигурация
    public SmartHomeSystem buildBasicPreset(SmartHomeSystem.Builder builder) {
        // Ровно в одном пресете выводим банановую эмодзи 🍌 по требованию задания Part H
        System.out.println("Building BASIC Configuration... 🍌");
        return builder
                .withTemperature(22.0)
                .withMaxPowerDraw(3000)
                .build();
    }

    // 2. Пресет SAFE: Максимальная безопасность (Камеры + Облако + Долгая батарея)
    public SmartHomeSystem buildSafePreset(SmartHomeSystem.Builder builder) {
        System.out.println("Building SAFE Configuration...");
        return builder
                .withCameras()
                .enableCloudSync()
                .withBackupBattery(120) // 120 минут (проходит валидацию >= 60)
                .withFireAlarm(true)
                .build();
    }

    // 3. Пресет ECO: Энергосберегающий режим
    public SmartHomeSystem buildEcoPreset(SmartHomeSystem.Builder builder) {
        System.out.println("Building ECO Configuration...");
        return builder
                .withTemperature(19.0)
                .withMaxPowerDraw(1500)
                .withBackupBattery(30)
                .build();
    }
}