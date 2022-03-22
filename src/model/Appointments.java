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
public class Appointments {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;

    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    private int customerId;
    private String customerName;
    private int userId;
    private int contactId;

    public Appointments(int appId, String appTitle, String appDesc, String appLocation, String appType, String appStart, String appEnd, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) {
        this.id = appId;
        this.title = appTitle;
        this.description = appDesc;
        this.location = appLocation;
        this.type = appType;
        this.start = appStart;
        this.end = appEnd;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
    public Appointments(int inAppID, String appTitle, String appDesc, String appLocation, String appType, String appStart, String appEnd, String inCustomer) {
        this.id = inAppID;
        this.title = appTitle;
        this.description = appDesc;
        this.location = appLocation;
        this.type = appType;
        this.start = appStart;
        this.end = appEnd;
        this.customerName = inCustomer;
    }

    /**
     * @return the appId
     */
    public int getAppId() {
        return id;
    }
    /**
     * @return the customerId
     */
    public int getcustomerId() {
        return customerId;
    }
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @return the contactId
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * @return the userName
     */
    public String getAppTitle() {
        return title;
    }
    public String getAppDesc() {
        return description;
    }
    public String getAppLocation() {
        return location;
    }
    public String getAppType() { return type; }
    public String getAppStart() { return start; }
    public String getAppEnd() {
        return end;
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
