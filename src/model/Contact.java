package model;
/**
 * Main class for Contacts
 *@author Weston Brehe
 */
public class Contact {
    /**
     * Contact ID
     */
    private int contactId;
    /**
     * Contact Name
     */
    private String contactName;
    /**
     * Contact Email
     */
    private String contactEmail;

    /**
     * Main constructor for contact class
     * @param contactId
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return the contactId
     */
    public int getContactID() { return contactId; }
    /**
     * @return the contactName
     */
    public String getContactName() { return contactName; }
    /**
     * @return the contactEmail
     */
    public String getContactEmail() { return contactEmail; }
}
