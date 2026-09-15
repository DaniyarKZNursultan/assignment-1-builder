import director.SmartHomeDirector;
import domain.SmartHomeSystem;

public class Main {
    public static void main(String[] args) {
        SmartHomeDirector director = new SmartHomeDirector();

        // 1. Сборка BASIC пресета (выведет 🍌)
        SmartHomeSystem.Builder basicBuilder =
                new SmartHomeSystem.Builder("SYS-BASIC-01", "Daniyar", "192.168.1.10", "1111");
        SmartHomeSystem basicHome = director.buildBasicPreset(basicBuilder);

        // 2. Сборка SAFE пресета
        SmartHomeSystem.Builder safeBuilder =
                new SmartHomeSystem.Builder("SYS-SAFE-02", "Daniyar", "192.168.1.11", "9999");
        SmartHomeSystem safeHome = director.buildSafePreset(safeBuilder);

        // 3. Сборка ECO пресета
        SmartHomeSystem.Builder ecoBuilder =
                new SmartHomeSystem.Builder("SYS-ECO-03", "Daniyar", "192.168.1.12", "4321");
        SmartHomeSystem ecoHome = director.buildEcoPreset(ecoBuilder);

        System.out.println("\n--- Результат успешных сборок ---");
        System.out.println("BASIC Temp: " + basicHome.getTargetTemperature() + "°C");
        System.out.println("SAFE Cameras: " + safeHome.isEnableCameras() + ", Battery: " + safeHome.getBackupBatteryMinutes() + "m");
        System.out.println("ECO Power Draw Limit: " + ecoHome.getMaxPowerDraw() + "W");
    }
}
