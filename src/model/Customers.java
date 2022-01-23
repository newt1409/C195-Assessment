package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBCountries;
import Database.DBDivisions;
import com.mysql.cj.conf.IntegerProperty;
import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

/**
 *
 * @author carolyn.sher
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostal;
    private String customerPhone;
    private String customerDivision;
    private String customerCountry;

    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    private int divId;
    private int countryId;

    public Customers(int custID, String custName, String custAddress, String custPostal, String custPhone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int divId) {
        this.customerId = custID;
        this.customerName = custName;
        this.customerAddress = custAddress;
        this.customerPostal = custPostal;
        this.customerPhone = custPhone;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.divId = divId;
        try {
            this.customerDivision = (DBDivisions.getDivisionData(divId)).getDivName();
            this.countryId = (DBDivisions.getDivisionData(divId)).getCountryId();
            this.customerCountry = (DBCountries.getCountryData(countryId)).getCountryName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the appId
     */
    public int getCustomerId() { return customerId; }
    /**
     * @return the divId
     */
    public int getdivId() {
        return divId;
    }
    /**
     * @return the userName
     */
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public String getCustomerPostal() {
        return customerPostal;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public String getCustomerDivision() {
        return customerDivision;
    }
    public String getCustomerCountry() {
        return customerCountry;
    }
    /**
     * @return the createDate
     */
    public Calendar getCreateDate() {
        return createDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @return the lastUpdate
     */
    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return the lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

}
