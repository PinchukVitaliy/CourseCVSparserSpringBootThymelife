package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.RoleService;

import java.util.List;

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

    @GetMapping("/role/add")
    private String addRole(Model model) {
        model.addAttribute("contentPage", "/role/add");
        return "default";
    }

    @PostMapping("/role/add")
    private String addRoleSubmit(@ModelAttribute("role") Role role) {
        roleService.createRole(role);
        return "redirect:/role/roles/";
    }

    @GetMapping("/role/edit/{id}")
    private String editRole(@PathVariable("id") Integer idRole, Model model) {
        model.addAttribute("role", roleService.getRole(idRole));
        model.addAttribute("user_no_role", roleService.listUsersNoRole(idRole));
        model.addAttribute("contentPage", "/role/edit");
        return "default";

    }

    @PostMapping("/role/edit")
    private String editRoleSubmit(@ModelAttribute("role") Role role,
                                  @RequestParam(value = "IdsToAdd", required = false) List<User> IdsToAdd,
                                  @RequestParam(value = "IdsToDelete", required = false) List<User> IdsToDelete) {
        roleService.editRole(roleService.getRole(role.getId()),IdsToAdd, IdsToDelete);
        return "redirect:/role/edit/"+role.getId();
    }

    @GetMapping("/role/delete/{id}")
    private String delete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        model.addAttribute("contentPage", "/role/delete");
        return "default";
    }

    @PostMapping("/role/delete")
    private String deleteSubmit(@ModelAttribute("role") Role role) {
        roleService.deleteRole(role);
        return "redirect:/role/roles";
    }
}
