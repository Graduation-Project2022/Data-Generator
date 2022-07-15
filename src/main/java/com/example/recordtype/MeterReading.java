package com.example.recordtype;

import java.util.Random;

public class MeterReading {
    String recordTypeID, meterSerial, UOM;
    Long startDateTime, endDateTime;
    float reading;
    Random random;

     public MeterReading(int id) {
         this.random = new Random();
         this.recordTypeID = setRecordTypeID();
         this.endDateTime = setEndDateTime();
         this.startDateTime = setStartDateTime();
         this.meterSerial = setMeterSerial(id);
         this.UOM = setUOM();
         this.reading = setReading();
     }

     // Customized Setters
    private String setRecordTypeID(){
        return "U";
    }
    private Long setEndDateTime(){
        return System.currentTimeMillis() / 1000L;
    }
    private Long setStartDateTime(){
        return endDateTime - 900;
    }
    private String setMeterSerial(int id){
        return "SER" + id;
    }
    private String setUOM(){
        return "KWH";
    }
    private float setReading(){
        return (random.nextFloat() * ((30 - 1) + 1)) + 1;
    }

    // Customized getters
    public String getRecordTypeID() {
        return recordTypeID;
    }
    public String getMeterSerial() {
        return meterSerial;
    }
    public String getUOM() {
        return UOM;
    }
    public Long getStartDateTime() {
        return startDateTime;
    }
    public Long getEndDateTime() {
        return endDateTime;
    }
    public float getReading() {
        return reading;
    }
}