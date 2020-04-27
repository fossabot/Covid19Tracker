package com.example.covid19tracker;

public class CountryModel implements Cloneable {
    static CountryModel countryModel;
    String country = "null";
    String total = "null";
    String today = "null";
    String dead = "null";
    String diedToday = "null";
    String recovered = "null";
    String active = "null";
    String critical = "null";
    String timestamp = "null";
    String countryCode = "null";

    public CountryModel() {

    }

    public CountryModel(String whichCountry, String total, String today, String dead, String diedToday, String recovered, String active, String critical) {
        this.country = whichCountry;
        this.total = total;
        this.today = today;
        this.dead = dead;
        this.diedToday = diedToday;
        this.recovered = recovered;
        this.active = active;
        this.critical = critical;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Object clone() throws CloneNotSupportedException {
        CountryModel cloned = (CountryModel) super.clone();
        cloned.setCountry(cloned.getCountry());
        cloned.setTotal(cloned.getTotal());
        cloned.setToday(cloned.getToday());
        cloned.setDead(cloned.getDead());
        cloned.setDiedToday(cloned.getDiedToday());
        cloned.setRecovered(cloned.getRecovered());
        cloned.setActive(cloned.getActive());
        cloned.setCritical(cloned.getCritical());
        return cloned;
    }

    public String toString() {
        return country + total + today + dead + diedToday + recovered + active + critical;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String whichCountry) {
        this.country = whichCountry;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getDiedToday() {
        return diedToday;
    }

    public void setDiedToday(String diedToday) {
        this.diedToday = diedToday;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

}
