package be.vives.ti.courses.repository;

import be.vives.ti.courses.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    List<Course> findByTheory(Boolean isTheory);
}
