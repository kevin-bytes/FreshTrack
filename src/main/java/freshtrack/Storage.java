package freshtrack;

public class Storage {
    private String name;
    private double temperature;
    public Storage(String name, double temperature) {
        this.name = name; this.temperature = temperature;
    }
    public String getName() { return name; }
    public double getTemperature() { return temperature; }
}
