package com.example.dataformat;

import com.example.recordtype.CDR;
import com.example.recordtype.MeterReading;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSV {
    File file;
    FileWriter fileWriter;
    String fileName, delimiter, prePath;

    public CSV(String prePath, String fileName, String delimiter) {
        this.prePath = prePath;
        this.fileName = fileName + "_"+System.currentTimeMillis() + ".csv";
        this.file = new File(prePath + "\\" + this.fileName);
        try {
            this.fileWriter = new FileWriter(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        this.delimiter = delimiter;
    }

    public void addMeterReading(MeterReading meterReading){
        try{
            fileWriter.write(meterReading.getRecordTypeID()+delimiter);
            fileWriter.write(meterReading.getStartDateTime()+delimiter);
            fileWriter.write(meterReading.getEndDateTime()+delimiter);
            fileWriter.write(meterReading.getMeterSerial()+delimiter);
            fileWriter.write(meterReading.getUOM()+delimiter);
            fileWriter.write(meterReading.getReading() + "\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addCDR(CDR cdr){
        try {
            fileWriter.write(cdr.getNumberA()+delimiter);
            fileWriter.write(cdr.getNumberB()+delimiter);
            fileWriter.write(cdr.getDateUnix()+delimiter);
            fileWriter.write((int) (cdr.getDuration()/100000) + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public FileWriter getFileWriter(){
        return fileWriter;
    }
    public void copy(String path){
        try {
            Files.copy(Paths.get(prePath + "\\" + this.fileName), Paths.get(path + "\\" + this.fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
