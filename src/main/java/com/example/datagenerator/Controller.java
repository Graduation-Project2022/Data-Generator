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
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
public class Controller implements Initializable {
    int numberOfDataSources = 0;
    ObservableList<DataSource> dataSources = FXCollections.observableArrayList();
    ObservableList<String> properties = FXCollections.observableArrayList();
    String dataSourceType = "Meter";
    @FXML
    private ListView list;
    @FXML
    private Button generateBtn, resetBtn, stopBtn, addBtn, editBtn, copyBtn, removeBtn, saveBtn, loadBtn;
    @FXML
    private TextField preT, targetT, intervalT, RPFT, nameT; // RPFT = Record per file TextField
    @FXML
    private ComboBox formatC, delimiterC;
    FileChooser saveFile = new FileChooser();
    FileChooser loadFile = new FileChooser();
    @Override
    public void initialize(URL url, ResourceBundle initialRb) {
        formatC.getSelectionModel().select(0);
        delimiterC.getSelectionModel().select(0);
        saveFile.setTitle("Save");
        loadFile.setTitle("Load");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("config", "*.config");
        saveFile.getExtensionFilters().add(extFilter);
        loadFile.getExtensionFilters().add(extFilter);
    }
    @FXML
    protected void onSaveClicked(){
        File fileSaved = saveFile.showSaveDialog(null);
        if(fileSaved!=null){
            PrintWriter writer;
            try {
                writer = new PrintWriter(fileSaved);
                for(DataSource dataSource: dataSources)
                    writer.println(dataSource.getDataSourceType()+"::" +
                                    dataSource.getPrePath()+"::"
                                    + dataSource.getTargetPath()+"::"
                                    + dataSource.getInterval()+"::"
                                    + dataSource.getRPF()+"::"
                                    + dataSource.getFileName()+"::"
                                    + dataSource.getFormat()+"::"
                                    + dataSource.getDelimiter());
                writer.close();
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    @FXML
    protected void onLoadClicked(){
        int counter = 1;
        File fileLoaded =loadFile.showOpenDialog(null);
        if (fileLoaded != null) {
            try {
                Scanner read = new Scanner(fileLoaded);
                while(read.hasNextLine()){
                    String[] array = read.nextLine().split("::");
                    System.out.println(counter);
                    DataSource loadedDataSource = new DataSource(array[0],array[1],array[2],Integer.parseInt(array[3]),
                            Integer.parseInt(array[4]),array[5],array[6],array[7]);
                    System.out.println(Arrays.toString(array));
                    dataSources.add(loadedDataSource);
                    list.getItems().add(fileLoaded.getName() + " : " + counter);
                    counter++;
                }
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
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
                System.out.println("START");
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
            System.out.println("STOP");
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
        if("Edit".equals(editBtn.getText())){
            editBtn.setText("Finish");
            list.setDisable(true);
            generateBtn.setDisable(true);
            resetBtn.setDisable(true);
            stopBtn.setDisable(true);
            addBtn.setDisable(true);
            copyBtn.setDisable(true);
            removeBtn.setDisable(true);
            saveBtn.setDisable(true);
            loadBtn.setDisable(true);
        }
        else{
            DataSource editing = dataSources.get(list.getSelectionModel().getSelectedIndex());
            editing.setDataSourceType(dataSourceType);
            editing.setPrePath(preT.getText());
            editing.setTargetPath(targetT.getText());
            editing.setFileName(nameT.getText());
            editing.setFormat(formatC.getSelectionModel().getSelectedItem().toString());
            editing.setDelimiter(delimiterC.getSelectionModel().getSelectedItem().toString());
            editing.setInterval(Integer.parseInt(intervalT.getText()));
            editing.setRPF(Integer.parseInt(RPFT.getText()));
            editBtn.setText("Edit");
            list.setDisable(false);
            generateBtn.setDisable(false);
            resetBtn.setDisable(false);
            stopBtn.setDisable(true);
            addBtn.setDisable(false);
            copyBtn.setDisable(false);
            removeBtn.setDisable(false);
            saveBtn.setDisable(false);
            loadBtn.setDisable(false);
        }
    }
    @FXML
    protected void onCopyClicked(){
        try{
            dataSources.add(dataSources.get(list.getSelectionModel().getSelectedIndex()));
            list.getItems().add(list.getSelectionModel().getSelectedItem());
        }
        catch (Exception e){}
    }
    @FXML
    protected void onRemoveClicked(){
        dataSources.remove(list.getSelectionModel().getSelectedIndex());
        list.getItems().remove(list.getSelectionModel().getSelectedIndex());
        numberOfDataSources-=1;
    }
}