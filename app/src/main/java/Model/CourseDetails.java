package Model;

public class CourseDetails {
    String CourseCode,Series,Section;
    int marks;

    public CourseDetails(String courseCode, String series, String section,int marks) {
        CourseCode = courseCode;
        Series = series;
        Section = section;
        this.marks=marks;
    }

    public String getCourseCode() {
        return CourseCode;
    }


    public String getSeries() {
        return Series;
    }


    public String getSection() {
        return Section;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
