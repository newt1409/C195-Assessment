package model;

public class Reports {
    private String rptAppByType_Months;
    private String rptAppByType_Type;
    private int rptAppByType_Count;

    public Reports (String inMonth, String inType, int inCount) {
        this.rptAppByType_Months = inMonth;
        this.rptAppByType_Type = inType;
        this.rptAppByType_Count = inCount;
    }

    public String getRptAppByType_Months () { return this.rptAppByType_Months; }
    public String getRptAppByType_Type () { return this.rptAppByType_Type; }
    public int getRptAppByType_Count () { return this.rptAppByType_Count; }
}
