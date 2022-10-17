package com.cf.EventApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    @GetMapping("/event")
    public String displayEventPage() {
        return "event";
    }

    @GetMapping("/createEvent")
    public String displayCreateEventPage() { return "createEvent"; }
}
