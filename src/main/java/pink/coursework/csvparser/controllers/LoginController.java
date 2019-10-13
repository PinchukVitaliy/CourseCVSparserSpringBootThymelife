package pink.coursework.csvparser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 *  Контроллер логинизации пользователя
 *  @Controller определяет класс как Контроллер Spring MVC
 *  @GetMapping аннотация для отображения HTTP- GET запросов на определенные методы-обработчики.
 *  В частности, @GetMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.GET).
 */
@Controller
public class LoginController {

    /**<p>Get маппинг на страницу login</p>
     * @param model объект который передает данные в представление
     * @return переход страницу login
     */
    @GetMapping("/login/login")
    public String getLoginPage(Model model, HttpSession httpSession) {
            model.addAttribute("contentPage", "/login/login");
            return "default";
        }
}