package Model;

import com.google.gson.annotations.Expose;

public class LoginResult {
    @Expose
    String Name;
    @Expose
    String Number;
    @Expose
    String Password;
    @Expose
    String Designation;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
