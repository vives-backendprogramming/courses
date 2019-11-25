package be.vives.ti.courses;

import be.vives.ti.courses.domain.Course;
import be.vives.ti.courses.domain.Gender;
import be.vives.ti.courses.domain.Lector;
import be.vives.ti.courses.domain.Study;
import be.vives.ti.courses.repository.CourseRepository;
import be.vives.ti.courses.repository.LectorRepository;
import be.vives.ti.courses.repository.StudyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class CommandLineRunnerAtStartup implements CommandLineRunner {

    private final StudyRepository studyRepository;
    private final LectorRepository lectorRepository;
    private final CourseRepository courseRepository;

    public CommandLineRunnerAtStartup(StudyRepository studyRepository, LectorRepository lectorRepository, CourseRepository courseRepository) {
        this.studyRepository = studyRepository;
        this.lectorRepository = lectorRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Study ti = studyRepository.save(new Study("Toegepaste Informatica", new Lector("Martin", "Lippert", LocalDate.now().minusMonths(4).minusYears(35).minusDays(4), Gender.M)));

        Lector pieter = lectorRepository.save(new Lector("Pieter", "Humphrey", LocalDate.now().minusMonths(3).minusYears(27).minusDays(13), Gender.M));
        Lector josh = lectorRepository.save(new Lector("Josh", "Long", LocalDate.now().minusMonths(9).minusYears(27).minusDays(27), Gender.M));
        Lector ria = lectorRepository.save(new Lector("Ria", "Stein", LocalDate.now().minusMonths(1).minusYears(30).minusDays(3), Gender.V));

        courseRepository.save(new Course("Backend Programming", 3, false));
        courseRepository.save(new Course("iOS", 3, false));
        courseRepository.save(new Course("Android", 2, false));
        courseRepository.save(new Course("FoP2", 2, true));

        Optional<Course> iOS = courseRepository.findByName("iOS");
        Optional<Course> android = courseRepository.findByName("Android");
        Optional<Course> FoP2 = courseRepository.findByName("FoP2");
        Optional<Course> backend = courseRepository.findByName("Backend Programming");

        pieter.addCourse(FoP2.get());
        pieter.addCourse(backend.get());
        ria.addCourse(FoP2.get());
        josh.addCourse(iOS.get());
        josh.addCourse(android.get());

        Course sql = new Course("SQL", 1, false);

        lectorRepository.save(pieter);
        lectorRepository.save(ria);
        lectorRepository.save(josh);

        courseRepository.save(iOS.get());
        courseRepository.save(android.get());
        courseRepository.save(backend.get());

        ti.addCourse(FoP2.get());
        ti.addCourse(iOS.get());
        ti.addCourse(android.get());
        ti.addCourse(backend.get());
        ti.addCourse(FoP2.get());
        ti.addCourse(sql);

        studyRepository.save(ti);
    }
}
