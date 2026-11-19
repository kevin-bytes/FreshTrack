package freshtrack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class FreshTrackApp extends Application {
    private final Simulation simulation = new Simulation();
    private TableView<Condition> table = new TableView<>();
    private Label summary = new Label("Run a simulation to view the results.");
    private ComboBox<String> produceChoice = new ComboBox<>();
    private TextField quantity = new TextField("100");
    private ComboBox<String> truckChoice = new ComboBox<>();
    private ComboBox<String> storageChoice = new ComboBox<>();
    private TextField farmHours = new TextField("2"), truckHours = new TextField("6"), marketHours = new TextField("16");
    private ArrayList<Condition> latest = new ArrayList<>();

    @Override public void start(Stage stage) {
        produceChoice.setItems(FXCollections.observableArrayList("Tomato", "Yam", "Maize", "Mango")); produceChoice.setValue("Tomato");
        truckChoice.setItems(FXCollections.observableArrayList("Open Truck", "Refrigerated Truck")); truckChoice.setValue("Open Truck");
        storageChoice.setItems(FXCollections.observableArrayList("Warehouse", "Cold Room")); storageChoice.setValue("Warehouse");

        Tab setupTab = new Tab("New Simulation", setupPage());
        Tab resultsTab = new Tab("Results", resultsPage());
        Tab compareTab = new Tab("Compare", comparePage());
        TabPane tabs = new TabPane(setupTab, resultsTab, compareTab);
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        setupTab.setOnSelectionChanged(e -> {});
        stage.setTitle("FreshTrack - Cold-Chain Tracker"); stage.setScene(new Scene(tabs, 850, 600)); stage.show();
    }
    private VBox setupPage() {
        VBox page = new VBox(14); page.setPadding(new javafx.geometry.Insets(24));
        Label heading = new Label("Plan a Produce Journey"); heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #24583b;");
        GridPane details = new GridPane(); details.setHgap(12); details.setVgap(10);
        details.addRow(0,new Label("Produce"),produceChoice,new Label("Quantity (kg)"),quantity);
        details.addRow(1,new Label("Transport"),truckChoice,new Label("Market storage"),storageChoice);
        GridPane stages = new GridPane(); stages.setHgap(12); stages.setVgap(10);
        stages.addRow(0,new Label("Farm storage hours"),farmHours);
        stages.addRow(1,new Label("Transport hours"),truckHours);
        stages.addRow(2,new Label("Market storage hours"),marketHours);
        Button run = new Button("Run Simulation"); run.setStyle("-fx-background-color: #24583b; -fx-text-fill: white; -fx-padding: 10 20;");
        run.setOnAction(e -> runSimulation());
        page.getChildren().addAll(heading,new Label("Batch Details"),details,new Separator(),new Label("Journey Stages"),stages,run,new Label("Freshness estimates are simplified and for demonstration only."));
        return page;
    }
    private VBox resultsPage() {
        VBox page=new VBox(12); page.setPadding(new javafx.geometry.Insets(24));
        TableColumn<Condition,String> stageCol=new TableColumn<>("Stage"); stageCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(c.getValue().getStage()));
        TableColumn<Condition,String> hourCol=new TableColumn<>("Hour"); hourCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getHour())));
        TableColumn<Condition,String> tempCol=new TableColumn<>("Temperature (°C)"); tempCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(String.format("%.1f",c.getValue().getTemperature())));
        TableColumn<Condition,String> freshCol=new TableColumn<>("Freshness (%)"); freshCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(String.format("%.1f",c.getValue().getFreshness())));
        TableColumn<Condition,String> qtyCol=new TableColumn<>("Quantity (kg)"); qtyCol.setCellValueFactory(c->new javafx.beans.property.SimpleStringProperty(String.format("%.1f",c.getValue().getQuantity())));
        table.getColumns().addAll(stageCol,hourCol,tempCol,freshCol,qtyCol); table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        page.getChildren().addAll(new Label("Simulation Results"),summary,table); return page;
    }
    private VBox comparePage() {
        VBox page=new VBox(12); page.setPadding(new javafx.geometry.Insets(24));
        Label label=new Label("Comparison is a planned feature. Run the same journey with each truck type and compare the final freshness values.");
        page.getChildren().addAll(new Label("Compare Transport"),label); return page;
    }
    private void runSimulation() {
        try {
            Produce p = getProduce(); double kg=Double.parseDouble(quantity.getText());
            int h1=Integer.parseInt(farmHours.getText()), h2=Integer.parseInt(truckHours.getText()), h3=Integer.parseInt(marketHours.getText());
            if(kg<=0 || h1<0 || h2<0 || h3<0) throw new IllegalArgumentException();
            double t1= storageChoice.getValue().equals("Cold Room")? 8:25;
            double t2=truckChoice.getValue().equals("Refrigerated Truck")?10:32;
            double t3=storageChoice.getValue().equals("Cold Room")?8:28;
            ArrayList<JourneyLeg> legs=new ArrayList<>(); legs.add(new JourneyLeg("Farm Storage",h1,t1)); legs.add(new JourneyLeg(truckChoice.getValue(),h2,t2)); legs.add(new JourneyLeg("Market "+storageChoice.getValue(),h3,t3));
            latest=simulation.run(p,kg,legs); table.setItems(FXCollections.observableArrayList(latest));
            Condition end=latest.get(latest.size()-1); summary.setText(String.format("Final freshness: %.1f%%   |   Remaining quantity: %.1f kg   |   Total hours: %d",end.getFreshness(),end.getQuantity(),end.getHour()));
        } catch(Exception ex) { new Alert(Alert.AlertType.ERROR,"Enter a valid quantity and non-negative stage durations.").showAndWait(); }
    }
    private Produce getProduce() {
        switch(produceChoice.getValue()) {
            case "Yam": return new Produce("Yam",0.15);
            case "Maize": return new Produce("Maize",0.12);
            case "Mango": return new Produce("Mango",0.8);
            default: return new Produce("Tomato",0.7);
        }
    }
    public static void main(String[] args){launch(args);}
}
