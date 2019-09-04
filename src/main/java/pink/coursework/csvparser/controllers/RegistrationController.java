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
/**
 *  Контроллер регистрации пользователя
 *  @Controller определяет класс как Контроллер Spring MVC
 *  @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 *  @GetMapping аннотация для отображения HTTP- GET запросов на определенные методы-обработчики.
 *  В частности, @GetMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.GET).
 *  @PostMapping аннотация для отображения HTTP- POSTзапросов на определенные методы-обработчики.
 *  В частности, @PostMapping это составная аннотация, которая действует как ярлык для @RequestMapping(method = RequestMethod.POST).
 *  @PathVariable определяет шаблон, который используется в URI для входящего запроса.
 *  PathVariable имеет только одно значение атрибута для привязки шаблона URI запроса
 *  @ModelAttribute это аннотация, которая связывает параметр метода или возвращаемое значение метода с
 *  именованным атрибутом модели, а затем предоставляет его веб-представлению.
 */
@Controller
public class RegistrationController {
    //сервис объекта пользователь
    @Autowired
    private UserService userService;

    /**<p>Get маппинг на страницу register</p>
     * @param model объект который передает данные в представление
     * @return переход на страницу register
     */
    @GetMapping(value = "/registration/register")
    private String getRegister(Model model) {
        model.addAttribute("contentPage", "/registration/register");
        return "default";
    }

    /**<p>Post маппинг отправки модели пользователя на сервер</p>
     * @param user обьект пользователя
     * @param model объект который передает данные в представление
     * @return переход на страницу register с ошибкой ели такой пользователь есть, ибо
     * отправка кода активации на почту
     */
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

    /**<p>Get маппинг на активацию пользователя по коду с почты</p>
     * @param model объект который передает данные в представление
     * @param code код активации с почты
     * @return если код активации есть и не использован то регистрируем пользователя и переходим на страницу входа,
     * или показываем ошибку что кода нет
     */
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
