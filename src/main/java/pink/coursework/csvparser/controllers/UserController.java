package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.UserService;

import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/users/{page}")
    private String getUsersPaginationList(Model model, @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", userService.pages());
        model.addAttribute("users", userService.listUsers(page));
        model.addAttribute("contentPage", "/user/users");
        return "default";
    }

    @GetMapping("/user/details/{id}")
    private String details(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getDetails(id));
        model.addAttribute("contentPage", "/user/details");
        return "default";
    }

    @GetMapping("/user/edit/{id}")
    private String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getDetails(id));
        model.addAttribute("contentPage", "/user/edit");
        return "default";

    }

    @PostMapping("/user/edit")
    private String editSubmit(@RequestParam("file") MultipartFile file, @ModelAttribute("user") User user) {
        userService.setUser(user, file);
        return "redirect:/user/details/"+user.getId();
    }

    @GetMapping("/user/delete/{id}")
    private String delete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getDetails(id));
        model.addAttribute("contentPage", "/user/delete");
        return "default";
    }

    @PostMapping("/user/delete")
    private String deleteSubmit(@ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/";
    }

    @GetMapping(value = "/user/users/{page}", params = { "search" })
    private String getSearchUser(Model model, String search) {
        if(userService.searchList(search) == null || userService.searchList(search).isEmpty()){
            model.addAttribute("contentPage", "/fragments/searchResultNull");
        }else{
            model.addAttribute("users", userService.searchList(search));
            model.addAttribute("contentPage", "/user/search");
        }
        return "default";
    }
}
