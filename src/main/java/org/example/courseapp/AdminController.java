package org.example.courseapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdministratorRepository adminRepo;

    public AdminController(AdministratorRepository adminRepo) {
        this.adminRepo = adminRepo;
        // Basic initial admin (Better than leaving it empty)
        if (adminRepo.findByUsername("admin").isEmpty()) {
            adminRepo.save(new Administrator("admin", "admin123"));
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        Optional<Administrator> admin = adminRepo.findByUsername(username);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            session.setAttribute("admin", admin.get());
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("admin", session.getAttribute("admin"));
        return "admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/admin/login";
    }
}
