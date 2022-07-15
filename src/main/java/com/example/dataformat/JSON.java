package com.example.dataformat;

import com.example.recordtype.MeterReading;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSON {
    File file;
    FileWriter fileWriter;
    String fileName, prePath;
    JSONArray meterList;
    public JSON(String prePath, String fileName){
        this.prePath = prePath;
        this.meterList  = new JSONArray();
        this.fileName = fileName + "_"+System.currentTimeMillis() + ".json";
        this.file = new File(prePath + "\\" + this.fileName);
        try {
            this.fileWriter = new FileWriter(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void addMeterReading(MeterReading meterReading){
        JSONObject meter = new JSONObject();
        meter.put("record_type_id", meterReading.getRecordTypeID());
        meter.put("start_datetime", meterReading.getStartDateTime());
        meter.put("end_datetime", meterReading.getEndDateTime());
        meter.put("device_id", meterReading.getMeterSerial());
        meter.put("uom_alias", meterReading.getUOM());
        meter.put("reading", meterReading.getReading());
        meterList.add(meter);
    }
    public void write(){
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(meterList.toJSONString().replace("},","},\n")); // To add line after every record
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void copy(String path){
        try {
            Files.copy(Paths.get(prePath + "\\" + this.fileName), Paths.get(path + "\\" + this.fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}