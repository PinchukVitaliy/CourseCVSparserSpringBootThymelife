package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/registration/register")
    private String getRegister(Model model) {
        model.addAttribute("contentPage", "/registration/register");
        return "default";
    }

    @PostMapping(value = "/registration/register")
    private String getRegisterSubmit(@ModelAttribute("user") User user) {
        userService.addUser(user);
        //model.addAttribute("contentPage", "/user/add");
        return "redirect:/login/login";
    }
}
