package com.example.ui1.API;

public class ModelClass {
    private String country,cases,active,recovered,deaths;

    public ModelClass(String country, String cases, String active, String recovered, String deaths) {
        this.country = country;
        this.cases = cases;
        this.active = active;
        this.recovered = recovered;
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
}
