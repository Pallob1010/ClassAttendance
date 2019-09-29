package Model;

public class RollState {
    String Roll;
    String checked;

    public RollState(String roll, String checked) {
        Roll = roll;
        this.checked = checked;
    }

    public String getRoll() {
        return Roll;
    }

    public void setRoll(String roll) {
        Roll = roll;
    }

    public boolean isChecked() {
        return checked.equals("true");
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
