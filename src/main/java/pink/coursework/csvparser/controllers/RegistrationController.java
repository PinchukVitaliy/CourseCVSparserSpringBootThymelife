package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private String getRegisterSubmit(@ModelAttribute("user") User user, Model model) {
        boolean flag = userService.addUser(user);
        if(flag){
            model.addAttribute("user", user);
            model.addAttribute("contentPage", "/registration/activation");
        }else{
            model.addAttribute("error", true);
            model.addAttribute("contentPage", "/registration/register");
        }
        return "default";
    }

    @GetMapping(value = "/registration/active/{code}")
    private String activate(Model model, @PathVariable("code") String code){
        boolean isActivate = userService.getActivate(code);
        if(isActivate){
            model.addAttribute("message", "User successfully activated");
            model.addAttribute("contentPage", "/login/login");
        }else{
            model.addAttribute("message", "Activation code is not found!");
            model.addAttribute("contentPage", "/login/login");
        }

        return "default";
    }
}
