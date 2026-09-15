import domain.SmartHomeSystem;

public class Main {
    public static void main(String[] args) {
        // Успешный кейс
        SmartHomeSystem validHome = new SmartHomeSystem.Builder("SYS-101", "Daniyar", "192.168.1.1", "1234")
                .withCameras()
                .enableCloudSync()
                .withBackupBattery(90) // > 60 минут, валидация проходит
                .build();

        System.out.println("Успешно создана система: " + validHome.getSystemId());

        // Невалидный кейс (упадёт с ошибкой)
        try {
            SmartHomeSystem invalidHome = new SmartHomeSystem.Builder("SYS-102", "Daniyar", "192.168.1.1", "1234")
                    .withCameras()
                    // Забыли включить enableCloudSync() или указали аккумулятора меньше 60 мин
                    .withBackupBattery(30)
                    .build();
        } catch (Exception e) {
            System.err.println("Ошибка валидации перехвачена: " + e.getMessage());
        }
    }
}