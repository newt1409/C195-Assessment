package model;
import java.util.Calendar;
/**
 * Main class for Countries
 *@author Weston Brehe
 */
public class Countries {
    /**
     * Country ID
     */
    private int countryId;
    /**
     * Country Name
     */
    private String countryName;
    /**
     * Country created date
     */
    private Calendar createDate;
    /**
     * Country created by who
     */
    private String createdBy;
    /**
     * Country last update date
     */
    private Calendar lastUpdate;
    /**
     * Country last updated by who
     */
    private String lastUpdateBy;

    /**
     * Main constructor for the Country class
     * @param countryId
     * @param countryName
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     */
    public Countries(int countryId, String countryName, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * @return the userId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param userId the userId to set
     */
    public void setCountryId(int userId) {
        this.countryId = userId;
    }

    /**
     * @return the userName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param userName the userName to set
     */
    public void setCountryName(String userName) {
        this.countryName = userName;
    }

    /**
     * @return the createDate
     */
    public Calendar getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the lastUpdate
     */
    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @param lastUpdateBy the lastUpdateBy to set
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
