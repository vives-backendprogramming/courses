package be.vives.ti.courses.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="head_id")
    private Lector headOfStudy;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "study_id")
    private List<Course> courses = new ArrayList();

    public Study() {
    }

    public Study(String name, Lector headOfStudy) {
        this.name = name;
        this.headOfStudy = headOfStudy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lector getHeadOfStudy() {
        return headOfStudy;
    }

    public void setHeadOfStudy(Lector headOfStudy) {
        this.headOfStudy = headOfStudy;
    }

    public void addCourse(Course course){
        this.courses.add(course);
    }
}
