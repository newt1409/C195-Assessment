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

    private int userId;
    private int contactId;

    public Appointments(int appId, String appTitle, String appDesc, String appLocation, String appType, String appStart, String appEnd, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int userId, int contactId) {
        this.appId = userId;
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
        this.userId = userId;
        this.contactId = contactId;
    }
}
