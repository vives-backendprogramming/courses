package be.vives.ti.courses.responses;

import be.vives.ti.courses.domain.Lector;

import java.time.LocalDate;

public class LectorResponse {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String url;

    public LectorResponse(Lector lector) {
        this.id = lector.getId();
        this.name = lector.getLastName() + " " + lector.getFirstName();
        this.birthDate = lector.getBirthDate();
        this.gender = lector.getGender().name();
        this.url = lector.getUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getUrl() {
        return url;
    }
}
