package be.vives.ti.courses.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Lecturer")
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String gsmNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "url_photo")
    private String url;

    @OneToOne(mappedBy = "headOfStudy")
    private Study study;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "lecturer_courses",
               joinColumns = {@JoinColumn(name="lecturer_id")},
               inverseJoinColumns = {@JoinColumn(name="course_id")})
    private List<Course> courses = new ArrayList<>();

    private Lector() {
    }

    public Lector(String firstName, String lastName, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGsmNumber() {
        return gsmNumber;
    }

    public void setGsmNumber(String gsmNumber) {
        this.gsmNumber = gsmNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Study getStudy() {
        return study;
    }

    public void addCourse(Course course) {
        this.getCourses().add(course);
        //course.getLectors().add(this);
    }

    public void removeCourse(Course course) {
        this.getCourses().remove(course);
        //course.getLectors().remove(this);
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
