package model;
/**
 * Main class for Reporting on the different tabs
 *@author Weston Brehe
 */
public class Reports {
    /**
     * Appointment by Type value Months
     */
    private String rptAppByType_Months;
    /**
     * Appointment by Type value Type
     */
    private String rptAppByType_Type;
    /**
     * Appointment by Type value Count
     */
    private int rptAppByType_Count;

    /**
     * Contact Schedule value Contact Name
     */
    private String rptContactSchedule_Contact;
    /**
     * Contact Schedule value Appointment Title
     */
    private String rptContactSchedule_Title;
    /**
     * Contact Schedule value Appointment Type
     */
    private String rptContactSchedule_Type;
    /**
     * Contact Schedule value Appointment Start
     */
    private String rptContactSchedule_Start;
    /**
     * Contact Schedule value Appointment End
     */
    private String rptContactSchedule_End;

    /**
     * Appointment by Customer value Month
     */
    private String rptAppByCustomer_Month;
    /**
     * Appointment by Customer value Name
     */
    private String rptAppByCustomer_Name;
    /**
     * Appointment by Customer value Phone
     */
    private String rptAppByCustomer_Phone;
    /**
     * Appointment by Customer value State or Province
     */
    private String rptAppByCustomer_Div;
    /**
     * Appointment by Customer value Country
     */
    private String rptAppByCustomer_Country;
    /**
     * Appointment by Customer value Count
     */
    private int rptAppByCustomer_count;

    /**
     * Constructor for Appointment by Type
     * @param inMonth
     * @param inType
     * @param inCount
     */
    public Reports (String inMonth, String inType, int inCount) {
        this.rptAppByType_Months = inMonth;
        this.rptAppByType_Type = inType;
        this.rptAppByType_Count = inCount;
    }

    /**
     * Constructor for Contact Schedule
     * @param inContact
     * @param inTitle
     * @param inType
     * @param inStart
     * @param inEnd
     */
    public Reports (String inContact, String inTitle, String inType, String inStart, String inEnd ) {
        this.rptContactSchedule_Contact = inContact;
        this.rptContactSchedule_Title = inTitle;
        this.rptContactSchedule_Type = inType;
        this.rptContactSchedule_Start = inStart;
        this.rptContactSchedule_End = inEnd;
    }

    /**
     * Constructor for Appointments by Customer
     * @param inMonth
     * @param inCustName
     * @param inPhone
     * @param inDiv
     * @param inCountry
     * @param inCount
     */
    public Reports (String inMonth, String inCustName, String inPhone, String inDiv, String inCountry, int inCount){
        this.rptAppByCustomer_Month = inMonth;
        this.rptAppByCustomer_Name = inCustName;
        this.rptAppByCustomer_Phone = inPhone;
        this.rptAppByCustomer_Div = inDiv;
        this.rptAppByCustomer_Country = inCountry;
        this.rptAppByCustomer_count = inCount;
    }

    /**
     * @return rptAppByType_Months
     */
    public String getRptAppByType_Months () { return this.rptAppByType_Months; }
    /**
     * @return rptAppByType_Type
     */
    public String getRptAppByType_Type () { return this.rptAppByType_Type; }
    /**
     * @return rptAppByType_Count
     */
    public int getRptAppByType_Count () { return this.rptAppByType_Count; }

    /**
     * @return rptContactSchedule_Contact
     */
    public String getRptContactSchedule_Contact () { return this.rptContactSchedule_Contact; }
    /**
     * @return rptContactSchedule_Title
     */
    public String getRptContactSchedule_Title () { return this.rptContactSchedule_Title; }
    /**
     * @return rptContactSchedule_Type
     */
    public String getRptContactSchedule_Type () { return this.rptContactSchedule_Type; }
    /**
     * @return rptContactSchedule_Start
     */
    public String getRptContactSchedule_Start () { return this.rptContactSchedule_Start; }
    /**
     * @return rptContactSchedule_End
     */
    public String getRptContactSchedule_End () { return this.rptContactSchedule_End; }

    /**
     * @return rptAppByCustomer_Month
     */
    public String getRptAppByCustomer_Month() { return this.rptAppByCustomer_Month; }
    /**
     * @return rptAppByCustomer_Name
     */
    public String getRptAppByCustomer_Name() { return this.rptAppByCustomer_Name; }
    /**
     * @return rptAppByCustomer_Phone
     */
    public String getRptAppByCustomer_Phone() { return rptAppByCustomer_Phone; }
    /**
     * @return rptAppByCustomer_Div
     */
    public String getRptAppByCustomer_Div() { return this.rptAppByCustomer_Div; }
    /**
     * @return rptAppByCustomer_Country
     */
    public String getRptAppByCustomer_Country() { return this.rptAppByCustomer_Country; }
    /**
     * @return rptAppByCustomer_count
     */
    public int getRptAppByCustomer_Count() { return this.rptAppByCustomer_count; }


    }

