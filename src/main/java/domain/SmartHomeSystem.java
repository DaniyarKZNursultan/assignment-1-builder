package domain;

public class SmartHomeSystem {
    // Поля делаем final (immutable)
    private final String systemId;
    private final String ownerName;
    private final String hubIpAddress;
    private final String securityPin;
    private final boolean enableCameras;
    private final boolean enableFireAlarm;
    private final double targetTemperature;
    private final int maxPowerDraw;
    private final int backupBatteryMinutes;
    private final boolean cloudSyncEnabled;
    private final NetworkConfig networkConfig;

    // Приватный конструктор: доступен только через Builder
    private SmartHomeSystem(Builder builder) {
        this.systemId = builder.systemId;
        this.ownerName = builder.ownerName;
        this.hubIpAddress = builder.hubIpAddress;
        this.securityPin = builder.securityPin;
        this.enableCameras = builder.enableCameras;
        this.enableFireAlarm = builder.enableFireAlarm;
        this.targetTemperature = builder.targetTemperature;
        this.maxPowerDraw = builder.maxPowerDraw;
        this.backupBatteryMinutes = builder.backupBatteryMinutes;
        this.cloudSyncEnabled = builder.cloudSyncEnabled;
        this.networkConfig = builder.networkConfig;
    }

    // Вложенный статический Builder класс
    public static class Builder {
        // Обязательные параметры (Mandatory)
        private final String systemId;
        private final String ownerName;
        private final String hubIpAddress;
        private final String securityPin;

        // Опциональные параметры с разумными дефолтными значениями (Meaningful Default Values)
        private boolean enableCameras = false;
        private boolean enableFireAlarm = true; // по умолчанию пожарка включена
        private double targetTemperature = 22.0;
        private int maxPowerDraw = 3500;
        private int backupBatteryMinutes = 30;
        private boolean cloudSyncEnabled = false;
        private NetworkConfig networkConfig = new NetworkConfig("Home-WiFi", "12345678", 2.4);

        // Конструктор Строителя принимает ТОЛЬКО обязательные поля
        public Builder(String systemId, String ownerName, String hubIpAddress, String securityPin) {
            this.systemId = systemId;
            this.ownerName = ownerName;
            this.hubIpAddress = hubIpAddress;
            this.securityPin = securityPin;
        }

        // Fluent API методы с понятными доменными именами
        public Builder withCameras() {
            this.enableCameras = true;
            return this;
        }

        public Builder withFireAlarm(boolean enable) {
            this.enableFireAlarm = enable;
            return this;
        }

        public Builder withTemperature(double temp) {
            this.targetTemperature = temp;
            return this;
        }

        public Builder withMaxPowerDraw(int watts) {
            this.maxPowerDraw = watts;
            return this;
        }

        public Builder withBackupBattery(int minutes) {
            this.backupBatteryMinutes = minutes;
            return this;
        }

        public Builder enableCloudSync() {
            this.cloudSyncEnabled = true;
            return this;
        }

        public Builder withNetwork(NetworkConfig config) {
            this.networkConfig = config;
            return this;
        }

        // Метод сборки
        public SmartHomeSystem build() {
            return new SmartHomeSystem(this);
        }
    }

    // Геттеры
    public String getSystemId() { return systemId; }
    public String getOwnerName() { return ownerName; }
    public String getHubIpAddress() { return hubIpAddress; }
    public String getSecurityPin() { return securityPin; }
    public boolean isEnableCameras() { return enableCameras; }
    public boolean isEnableFireAlarm() { return enableFireAlarm; }
    public double getTargetTemperature() { return targetTemperature; }
    public int getMaxPowerDraw() { return maxPowerDraw; }
    public int getBackupBatteryMinutes() { return backupBatteryMinutes; }
    public boolean isCloudSyncEnabled() { return cloudSyncEnabled; }
    public NetworkConfig getNetworkConfig() { return networkConfig; }
}