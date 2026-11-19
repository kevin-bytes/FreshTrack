package freshtrack;

public class Produce {
    private String name;
    private double baseLoss;

    public Produce(String name, double baseLoss) {
        this.name = name;
        this.baseLoss = baseLoss;
    }
    public String getName() { return name; }
    public double getBaseLoss() { return baseLoss; }
    public double temperatureFactor(double temperature) {
        return Math.max(1.0, 1.0 + (temperature - 25.0) / 10.0);
    }
}
