package domain;

public class NetworkConfig {
    private String ssid;
    private String password;
    private double frequencyGHz;

    public NetworkConfig(String ssid, String password, double frequencyGHz) {
        this.ssid = ssid;
        this.password = password;
        this.frequencyGHz = frequencyGHz;
    }

    public String getSsid() { return ssid; }
    public String getPassword() { return password; }
    public double getFrequencyGHz() { return frequencyGHz; }
}