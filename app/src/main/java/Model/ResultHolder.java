package Model;

public class ResultHolder {

    String roll;
    int attendence=0,absence=0,marks=0;

    public ResultHolder(String roll, int attendence, int absence, int marks) {
        this.roll = roll;
        this.attendence = attendence;
        this.absence = absence;
        this.marks = marks;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public int getAttendence() {
        return attendence;
    }

    public void setAttendence(int attendence) {
        this.attendence = attendence;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}