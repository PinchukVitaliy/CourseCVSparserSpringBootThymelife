package pink.coursework.csvparser.controllers;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  Контроллер главной страници
 *  @Controller определяет класс как Контроллер Spring MVC
 *  @GetMapping аннотация для отображения HTTP- GET запросов на определенные методы-обработчики.
 *  В частности, @GetMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.GET).
 */
@Controller
public class HomeController {

    /**<p>Get маппинг на главную страницу</p>
     * @param model объект который передает данные в представление
     * @return переход страницу index
     */
    @GetMapping("/")
    private String index(Model model) {
        model.addAttribute("contentPage", "/home/index");
        return "default";
    }

    /**<p>Get маппинг на страницу администратора</p>
     * @param model объект который передает данные в представление
     * @return  переход страницу admin
     */
    @GetMapping("/home/admin")
    private String admin(Model model) {
        model.addAttribute("contentPage", "/home/admin");
        return "default";
    }

    /**<p>Get маппинг на страницу 404</p>
     * @param model объект который передает данные в представление
     * @return  переход страницу access-denied
     */
    @GetMapping("/access-denied")
    private String accessdenied(Model model) {
        model.addAttribute("contentPage", "/fragments/access-denied");
        return "default";
    }
}
