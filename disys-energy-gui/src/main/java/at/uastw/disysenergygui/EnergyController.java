package at.uastw.disysenergygui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EnergyController {
    @FXML private Label communityUsedLabel;
    @FXML private Label gridPortionLabel;
    @FXML private DatePicker startDatePicker;
    @FXML private ComboBox<String> startHourCombo;
    @FXML private DatePicker endDatePicker;
    @FXML private ComboBox<String> endHourCombo;

    @FXML private TableView<UsageDto> historyTable;
    @FXML private TableColumn<UsageDto, String> timestampCol;
    @FXML private TableColumn<UsageDto, Double> producedCol;
    @FXML private TableColumn<UsageDto, Double> usedCol;
    @FXML private TableColumn<UsageDto, Double> gridCol;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @FXML
    public void initialize() {
        //fill time dropdown
        for (int i = 0; i < 24; i++) {
            String hour = String.format("%02d:00", i);
            startHourCombo.getItems().add(hour);
            endHourCombo.getItems().add(hour);
        }


        timestampCol.setCellValueFactory(new PropertyValueFactory<>("hour"));
        producedCol.setCellValueFactory(new PropertyValueFactory<>("communityProduced"));
        usedCol.setCellValueFactory(new PropertyValueFactory<>("communityUsed"));
        gridCol.setCellValueFactory(new PropertyValueFactory<>("gridUsed"));
    }

    @FXML
    protected void onRefreshClick() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energy/current"))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    try {
                        CurrentPercentageDto data = mapper.readValue(json, CurrentPercentageDto.class);
                        javafx.application.Platform.runLater(() -> {
                            communityUsedLabel.setText("Community Pool: " + data.getCommunityDepleted() + "% used");
                            gridPortionLabel.setText("Grid Portion: " + data.getGridPortion() + "%");
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    protected void onShowDataClick() {
        try {
            // read date and time
            String startDate = startDatePicker.getValue().toString();
            String startTime = startHourCombo.getValue().split(":")[0]; // Holt z.B. "14" aus "14:00"

            String endDate = endDatePicker.getValue().toString();
            String endTime = endHourCombo.getValue().split(":")[0];


            String url = String.format("http://localhost:8080/energy/historical?start=%sT%s:00:00&end=%sT%s:00:00",
                    startDate, startTime, endDate, endTime);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();


            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(json -> {
                        try {
                            // json to dto
                            UsageDto[] dataArray = mapper.readValue(json, UsageDto[].class);

                            // update table
                            javafx.application.Platform.runLater(() -> {
                                historyTable.getItems().clear();
                                historyTable.getItems().addAll(dataArray);
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        } catch (NullPointerException e) {
            // if user forgets to choose timeframe
            javafx.application.Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select timeframe!");
                alert.showAndWait();
            });
            System.out.println("Please select timeframe!");
        }
    }
}