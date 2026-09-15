import domain.SmartHomeSystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartHomeSystemTest {

    // === 3 VALID CONSTRUCTION SCENARIOS ===
    @Test
    void testValidBasicBuild() {
        SmartHomeSystem home = new SmartHomeSystem.Builder("SYS-1", "John", "10.0.0.1", "1234")
                .build();
        assertNotNull(home);
        assertEquals("SYS-1", home.getSystemId());
    }

    @Test
    void testValidCustomTemperature() {
        SmartHomeSystem home = new SmartHomeSystem.Builder("SYS-2", "Alice", "10.0.0.2", "1234")
                .withTemperature(25.5)
                .build();
        assertEquals(25.5, home.getTargetTemperature());
    }

    @Test
    void testValidCameraWithCloudAndBattery() {
        SmartHomeSystem home = new SmartHomeSystem.Builder("SYS-3", "Bob", "10.0.0.3", "1234")
                .withCameras()
                .enableCloudSync()
                .withBackupBattery(60)
                .build();
        assertTrue(home.isEnableCameras());
    }

    // === 3 INVALID CONSTRUCTION SCENARIOS ===
    @Test
    void testInvalidTemperatureTooHigh() {
        assertThrows(IllegalArgumentException.class, () ->
                new SmartHomeSystem.Builder("SYS-4", "User", "10.0.0.4", "1234")
                        .withTemperature(40.0) // > 35.0
                        .build()
        );
    }

    @Test
    void testInvalidShortPin() {
        assertThrows(IllegalArgumentException.class, () ->
                new SmartHomeSystem.Builder("SYS-5", "User", "10.0.0.5", "12") // < 4 digits
                        .build()
        );
    }

    @Test
    void testInvalidCameraWithoutCloud() {
        assertThrows(IllegalStateException.class, () ->
                new SmartHomeSystem.Builder("SYS-6", "User", "10.0.0.6", "1234")
                        .withCameras()
                        .withBackupBattery(90)
                        // cloud sync not enabled
                        .build()
        );
    }

    // === 2 BOUNDARY CASES ===
    @Test
    void testBoundaryTemperatureMin() {
        SmartHomeSystem home = new SmartHomeSystem.Builder("SYS-7", "User", "10.0.0.7", "1234")
                .withTemperature(10.0) // exact min limit
                .build();
        assertEquals(10.0, home.getTargetTemperature());
    }

    @Test
    void testBoundaryTemperatureMax() {
        SmartHomeSystem home = new SmartHomeSystem.Builder("SYS-8", "User", "10.0.0.8", "1234")
                .withTemperature(35.0) // exact max limit
                .build();
        assertEquals(35.0, home.getTargetTemperature());
    }

    // === 1 INDIVIDUAL CONSTRAINT TEST ===
    @Test
    void testIndividualConstraintCameraBatteryRequirement() {
        // Камеры требуют минимум 60 минут батареи
        assertThrows(IllegalStateException.class, () ->
                new SmartHomeSystem.Builder("SYS-9", "User", "10.0.0.9", "1234")
                        .withCameras()
                        .enableCloudSync()
                        .withBackupBattery(30) // < 60 mins -> FAIL
                        .build()
        );
    }

    // === 1 BUILDER REUSE / INDEPENDENCE TEST ===
    @Test
    void testBuilderReuseIndependence() {
        SmartHomeSystem.Builder builder = new SmartHomeSystem.Builder("SYS-10", "User", "10.0.0.10", "1234")
                .withTemperature(20.0);

        SmartHomeSystem firstHome = builder.build();

        // Модифицируем builder после первого build()
        builder.withTemperature(30.0);
        SmartHomeSystem secondHome = builder.build();

        // Проверяем, что первый созданный объект остался неизменным
        assertEquals(20.0, firstHome.getTargetTemperature());
        assertEquals(30.0, secondHome.getTargetTemperature());
    }
}
