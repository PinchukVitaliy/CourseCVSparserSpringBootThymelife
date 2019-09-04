package pink.coursework.csvparser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

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
    @GetMapping(value = "/login/login")
    public String getLoginPage(Model model) {
            model.addAttribute("contentPage", "/login/login");
            return "default";
        }
}