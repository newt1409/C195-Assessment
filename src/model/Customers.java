package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    private int id;
    private String customerName;
    private String customerAddress;
    private String customerPostal;
    private String customerPhone;

    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    private int divId;

    public Customers(int custID, String custName, String custAddress, String custPostal, String custPhone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int divId) {
        this.id = custID;
        this.customerName = custName;
        this.customerAddress = custAddress;
        this.customerPostal = custPostal;
        this.customerPhone = custPhone;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.divId = divId;
    }
    /**
     * @return the appId
     */
    public int getId() { return this.id; }
    /**
     * @return the divId
     */
    public int getdivId() {
        return divId;
    }
    /**
     * @return the userName
     */
    public String getCustName() {
        return customerName;
    }
    public String getCustAddress() {
        return customerAddress;
    }
    public String getCustPostal() {
        return customerPostal;
    }
    public String getCustPhone() { return customerPhone; }
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
