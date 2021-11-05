package model;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    private int divId;

    public Customers(int customerId, String customerName, String customerAddress, String customerPostal, String customerPhone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int divId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostal = customerPostal;
        this.customerPhone = customerPhone;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.divId = divId;
    }
    /**
     * @return the appId
     */
    public int getCustomerId() {
        return customerId;
    }
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
    public String getCustomerPhone() { return customerPhone; }
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
