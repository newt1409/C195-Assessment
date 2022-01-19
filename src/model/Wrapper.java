package model;

import java.util.Calendar;

public class Wrapper {
    private Customers custWrap;
    private Divisions divWrap;
    private Countries countryWrap;

    public Wrapper(Customers inCust, Divisions inDiv, Countries inCountry) {
        this.custWrap = inCust;
        this.divWrap = inDiv;
        this.countryWrap = inCountry;
    }

    public Integer getCustomerId () {return custWrap.getCustomerId();}
    public String getCustomerName () {return custWrap.getCustomerName();}
    public String getCustomerAddress () {return custWrap.getCustomerAddress();}
    public String getCustomerPostal () {return custWrap.getCustomerPostal();}
    public String getCustomerPhone () {return custWrap.getCustomerPhone();}
    public String getCustomerDiv () {return divWrap.getDivName();}
    public String getCustomerCountry () {return countryWrap.getCountryName();}
}
