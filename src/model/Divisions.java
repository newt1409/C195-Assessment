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
public class Divisions {
    private int divId;
    private String divName;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    private int countryId;

    public Divisions(int divId, String divName, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int countryId) {
        this.divId = divId;
        this.divName = divName;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.countryId = countryId;
    }

}
