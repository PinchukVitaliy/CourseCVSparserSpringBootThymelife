package pink.coursework.csvparser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Кастомный класс обработчика метода logout
 * @Override аннотация позволяет переопределить метод у родителя.
 */
@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    /**<p>метод выхода из сайта который очищает сессию пользователя</p>
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param authentication Authentication
     */
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res,
                       Authentication authentication) {
        if(req != null) {
            req.getSession().removeAttribute("user");
        }
    }
}
