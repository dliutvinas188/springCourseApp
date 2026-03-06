package org.example.courseapp;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/courses")
public class CourseWebController {

    private final CourseRepository repo;

    public CourseWebController(CourseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(@RequestParam(name = "search", required = false) String search, Model model) {
        Iterable<Course> iterable;
        if (search != null && !search.trim().isEmpty()) {
            iterable = repo.findByTitleContainingIgnoreCaseOrCourseCodeContainingIgnoreCase(search, search);
        } else {
            iterable = repo.findAll();
        }
        List<Course> courses = StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
        model.addAttribute("courses", courses);
        model.addAttribute("search", search);
        return "courses/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("mode", "create");
        return "courses/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("course") Course course,
                         BindingResult result,
                         Model model) {
        if (repo.findByCourseCode(course.getCourseCode()).isPresent()) {
            result.rejectValue("courseCode", "error.course", "Course code already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("mode", "create");
            return "courses/form";
        }

        repo.save(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Course course = repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course not found: " + id));

        model.addAttribute("course", course);
        model.addAttribute("mode", "edit");
        return "courses/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("course") Course updated,
                         BindingResult result,
                         Model model) {
        repo.findByCourseCode(updated.getCourseCode()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                result.rejectValue("courseCode", "error.course", "Course code already exists");
            }
        });

        if (result.hasErrors()) {
            updated.setId(id);
            model.addAttribute("mode", "edit");
            return "courses/form";
        }

        Course course = repo.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course not found: " + id));

        course.setTitle(updated.getTitle());
        course.setInstructor(updated.getInstructor());
        course.setCourseCode(updated.getCourseCode());
        course.setDescription(updated.getDescription());
        repo.save(course);

        return "redirect:/courses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/courses";
    }
}