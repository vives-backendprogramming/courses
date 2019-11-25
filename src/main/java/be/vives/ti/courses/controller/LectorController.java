package be.vives.ti.courses.controller;

import be.vives.ti.courses.exceptions.ResourceNotFoundException;
import be.vives.ti.courses.repository.LectorRepository;
import be.vives.ti.courses.responses.LectorResponse;
import be.vives.ti.courses.responses.LectorWithCoursesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lectors")
@CrossOrigin("*")
public class LectorController {

    private final LectorRepository lectorRepository;

    public LectorController(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }

    @GetMapping
    public Page<LectorResponse> findAllLectors(Pageable pageable){
        return lectorRepository.findAll(pageable).map(LectorResponse::new);
    }

    @GetMapping("/{lectorId}")
    public LectorResponse retrieveLectorById(@PathVariable(name = "lectorId") Long lectorId) {
        return new LectorResponse(lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString())));
    }

    @GetMapping("/{lectorId}/courses")
    public LectorWithCoursesResponse retrieveLectorByIdWithCourses(@PathVariable(name = "lectorId") Long lectorId) {
        return new LectorWithCoursesResponse(lectorRepository.findById(lectorId).orElseThrow(() -> new ResourceNotFoundException(lectorId.toString())));
    }




}
