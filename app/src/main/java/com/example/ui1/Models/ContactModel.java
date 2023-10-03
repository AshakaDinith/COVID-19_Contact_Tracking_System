package com.example.ui1.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContactModel {
    private String macAddress;
    private LocalDateTime date;



    public ContactModel(String macAddress, LocalDateTime date) {
        this.macAddress = macAddress;
        this.date = date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ContactModel(String macAddress, String date) {
        this.macAddress = macAddress;
        this.date = LocalDateTime.parse(date);
    }

    public ContactModel() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
