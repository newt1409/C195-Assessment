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
    private int appId;
    private String appTitle;
    private String appDesc;
    private String appLocation;
    private String appType;
    private String appStart;
    private String appEnd;

    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;

    private int customerId;
    private int userId;
    private int contactId;

    public Appointments(int appId, String appTitle, String appDesc, String appLocation, String appType, String appStart, String appEnd, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) {
        this.appId = appId;
        this.appTitle = appTitle;
        this.appDesc = appDesc;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }
    /**
     * @return the appId
     */
    public int getAppId() {
        return appId;
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
    public int getuserId() {
        return userId;
    }
    /**
     * @return the contactId
     */
    public int getcontactId() {
        return contactId;
    }

    /**
     * @return the userName
     */
    public String getappTitle() {
        return appTitle;
    }

    public String getappDesc() {
        return appDesc;
    }
    public String getappLocation() {
        return appLocation;
    }
    public String getappType() { return appType; }
    public String getappStart() { return appStart; }
    public String getappEnd() {
        return appEnd;
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
