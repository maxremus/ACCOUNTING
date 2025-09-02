package accounting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/uslugi")
    public String services() { return "uslugi"; }

    @GetMapping("/za-nas")
    public String about() { return "za-nas"; }

    // МАХНАТ /kontakt – вече е в ContactController

}
