package com.example.ui1.SelfAssessment;

public class SelfData {

    String status,date;

    public SelfData(){

    }

    public SelfData(String status, String date) {
        this.status = status;
        this.date = date;

    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}