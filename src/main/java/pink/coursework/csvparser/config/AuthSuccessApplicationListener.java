package pink.coursework.csvparser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.servises.UserService;

import javax.servlet.http.HttpSession;


/**
 * <p>Кастомный клас слушатель аутентификации текущего пользователя</p>
 *  @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 *  @Override аннотация позволяет переопределить метод у родителя.
 */
@Configuration
public class AuthSuccessApplicationListener implements
        ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    //объекта сессии в сервлете объекта HttpServletRequest
    @Autowired
    private HttpSession httpSession;
    //сервис класса пользователь
    @Autowired
    private UserService userService;

    /**<p>Обработка события приложения</p>
     *<p>Слушатель который отслеживает аутентификацию пользователя.
     * Если аутентификация успешная создается сессия с текущим объектом пользователя или пустая ссесия.</p>
     * @param appEvent событие, чтобы ответить на аутентификацию пользователя
     */
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent appEvent) {

        if (appEvent != null) {
            if(httpSession.getAttribute("user") == null){
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof UserDetails) {
                    String useremail = ((UserDetails)principal).getUsername();
                    User user = userService.findUserByEmail(useremail);
                    httpSession.setAttribute("user", user);
                } else {
                    httpSession.setAttribute("user", null);
                }
            }

        }
    }
}