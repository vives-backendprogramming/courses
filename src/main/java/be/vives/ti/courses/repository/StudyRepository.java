package be.vives.ti.courses.repository;

import be.vives.ti.courses.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
