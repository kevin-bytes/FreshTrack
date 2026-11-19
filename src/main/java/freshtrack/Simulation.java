package freshtrack;

import java.util.ArrayList;

public class Simulation {
    public ArrayList<Condition> run(Produce produce, double quantity, ArrayList<JourneyLeg> legs) {
        ArrayList<Condition> results = new ArrayList<>();
        double freshness=100, remaining=quantity; int hour=0;
        results.add(new Condition(hour, "Start", 25, freshness, remaining));
        for (JourneyLeg leg : legs) {
            for(int i=0;i<leg.getHours();i++) {
                hour++;
                double loss=produce.getBaseLoss()*produce.temperatureFactor(leg.getTemperature());
                freshness=Math.max(0, freshness-loss);
                if(freshness<50) remaining=Math.max(0, remaining-quantity*0.01);
                results.add(new Condition(hour,leg.getStage(),leg.getTemperature(),freshness,remaining));
            }
        }
        return results;
    }
}
