package com.example.recordtype;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SplittableRandom;
public class CDR {
    String numberA,  numberB, dateTime;
    int duration;
    final String[] companiesDigits = {"010","011","012","015"};
    Date date;
    public CDR(){
        this.numberA = GeneratePhone();
        this.numberB = GeneratePhone();
        this.date = new Date();
        this.dateTime = setDateTime(date);
        this.duration = setDuration();
    }
    private String GeneratePhone(){
        return companiesDigits[new SplittableRandom().nextInt(0, 4)]
                + String.valueOf(new SplittableRandom().nextInt(100000000, 199999999)).substring(1);
    }
    private String setDateTime(Date date){
        return new SimpleDateFormat("yy MM dd HH mm ss").format(date);
    }
    private int setDuration(){
        return new SplittableRandom().nextInt(100001, 360000000);
    }
    public String getNumberA() {
        return numberA;
    }
    public String getNumberB() {
        return numberB;
    }
    public String getDateTime() {
        return dateTime;
    }
    public int getDuration() {
        return duration;
    }

    public String getHexCompanyA(){
        char company = this.getNumberA().charAt(2);
        if (company == '0'){
            return "30 ";
        }
        else if (company == '1'){
            return "31 ";
        }
        else if (company == '2'){
            return "32 ";
        }
        else {
            return "35 ";
        }
    }
    public String getNumberAWithoutCompany(){
        return getNumberA().substring(3);
    }
    public String getHexCompanyB(){
        char company = this.getNumberB().charAt(2);
        if (company == '0'){
            return "30 ";
        }
        else if (company == '1'){
            return "31 ";
        }
        else if (company == '2'){
            return "32 ";
        }
        else {
            return "35 ";
        }
    }
    public String getNumberBWithoutCompany(){
        return getNumberB().substring(3);
    }
}

