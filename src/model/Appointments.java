package model;
import java.util.Calendar;
/**
 * Main class for appointments
 *@author Weston Brehe
 */
public class Appointments {
    /**
     * Appointment ID
     */
    private int id;
    /**
     * Appointment Title
     */
    private String title;
    /**
     * Appointment Description
     */
    private String description;
    /**
     * Appointment Location
     */
    private String location;
    /**
     * Appointment Type
     */
    private String type;
    /**
     * Appointment Start time
     */
    private String start;
    /**
     * Appointment End time
     */
    private String end;
    /**
     * calendar format for the creation date
     */
    private Calendar createDate;
    /**
     * User who created the appointment
     */
    private String createdBy;
    /**
     * calendar format for the last time it was updated
     */
    private Calendar lastUpdate;
    /**
     * User who last updated the appointment
     */
    private String lastUpdateBy;
    /**
     * Customer ID who is associated with the Appointment
     */
    private int customerId;
    /**
     * Customer name who is associated with the Appointment
     */
    private String customerName;
    /**
     * User ID of who is associated with the Appointment
     */
    private int userId;
    /**
     * Contact ID of who is associated with the Appointment
     */
    private int contactId;

    /**
     * Constructor of a normal appointment with all data
     * @param appId
     * @param appTitle
     * @param appDesc
     * @param appLocation
     * @param appType
     * @param appStart
     * @param appEnd
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy
     * @param customerId
     * @param userId
     * @param contactId
     */
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

    /**
     * Constructor for partial appointment data
     * @param inAppID
     * @param appTitle
     * @param appDesc
     * @param appLocation
     * @param appType
     * @param appStart
     * @param appEnd
     * @param inCustomer
     */
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
     * @return the title
     */
    public String getAppTitle() {
        return title;
    }
    /**
     * @return the title
     */
    public String getAppDesc() {
        return description;
    }
    /**
     * @return the title
     */
    public String getAppLocation() {
        return location;
    }
    /**
     * @return the title
     */
    public String getAppType() { return type; }
    /**
     * @return the start
     */
    public String getAppStart() { return start; }
    /**
     * @return the end
     */
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
