package pink.coursework.csvparser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    private String index(Model model) {
        model.addAttribute("contentPage", "/home/index");
        return "default";
    }

    @GetMapping("/home/admin")
    private String admin(Model model) {
        model.addAttribute("contentPage", "/home/admin");
        return "default";
    }
}
