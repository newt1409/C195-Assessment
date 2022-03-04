package model;

public class Reports {
    private String rptAppByType_Months;
    private String rptAppByType_Type;
    private int rptAppByType_Count;

    private String rptContactSchedule_Contact;
    private String rptContactSchedule_Title;
    private String rptContactSchedule_Type;
    private String rptContactSchedule_Start;
    private String rptContactSchedule_End;

    private String rptAppByCustomer_Month;
    private String rptAppByCustomer_Name;
    private String rptAppByCustomer_Phone;
    private String rptAppByCustomer_Div;
    private String rptAppByCustomer_Country;
    private int rptAppByCustomer_count;

    public Reports (String inMonth, String inType, int inCount) {
        this.rptAppByType_Months = inMonth;
        this.rptAppByType_Type = inType;
        this.rptAppByType_Count = inCount;
    }

    public Reports (String inContact, String inTitle, String inType, String inStart, String inEnd ) {
        this.rptContactSchedule_Contact = inContact;
        this.rptContactSchedule_Title = inTitle;
        this.rptContactSchedule_Type = inType;
        this.rptContactSchedule_Start = inStart;
        this.rptContactSchedule_End = inEnd;
    }

    public Reports (String inMonth, String inCustName, String inPhone, String inDiv, String inCountry, int inCount){
        this.rptAppByCustomer_Month = inMonth;
        this.rptAppByCustomer_Name = inCustName;
        this.rptAppByCustomer_Phone = inPhone;
        this.rptAppByCustomer_Div = inDiv;
        this.rptAppByCustomer_Country = inCountry;
        this.rptAppByCustomer_count = inCount;
    }

    public String getRptAppByType_Months () { return this.rptAppByType_Months; }
    public String getRptAppByType_Type () { return this.rptAppByType_Type; }
    public int getRptAppByType_Count () { return this.rptAppByType_Count; }

    public String getRptContactSchedule_Contact () { return this.rptContactSchedule_Contact; }
    public String getRptContactSchedule_Title () { return this.rptContactSchedule_Title; }
    public String getRptContactSchedule_Type () { return this.rptContactSchedule_Type; }
    public String getRptContactSchedule_Start () { return this.rptContactSchedule_Start; }
    public String getRptContactSchedule_End () { return this.rptContactSchedule_End; }

    public String getRptAppByCustomer_Month() { return this.rptAppByCustomer_Month; }
    public String getRptAppByCustomer_Name() { return this.rptAppByCustomer_Name; }
    public String getRptAppByCustomer_Phone() { return rptAppByCustomer_Phone; }
    public String getRptAppByCustomer_Div() { return this.rptAppByCustomer_Div; }
    public String getRptAppByCustomer_Country() { return this.rptAppByCustomer_Country; }
    public int getRptAppByCustomer_Count() { return this.rptAppByCustomer_count; }


    }

