package org.example.courseapp;

import jakarta.persistence.GeneratedValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
public class CourseWebController {
    private final CourseRepository repo;

    public CourseWebController(CourseRepository repo){
        this.repo = repo;
    }

    //List: show all courses
    @GetMapping
    public String list(Model model){
        model.addAttribute("courses", repo.findAll());
        return "courses/list";
    }
    //Create form. show and empty form
    @GetMapping("/new")
    public String newForm(Model model){
        model.addAttribute("course", new Course());
        model.addAttribute("mode", "create");
        return "courses/form";
    }
    //Create submit
    @PostMapping()
    public String create(@ModelAttribute("course") Course course){
        repo.save(course);
        return "redirect:/courses";
    }
    //Edit form
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        Course course = repo.findById(id).orElseThrow(()->
                new IllegalArgumentException("Course not found"+id));
        model.addAttribute("course", course);
        model.addAttribute("mode", "edit");
        return "courses/form";
    }
    //Update submit
    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("course") Course updated){
        Course course = repo.findById(id).orElseThrow(()->
                new IllegalArgumentException("Course not found: "+id));
        course.setTitle(updated.getTitle());
        course.setInstructor(updated.getInstructor());
        repo.save(course);

        return "redirect:/courses";
    }
    //Delete
    @PostMapping("/{id}/delete")
        public String delete(@PathVariable Long id){
            repo.deleteById(id);
            return "redirect:/courses";
        }
    }

