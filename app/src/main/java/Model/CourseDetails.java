package Model;

public class CourseDetails {
    String CourseCode,Series,Section;

    public CourseDetails(String courseCode, String series, String section) {
        CourseCode = courseCode;
        Series = series;
        Section = section;
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

}
