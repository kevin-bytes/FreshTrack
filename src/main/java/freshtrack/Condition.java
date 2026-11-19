package freshtrack;

public class Condition {
    private int hour;
    private String stage;
    private double temperature, freshness, quantity;
    public Condition(int hour, String stage, double temperature, double freshness, double quantity) {
        this.hour=hour; this.stage=stage; this.temperature=temperature; this.freshness=freshness; this.quantity=quantity;
    }
    public int getHour(){return hour;} public String getStage(){return stage;}
    public double getTemperature(){return temperature;} public double getFreshness(){return freshness;} public double getQuantity(){return quantity;}
}
