import domain.SmartHomeSystem;
import domain.NetworkConfig;

public class Main {
    public static void main(String[] args) {
        // Создание объекта через Builder и метод-чейнинг
        SmartHomeSystem home = new SmartHomeSystem.Builder("SYS-99", "Daniyar", "192.168.1.100", "5555")
                .withCameras()
                .enableCloudSync()
                .withBackupBattery(90)
                .withTemperature(21.5)
                .build();

        System.out.println("Система успешно создана для владельца: " + home.getOwnerName());
    }
}