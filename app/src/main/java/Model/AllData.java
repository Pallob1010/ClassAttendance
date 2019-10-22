package Model;

import com.google.gson.annotations.Expose;

public class AllData {
    @Expose
    String Id;
    @Expose
    String Roll;
    @Expose
    String Aday;
    @Expose
    String Bday;
    @Expose
    String Cday;
    @Expose
    String Dday;
    @Expose
    String Eday;

    public String getIds() {
        return Id;
    }

    public void setIds(String id) {
        Id = id;
    }

    public String getRolls() {
        return Roll;
    }

    public void setRolls(String roll) {
        Roll = roll;
    }

    public String getAdays() {
        return Aday;
    }

    public void setAdays(String aday) {
        Aday = aday;
    }

    public String getBday() {
        return Bday;
    }

    public void setBday(String bday) {
        Bday = bday;
    }

    public String getCday() {
        return Cday;
    }

    public void setCday(String cday) {
        Cday = cday;
    }

    public String getDday() {
        return Dday;
    }

    public void setDday(String dday) {
        Dday = dday;
    }

    public String getEday() {
        return Eday;
    }

    public void setEday(String eday) {
        Eday = eday;
    }
}
