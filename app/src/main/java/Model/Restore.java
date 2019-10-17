package Model;

import com.google.gson.annotations.Expose;

public class Restore {

    @Expose
    String Number;
    @Expose
    String Series;
    @Expose
    String Section;
    @Expose
    String Course;
    @Expose
    String FirstRoll;
    @Expose
    String TotalStudents;
    @Expose
    String AttendanceMarks;

    public String getTotalStudents() {
        return TotalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        TotalStudents = totalStudents;
    }
    public String getAttendanceMarks() {
        return AttendanceMarks;
    }
    public void setAttendanceMarks(String attendanceMarks) {
        AttendanceMarks = attendanceMarks;
    }
    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getFirstRoll() {
        return FirstRoll;
    }

    public void setFirstRoll(String firstRoll) {
        FirstRoll = firstRoll;
    }


}
