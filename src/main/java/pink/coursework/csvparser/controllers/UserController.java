package pink.coursework.csvparser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * Контроллер класса пользователь
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
public class UserController {
    //сервис класса пользователь
    @Autowired
    private UserService userService;

    /**<p>Get маппинг отображения всех пользователей + пеженация</p>
     * @param model объект который передает данные в представление
     * @param page текущая страница
     * @return переход на страницу users
     */
    @GetMapping("/user/users/{page}")
    private String getUsersPaginationList(Model model, @PathVariable int page) {
        model.addAttribute("curpage", page);
        model.addAttribute("pages", userService.pages());
        model.addAttribute("users", userService.listUsers(page));
        model.addAttribute("contentPage", "/user/users");
        return "default";
    }

    /**<p>Get маппинг отображения информации о пользователе</p>
     * @param id идентификатор пользователя
     * @param model объект который передает данные в представление
     * @return переход на страницу details
     */
    @GetMapping("/user/details/{id}")
    private String details(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("user", userService.getDetails(id));
        model.addAttribute("contentPage", "/user/details");
        return "default";
    }

    /**<p>Get маппинг изменения информации о пользователе</p>
     * @param id  идентификатор пользователя
     * @param model объект который передает данные в представление
     * @return переход на страницу edit
     */
    @GetMapping("/user/edit/{id}")
    private String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getDetails(id));
        model.addAttribute("contentPage", "/user/edit");
        return "default";

    }

    /**<p>Post маппинг отправки измененной информации о пользователе</p>
     * @param file обьект класса MultipartFile
     * @param user обьект класса пользователь
     * @return редирект на страницу details
     */
    @PostMapping("/user/edit")
    private String editSubmit(@RequestParam("file") MultipartFile file, @ModelAttribute("user") User user) {
        userService.setUser(user, file);
        return "redirect:/user/details/"+user.getId();
    }

    /**<p>Get маппинг удаления пользователя</p>
     * @param id идентификатор пользователя
     * @param model объект который передает данные в представление
     * @return переход на страницу delete
     */
    @GetMapping("/user/delete/{id}")
    private String delete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getDetails(id));
        model.addAttribute("contentPage", "/user/delete");
        return "default";
    }

    /**<p>Post маппинг удаления пользователя</p>
     * @param user обьект класса пользователь
     * @return редирект на главную страницу home
     */
    @PostMapping("/user/delete")
    private String deleteSubmit(@ModelAttribute("user") User user) {
        userService.delete(user);
        return "redirect:/";
    }

    /**<p>Get маппинг поиска пользователей + пеженация</p>
     * @param model объект который передает данные в представление
     * @param search фильтр поиска
     * @return если есть результат переход на страницу search, или searchResultNullUsers
     */
    @GetMapping(value = "/user/users/{page}", params = { "search" })
    private String getSearchUser(Model model, String search, @PathVariable("page") int page) {
        if(userService.searchList(search) == null || userService.searchList(search).isEmpty()){
            model.addAttribute("page", page);
            model.addAttribute("contentPage", "/fragments/searchResultNullUsers");
        }else{
            model.addAttribute("page", page);
            model.addAttribute("users", userService.searchList(search));
            model.addAttribute("contentPage", "/user/search");
        }
        return "default";
    }

    /**<p>Get маппинг восстановления пароля пользователя через почту</p>
     * @param model объект который передает данные в представление
     * @return переход на страницу password
     */
    @GetMapping("/user/password")
    private String recorevy(Model model) {
        model.addAttribute("contentPage", "/user/password");
        return "default";
    }

    /**<p>Post маппинг восстановления пароля пользователя через почту</p>
     * @param email почта пользователя
     * @param model объект который передает данные в представление
     * @return если почта такая есть отправляем новый пароль и переходим на страницу recovery, или
     * показываем ошибку и переходим на страницу password
     */
    @PostMapping("/user/password")
    private String recorevySubmit(@ModelAttribute("email") String email, Model model) {
        boolean isUser = userService.recovery(email);
        if(isUser){
            model.addAttribute("contentPage", "/fragments/recovery");
        }else{
            model.addAttribute("message", "There is no such email!");
            model.addAttribute("contentPage", "/user/password");
        }
        return "default";
    }
}
