package be.vives.ti.courses.controller;

import be.vives.ti.courses.domain.Course;
import be.vives.ti.courses.domain.Gender;
import be.vives.ti.courses.domain.Lector;
import be.vives.ti.courses.exceptions.BadRequestException;
import be.vives.ti.courses.exceptions.ResourceNotFoundException;
import be.vives.ti.courses.repository.CourseRepository;
import be.vives.ti.courses.repository.LectorRepository;
import be.vives.ti.courses.request.CourseRequest;
import be.vives.ti.courses.request.LectorRequest;
import be.vives.ti.courses.responses.LectorResponse;
import be.vives.ti.courses.responses.LectorWithCoursesResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/lectors")
@CrossOrigin("*")
public class LectorController {

    private final LectorRepository lectorRepository;
    private final CourseRepository courseRepository;

    public LectorController(LectorRepository lectorRepository, CourseRepository courseRepository) {
        this.lectorRepository = lectorRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public Page<LectorResponse> findAllLectors(Pageable pageable){
        return lectorRepository.findAll(pageable).map(LectorResponse::new);
    }

    @GetMapping("/{lectorId}")
    public LectorResponse retrieveLectorById(@PathVariable(name = "lectorId") Long lectorId) {
        return new LectorResponse(lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector")));
    }

    @GetMapping("/{lectorId}/courses")
    public LectorWithCoursesResponse retrieveLectorByIdWithCourses(@PathVariable(name = "lectorId") Long lectorId) {
        return new LectorWithCoursesResponse(lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector")));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createLector(@RequestBody @Valid LectorRequest lectorRequest) {
        Lector lector = new Lector(
                lectorRequest.getFirstName(),
                lectorRequest.getLastName(),
                lectorRequest.getBirthDate(),
                Gender.valueOf(lectorRequest.getGender())
        );
        lector.setGsmNumber(lectorRequest.getGsmNumber());
        Lector l = lectorRepository.save(lector);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(l.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{lectorId}")
    public LectorResponse putLector(@PathVariable(name = "lectorId") Long lectorId,
                                    @RequestBody @Valid LectorRequest lectorRequest) {
        Lector l = lectorRepository.findById(Long.parseLong(lectorId.toString())).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector"));

        l.setFirstName(lectorRequest.getFirstName());
        l.setLastName(lectorRequest.getLastName());
        l.setBirthDate(lectorRequest.getBirthDate());
        l.setGender(Gender.valueOf(lectorRequest.getGender()));
        l.setGsmNumber(lectorRequest.getGsmNumber());

        return new LectorResponse(lectorRepository.save(l));
    }

    @PatchMapping("/{lectorId}")
    public LectorResponse patchLector(@PathVariable("lectorId") Long lectorId,
                                      @RequestBody LectorRequest lectorRequest) {
        Lector l = lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector"));
        if (lectorRequest.getFirstName() != null) {
            l.setFirstName(lectorRequest.getFirstName());
        }
        if (lectorRequest.getLastName() != null) {
            l.setLastName(lectorRequest.getLastName());
        }
        if (lectorRequest.getBirthDate() != null) {
            l.setBirthDate(lectorRequest.getBirthDate());
        }
        if (lectorRequest.getGender() != null) {
            l.setGender(Gender.valueOf(lectorRequest.getGender()));
        }
        if (lectorRequest.getGsmNumber() != null) {
            l.setGsmNumber(lectorRequest.getGsmNumber());
        }

        return new LectorResponse(lectorRepository.save(l));
    }

    @DeleteMapping("/{lectorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLector(@PathVariable("lectorId") Long lectorId) {
        Lector l = lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector"));

        if (l.getStudy() != null) {
            throw new BadRequestException();
        }

        try {
            lectorRepository.deleteById(lectorId);
        } catch (EmptyResultDataAccessException e) {
            // fine
        }
    }

    @PostMapping("/{lectorId}/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourseToLector(@PathVariable(name = "lectorId") Long lectorId, @RequestBody @Valid CourseRequest courseRequest){
        Lector lector = lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector"));
        Course course = courseRepository.findById(courseRequest.getId()).orElseThrow(() -> new ResourceNotFoundException(courseRequest.getId().toString(), "course"));
        lector.addCourse(course);
        lectorRepository.save(lector);
    }

    @DeleteMapping("/{lectorId}/courses/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseOfLector(@PathVariable(name = "lectorId") Long lectorId,
                                     @PathVariable(name = "courseId") Long courseId) {
        Lector lector = lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString(), "lector"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException(courseId.toString(), "course"));
        lector.removeCourse(course);
        lectorRepository.save(lector);
    }


}
