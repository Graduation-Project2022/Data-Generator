package com.example.dataformat;

import com.example.recordtype.CDR;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ASN1 {
    String RPF;
    File file;
    BufferedWriter fileBuffer;
    String fileName, prePath;
    StringBuilder cdrBuilder;
    int CDRsLengthWithoutHeader;
    public ASN1(String prePath, String fileName, int RPF) {
        this.prePath = prePath;
        this.cdrBuilder = new StringBuilder();
        this.fileName = fileName + "_"+System.currentTimeMillis() + ".asn1";
        this.file = new File(prePath + "\\" + this.fileName);
        fileBuffer = null;
        this.RPF = intToHex(RPF, 8);
        try {
            fileBuffer = new BufferedWriter(new FileWriter(file));
            fileBuffer.write("LL LL LL LL 00 00 00 32 a2 a2 b6 2d da 00 b6 2d da 00 "+this.RPF+
                    " 00 00 00 01 04 a7 01 02 c7 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addCDR(CDR cdr){
        try {
            fileBuffer.write("\n" + generateEncodedCDR(cdr));
            cdrBuilder.setLength(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateEncodedCDR(CDR cdr){
        cdrBuilder.append(listOfCallingPartyAddress(cdr));
        cdrBuilder.append(calledPartyAddress(cdr));
        cdrBuilder.append(serviceRequestTimeStamp(cdr));
        cdrBuilder.append(duration(cdr));
        cdrBuilder.insert(0, addCDRHeader(cdrBuilder.toString()));
        return cdrBuilder.toString();
    }
    private String addCDRHeader(String cdrWithoutHeader){
        int CDRsLengthInBytes = (cdrWithoutHeader.length()+1)/3;
        /*
            This variable will be used in file header not CDR header.
            It's used to calculate the total length of CDRs without headers
        */
        CDRsLengthWithoutHeader += CDRsLengthInBytes;
        // Calculate CDR Length without spaces in terms of bytes (2 Chars)
        return intToHex(CDRsLengthInBytes, 4)+" a2 29 ";
    }

    private String listOfCallingPartyAddress(CDR cdr){
        return "a6 13 81 11 74 65 6c 3a 2b 32 30 31 " + cdr.getHexCompanyA() + stringToHex(cdr.getNumberAWithoutCompany());
    }
    private String stringToHex(String asciiField){
        StringBuilder returnString = new StringBuilder();
        for (char ch : asciiField.toCharArray()) {
            returnString.append(Integer.toHexString(ch)).append(" ");
        }
        return returnString.toString();
    }
    private String calledPartyAddress(CDR cdr){
        return "a7 13 81 11 74 65 6c 3a 2b 32 30 31 " + cdr.getHexCompanyB() + stringToHex(cdr.getNumberBWithoutCompany());
    }
    private String serviceRequestTimeStamp(CDR cdr){
        String datetime = cdr.getDateTime();
        return "89 09 " + datetime + " 2B 02 00 ";
    }
    private String duration(CDR cdr){
        return "9f 81 48 0" + (intToHex(cdr.getDuration(),0).length() +1)/3 + " " + intToHex(cdr.getDuration(),0);
    }
    private String intToHex(int integerField, int numberOfHexBytes){
        StringBuilder returnString = new StringBuilder(Integer.toHexString(integerField));
        if (numberOfHexBytes == 0) {// 0 means it doesn't matter the number of bytes, but it should be an even number
            if (returnString.length() % 2 != 0) {
                returnString.insert(0, "0");
            }
        } else {
            while (returnString.length() < numberOfHexBytes) {
                returnString.insert(0, "0");
            }
        }
        returnString = new StringBuilder(returnString.toString().replaceAll("..(?!$)", "$0 "));  // Add a space after every 2 letters
        return returnString.toString();
    }
    public void close(){
        if ( fileBuffer != null ) {
            try {
                fileBuffer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void copy(String path){
        try {
            Files.copy(Paths.get(prePath + "\\" + this.fileName), Paths.get(path + "\\" + this.fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeFileHeader(){
        try {
            fileBuffer.write("Kareem",0,5);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}