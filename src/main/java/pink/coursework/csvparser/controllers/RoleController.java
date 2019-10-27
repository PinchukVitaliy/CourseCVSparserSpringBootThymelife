package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.RoleService;

import java.util.List;
/**
 * Контроллер класса роль
 * @Controller определяет класс как Контроллер Spring MVC
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 * @GetMapping аннотация для отображения HTTP- GET запросов на определенные методы-обработчики.
 * В частности, @GetMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.GET).
 * @PostMapping аннотация для отображения HTTP- POSTзапросов на определенные методы-обработчики.
 * В частности, @PostMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.POST).
 * @RequestParam аннотация используется для привязки параметров запроса к методу в вашем контроллере.
 * @PathVariable определяет шаблон, который используется в URI для входящего запроса.
 * PathVariable имеет только одно значение атрибута для привязки шаблона URI запроса
 * @ModelAttribute это аннотация, которая связывает параметр метода или возвращаемое значение метода с
 * именованным атрибутом модели, а затем предоставляет его веб-представлению.
 */
@Controller
public class RoleController {
    //сервис объекта роль
    @Autowired
    private RoleService roleService;

    /**<p>Get маппинг на список всех ролей</p>
     * @param model объект который передает данные в представление
     * @return переход на страницу roles
     */
    @GetMapping("/role/roles")
    private String getRoles(Model model) {
        model.addAttribute("roles", roleService.listRoles());
        model.addAttribute("contentPage", "/role/roles");
        return "default";
    }

    /**<p>Get маппинг на добавление новой роли</p>
     * @param model объект который передает данные в представление
     * @return переход на страницу add
     */
    @GetMapping("/role/add")
    private String addRole(Model model) {
        model.addAttribute("contentPage", "/role/add");
        return "default";
    }

    /**<p>Post маппинг на добавление новой роли</p>
     * @param role обьект роли
     * @return редирект на страницу roles
     */
    @PostMapping("/role/add")
    private String addRoleSubmit(@ModelAttribute("role") Role role) {
        roleService.createRole(role);
        return "redirect:/role/roles/";
    }

    /**<p>Get маппинг установки ролей пользователям</p>
     * @param idRole идентификатор роли
     * @param model объект который передает данные в представление
     * @return переход на страницу edit
     */
    @GetMapping("/role/edit/{id}")
    private String editRoleFirst(@PathVariable("id") Integer idRole, Model model) {
        model.addAttribute("role", roleService.getRole(idRole));
        model.addAttribute("user_yes_role", roleService.getRole(idRole).getUserList());
        model.addAttribute("user_no_role", roleService.listUsersNoRole(idRole));
        model.addAttribute("find", true);
        model.addAttribute("contentPage", "/role/edit");
        return "default";

    }

    /**<p>Get маппинг поиска по установки ролей пользователям</p>
     * @param idRole идентификатор роли
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @return если поиск не корректный переход на страницу с ошибкой searchResultNullRoleUsers,
     * вывод результата на странице edit
     */
    @GetMapping(value = "/role/edit/{id}", params = { "search" })
    private String getSearchRolesNoAntYes(@PathVariable("id") Integer idRole, Model model, String search) {
        if((roleService.searchList(search, roleService.getRole(idRole).getUserList()) == null &&
                roleService.searchList(search, roleService.listUsersNoRole(idRole)) == null ) ||
                (roleService.searchList(search, roleService.getRole(idRole).getUserList()).isEmpty() &&
                        roleService.searchList(search, roleService.listUsersNoRole(idRole)).isEmpty())){
            model.addAttribute("role", roleService.getRole(idRole));
            model.addAttribute("contentPage", "/fragments/searchResultNullRoleUsers");
        }else{
            model.addAttribute("role", roleService.getRole(idRole));
            model.addAttribute("user_yes_role", roleService.searchList(search, roleService.getRole(idRole).getUserList()));
            model.addAttribute("user_no_role", roleService.searchList(search, roleService.listUsersNoRole(idRole)));
            model.addAttribute("search", true);
            model.addAttribute("find", true);
            model.addAttribute("contentPage", "/role/edit");
        }
        return "default";
    }

    /**<p>Post маппинг установки ролей пользователям</p>
     * @param role обьект роли
     * @param IdsToAdd список пользователей которые получают роль
     * @param IdsToDelete список пользователей теряющил роль
     * @return редирект на страницу edit
     */
    @PostMapping("/role/edit")
    private String editRoleSubmit(@ModelAttribute("role") Role role,
                                  @RequestParam(value = "IdsToAdd", required = false) List<User> IdsToAdd,
                                  @RequestParam(value = "IdsToDelete", required = false) List<User> IdsToDelete) {
        roleService.editRole(roleService.getRole(role.getId()),IdsToAdd, IdsToDelete);
        return "redirect:/role/edit/"+role.getId();
    }

    /**<p>Get маппинг удаления роли</p>
     * @param id идентификатор роли
     * @param model объект который передает данные в представление
     * @return переход на страницу delete
     */
    @GetMapping("/role/delete/{id}")
    private String delete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        model.addAttribute("contentPage", "/role/delete");
        return "default";
    }

    /**<p>Post маппинг удаления роли</p>
     * @param role обьект роли
     * @return редирект на страницу roles
     */
    @PostMapping("/role/delete")
    private String deleteSubmit(@ModelAttribute("role") Role role) {
        roleService.deleteRole(role);
        return "redirect:/role/roles";
    }
}
