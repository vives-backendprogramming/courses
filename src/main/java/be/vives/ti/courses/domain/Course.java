package be.vives.ti.courses.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int phase;

    @NotNull
    private Boolean theory;

    @ManyToMany(mappedBy = "courses")
    private List<Lector> lectors = new ArrayList<>();

    private Course() {
    }

    public Course(String name, int phase, Boolean theory) {
        this.name = name;
        this.phase = phase;
        this.theory = theory;
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

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public Boolean isTheory() {
        return theory;
    }

    public void setTheory(Boolean theory) {
        this.theory = theory;
    }
    
    public List<Lector> getLectors() {
        return lectors;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", theory=" + theory +
                '}';
    }
}
