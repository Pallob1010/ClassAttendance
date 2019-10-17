package Model;

import com.google.gson.annotations.Expose;

public class RegistrationResult {
    @Expose
    String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
