package model;
import java.util.Calendar;
/**
 * Main class for States or Provinces
 *@author Weston Brehe
 */
public class Divisions {
    /**
     * State or Province ID
     */
    private int divId;
    /**
     * State or Province Name
     */
    private String divName;
    /**
     * State or Province Create date
     */
    private Calendar createDate;
    /**
     * State or Province created by who
     */
    private String createdBy;
    /**
     * State or Province last updated
     */
    private Calendar lastUpdate;
    /**
     * State or Province last updated by who
     */
    private String lastUpdateBy;
    /**
     * State or Province Country ID
     */
    private int countryId;

    /**
     * Main constructor for the State or Province class
     * @param divId
     * @param divName
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     * @param countryId
     */
    public Divisions(int divId, String divName, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int countryId) {
        this.divId = divId;
        this.divName = divName;

        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;

        this.countryId = countryId;
    }
    /**
     * @return divId
     */
    public int getDivId () { return divId;}
    /**
     * @return divName
     */
    public String getDivName () { return divName;}
    /**
     * @return countryId
     */
    public int getCountryId () {return countryId;}
}
