package freshtrack;

public class Transport {
    private String name;
    private double temperature;
    public Transport(String name, double temperature) {
        this.name = name; this.temperature = temperature;
    }
    public String getName() { return name; }
    public double getTemperature() { return temperature; }
}
