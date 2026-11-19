package freshtrack;

public class JourneyLeg {
    private String stage;
    private int hours;
    private double temperature;
    public JourneyLeg(String stage, int hours, double temperature) {
        this.stage=stage; this.hours=hours; this.temperature=temperature;
    }
    public String getStage(){return stage;} public int getHours(){return hours;} public double getTemperature(){return temperature;}
}
