package Model;

import com.google.gson.annotations.Expose;

public class LoginResult {
    @Expose
    String Name;
    @Expose
    String Number;
    @Expose
    String Password;

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
}
