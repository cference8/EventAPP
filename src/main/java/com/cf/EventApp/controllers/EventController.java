package com.cf.EventApp.controllers;

import com.cf.EventApp.models.Event;
import com.cf.EventApp.models.User;
import com.cf.EventApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Controller
public class EventController {

    @Autowired
    UserService userService;

    Set<ConstraintViolation<User>> violations = new HashSet<>();

    @GetMapping("/event")
    public String displayEventPage() {
        return "event";
    }

    @GetMapping("/createEvent")
    public String displayCreateEventPage(Model model) {
        Event blankEvent = new Event();

        blankEvent.setStart_date(LocalDate.now());
        blankEvent.setStart_time(LocalTime.now());

        blankEvent.setEnd_date(LocalDate.now());
        blankEvent.setEnd_time(LocalTime.now());

        model.addAttribute("event", blankEvent);
        model.addAttribute("errors", violations);
        return "createEvent";
    }

    @PostMapping("/createEvent")
    public String createEvent(Event event, HttpServletRequest request, Model m) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        User userId = userService.getUserByUsername(user.getUsername());

        event.setUser(userId);

        return "createEvent";
    }
}
