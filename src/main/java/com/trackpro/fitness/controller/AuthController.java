package com.trackpro.fitness.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trackpro.fitness.model.ExerciseLog;
import com.trackpro.fitness.model.User;
import com.trackpro.fitness.repository.ExerciseLogRepository;
import com.trackpro.fitness.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ExerciseLogRepository logRepo;


    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String fullname,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirm,
                               Model model) {
        if (!password.equals(confirm)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        if (userRepo.findByUsername(username) != null) {
            model.addAttribute("error", "Username already taken");
            return "register";
        }

        User user = new User();
        user.setFullname(fullname);
        user.setUsername(username);
        user.setPassword(password);
        userRepo.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        User user = userRepo.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        session.setAttribute("user", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<ExerciseLog> logs = logRepo.findByUser(user);
        model.addAttribute("logs", logs);
        return "dashboard";
    }

    @PostMapping("/log-exercise")
    public String logExercise(@RequestParam int bench,
                              @RequestParam int dead,
                              @RequestParam int squat,
                              HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        ExerciseLog log = new ExerciseLog();
        log.setUser(user);
        log.setLogDate(LocalDate.now());
        log.setBenchPress(bench);
        log.setDeadlift(dead);
        log.setSquats(squat);

        logRepo.save(log);
        return "redirect:/dashboard";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
