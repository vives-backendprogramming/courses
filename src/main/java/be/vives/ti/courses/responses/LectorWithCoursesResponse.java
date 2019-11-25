package be.vives.ti.courses.responses;

import be.vives.ti.courses.domain.Course;
import be.vives.ti.courses.domain.Lector;

import java.util.List;
import java.util.stream.Collectors;

public class LectorWithCoursesResponse extends LectorResponse {

    private List<Course> courses;

    public LectorWithCoursesResponse(Lector lector) {
        super(lector);
        this.courses = lector.getCourses();
    }

    public List<CourseResponse> getCourses() {
        return courses.stream().map(CourseResponse::new).collect(Collectors.toList());
    }
}
