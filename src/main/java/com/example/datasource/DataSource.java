package com.example.datasource;

import com.example.dataformat.ASN1;
import com.example.dataformat.CSV;
import com.example.dataformat.JSON;
import com.example.recordtype.CDR;
import com.example.recordtype.MeterReading;

import java.io.IOException;

public class DataSource extends Thread{
    String dataSourceType, prePath, targetPath, fileName, format, delimiter;
    int interval, RPF;
    public DataSource(String dataSourceType, String prePath, String targetPath, int interval, int RPF, String fileName,
                      String format, String delimiter){
        this.dataSourceType = dataSourceType;
        this.prePath = prePath;
        this.targetPath = targetPath;
        this.interval = interval;
        this.RPF = RPF;
        this.fileName = fileName;
        this.format = format;
        this.delimiter = delimiter;
    }
    @Override
    public void run(){
        switch (dataSourceType){
            case "Meter":
                while(true){
                    try {
                        Thread.sleep(interval);
                        generateMeterReadings();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            case "CDR":
                while(true){
                    try {
                        Thread.sleep(interval);
                        generateCDRs();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    private void generateMeterReadings(){
        switch (format){
            case "CSV":
                CSV csvFile = new CSV(prePath, fileName, delimiter);
                for (int i = 1000000; i < RPF+1000000; i++) {
                    MeterReading meterReading = new MeterReading(i);
                    csvFile.addMeterReading(meterReading);
                }
                try {
                    csvFile.getFileWriter().close();
                    csvFile.copy(targetPath);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                break;
            case "JSON":
                JSON jsonFile = new JSON(prePath, fileName);
                for (int i = 1000000; i < RPF+1000000; i++) {
                    MeterReading meterReading = new MeterReading(i);
                    jsonFile.addMeterReading(meterReading);
                }
                jsonFile.write();
                jsonFile.copy(targetPath);
                break;
        }
    }
    private void generateCDRs(){
        switch (format){
            case "ASN.1":
                ASN1 asnFile = new ASN1(prePath, fileName, RPF);
                for (int i = 0; i < RPF; i++) {
                    CDR cdr = new CDR();
                    asnFile.addCDR(cdr);
                }
                asnFile.writeFileHeader();
                asnFile.close();
                asnFile.copy(targetPath);
                break;
            case "CSV":
                CSV csvFile = new CSV(prePath, fileName, delimiter);
                for (int i = 0; i < RPF; i++) {
                    CDR cdr = new CDR();
                    csvFile.addCDR(cdr);
                }
                try {
                    csvFile.getFileWriter().close();
                    csvFile.copy(targetPath);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                break;
        }

    }
    public void pause(){
        this.suspend();
    }
    public void play(){
        this.resume();
    }
}
