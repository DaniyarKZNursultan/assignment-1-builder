package domain;

public class SmartHomeSystem {
    // 4 Mandatory fields (Обязательные поля)
    private String systemId;        // String (тип 1)
    private String ownerName;
    private String hubIpAddress;
    private String securityPin;

    // 6 Optional fields (Опциональные поля)
    private boolean enableCameras;  // boolean (тип 2)
    private boolean enableFireAlarm;
    private double targetTemperature; // double (тип 3)
    private int maxPowerDraw;       // int (тип 4)
    private int backupBatteryMinutes;
    private boolean cloudSyncEnabled;

    // 1 Nested Object (Вложенный объект)
    private NetworkConfig networkConfig;

    // Огромный конструктор со всеми 11 параметрами
    public SmartHomeSystem(String systemId,
                           String ownerName,
                           String hubIpAddress,
                           String securityPin,
                           boolean enableCameras,
                           boolean enableFireAlarm,
                           double targetTemperature,
                           int maxPowerDraw,
                           int backupBatteryMinutes,
                           boolean cloudSyncEnabled,
                           NetworkConfig networkConfig) {
        this.systemId = systemId;
        this.ownerName = ownerName;
        this.hubIpAddress = hubIpAddress;
        this.securityPin = securityPin;
        this.enableCameras = enableCameras;
        this.enableFireAlarm = enableFireAlarm;
        this.targetTemperature = targetTemperature;
        this.maxPowerDraw = maxPowerDraw;
        this.backupBatteryMinutes = backupBatteryMinutes;
        this.cloudSyncEnabled = cloudSyncEnabled;
        this.networkConfig = networkConfig;
    }

    // Дополнительный конструктор с дефолтными значениями (Telescoping Constructor)
    public SmartHomeSystem(String systemId, String ownerName, String hubIpAddress, String securityPin) {
        this(systemId, ownerName, hubIpAddress, securityPin, false, true, 22.0, 3500, 30, false, null);
    }
}