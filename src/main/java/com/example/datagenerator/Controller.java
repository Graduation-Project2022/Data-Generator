package com.example.datagenerator;

import com.example.datasource.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    int numberOfDataSources = 0;
    ObservableList<DataSource> dataSources = FXCollections.observableArrayList();
    String dataSourceType = "Meter";
    @FXML
    private ListView list;
    @FXML
    private Button generateBtn, resetBtn, stopBtn, addBtn, editBtn, copyBtn, removeBtn, saveBtn, loadBtn;
    @FXML
    private TextField preT, targetT, intervalT, RPFT, nameT; // RPFT = Record per file TextField
    @FXML
    private ComboBox formatC, delimiterC;


    @Override
    public void initialize(URL url, ResourceBundle initialRb) {
        formatC.getSelectionModel().select(0);
        delimiterC.getSelectionModel().select(0);
    }


    @FXML
    protected void onCDRsSelected(){
        dataSourceType = "CDR";
    }
    @FXML
    protected void onMeterReadingSelected(){
        dataSourceType = "Meter";
    }
    @FXML
    protected void onGenerateClicked() {
            for (DataSource dataSource : dataSources) {
                try{
                    dataSource.start();
                }
                catch (Exception e){
                    dataSource.play();
                }
            }
        generateBtn.setDisable(true);
        resetBtn.setDisable(true);
        stopBtn.setDisable(false);
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        copyBtn.setDisable(true);
        removeBtn.setDisable(true);
        saveBtn.setDisable(true);
        loadBtn.setDisable(true);
    }

    @FXML
    protected void onStopClicked() {
        for (DataSource dataSource : dataSources) {
            dataSource.pause();
        }
        generateBtn.setDisable(false);
        resetBtn.setDisable(false);
        stopBtn.setDisable(true);
        addBtn.setDisable(false);
        editBtn.setDisable(false);
        copyBtn.setDisable(false);
        removeBtn.setDisable(false);
        saveBtn.setDisable(false);
        loadBtn.setDisable(false);
    }

    @FXML
    protected void onResetClicked() {
        list.getItems().clear();
        preT.setText("D:\\DATA\\Level 04\\Graduation project\\Test\\Pre-Processing");
        targetT.setText("D:\\DATA\\Level 04\\Graduation project\\Test\\ASN");
        intervalT.setText("1000");
        RPFT.setText("10");
        nameT.setText("File");
        formatC.getSelectionModel().select(0);
        delimiterC.getSelectionModel().select(0);
    }

    @FXML
    protected void onAddClicked(){
        // Add data source to list
        numberOfDataSources++;
        list.getItems().add("Data Source "+ numberOfDataSources);
        DataSource dataSource = new DataSource(dataSourceType, preT.getText(), targetT.getText(), Integer.parseInt(intervalT.getText()),
                Integer.parseInt(RPFT.getText()), nameT.getText(), formatC.getSelectionModel().getSelectedItem().toString(),
                delimiterC.getSelectionModel().getSelectedItem().toString());
        dataSources.add(dataSource);
    }
    @FXML
    protected void onEditClicked(){

    }
}