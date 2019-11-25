package be.vives.ti.courses.repository;

import be.vives.ti.courses.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectorRepository extends JpaRepository<Lector, Long> {

    Optional<Lector> findByFirstNameAndLastName(String firstName, String lastName);

}
