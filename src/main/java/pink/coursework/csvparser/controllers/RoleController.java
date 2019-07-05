package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pink.coursework.csvparser.servises.RoleService;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/role/roles")
    private String getRoles(Model model) {
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("contentPage", "/role/roles");
        return "default";
    }
}
