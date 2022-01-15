package edu.neu.customertracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

  @GetMapping("/homepage")
  public String showHomePage() {
    return "home";
  }

  @GetMapping("/leaders")
  public String showLeaders() {
    return "leaders";
  }

  @GetMapping("/systems")
  public String showSystems() {
    return "systems";
  }
}
