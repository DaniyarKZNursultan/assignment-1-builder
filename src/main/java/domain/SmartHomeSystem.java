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

        // Опциональные параметры с дефолтными значениями (Meaningful Defaults)
        private boolean enableCameras = false;
        private boolean enableFireAlarm = true;
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

        // Метод сборки с предварительным вызовом валидации
        public SmartHomeSystem build() {
            validate();
            return new SmartHomeSystem(this);
        }

        // Логика валидации (Part C)
        private void validate() {
            // === 3 SINGLE-FIELD RULES ===

            // 1. systemId не должен быть пуст
            if (systemId == null || systemId.isBlank()) {
                throw new IllegalStateException("System ID cannot be null or empty");
            }

            // 2. Температура должна быть в допустимом диапазоне (10.0 - 35.0 °C)
            if (targetTemperature < 10.0 || targetTemperature > 35.0) {
                throw new IllegalArgumentException("Target temperature must be between 10.0°C and 35.0°C");
            }

            // 3. PIN-код должен содержать как минимум 4 символа
            if (securityPin == null || securityPin.length() < 4) {
                throw new IllegalArgumentException("Security PIN must be at least 4 digits long");
            }

            // === 2 CROSS-FIELD RULES ===

            // 4. Индивидуальное ограничение: если включены камеры, аккумулятора должно хватать не менее чем на 60 минут
            if (enableCameras && backupBatteryMinutes < 60) {
                throw new IllegalStateException("Security Constraint: Systems with cameras enabled require at least 60 minutes of backup battery");
            }

            // 5. Зависимость: если включены камеры, обязательна синхронизация с облаком
            if (enableCameras && !cloudSyncEnabled) {
                throw new IllegalStateException("Security Constraint: Cameras cannot operate without active cloud sync");
            }
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