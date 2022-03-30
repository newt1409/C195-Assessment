package model;
import Database.DBCountries;
import Database.DBDivisions;
import java.util.Calendar;
/**
 * Main class for Customers
 *@author Weston Brehe
 */
public class Customers {
    /**
     * Customer ID
     */
    private int customerId;
    /**
     * Customer Name
     */
    private String customerName;
    /**
     * Customer Address
     */
    private String customerAddress;
    /**
     * Customer Postal code
     */
    private String customerPostal;
    /**
     * Customer Phone number
     */
    private String customerPhone;
    /**
     * Customer State or province
     */
    private String customerDivision;
    /**
     * Customer Country
     */
    private String customerCountry;
    /**
     * Customer created date
     */
    private Calendar createDate;
    /**
     * Customer created by who
     */
    private String createdBy;
    /**
     * Customer last update date
     */
    private Calendar lastUpdate;
    /**
     * Customer last updated by who
     */
    private String lastUpdateBy;
    /**
     * Customer State or Province ID
     */
    private int divId;
    /**
     * Customer Country ID
     */
    private int countryId;

    /**
     * Main constructor for customer class
     * @param custID
     * @param custName
     * @param custAddress
     * @param custPostal
     * @param custPhone
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     * @param divId
     */
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
        //add data to class to make tableviews easier
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
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }
    /**
     * @return the customerPostal
     */
    public String getCustomerPostal() {
        return customerPostal;
    }
    /**
     * @return the customerPhone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }
    /**
     * @return the customerDivision
     */
    public String getCustomerDivision() {
        return customerDivision;
    }
    /**
     * @return the customerCountry
     */
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
