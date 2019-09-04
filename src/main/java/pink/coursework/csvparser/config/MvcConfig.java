package pink.coursework.csvparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**<p>Конфигурация над компонентамы в программе</p>
 *<p>Класс MvcConfig представляет собой класс конфигурации, совсем как XML-файл.
 * Это достигается путем использования аннотации @Configuration , которая размещается над классом.
 * Данная аннотация размещается над методом, который создает экземпляр bean-компонента и устанавливает зависимость.
 * @Value аннотация позволяет нам использовать значения из вне в поля в bean-компонентах.
 * @Override аннотация позволяет переопределить метод у родителя.
 * </p>
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    //путь к папке с аватарками
    @Value("${avatar.path}")
    private String avatarPath;
    //путь к файлам csv
    @Value("${file.path}")
    private String filePath;

    /**<p>Метод регистрирует ресурсы для доступа к ststic файлам динамически</p>
     * @param registry обьект ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/icon/**").
                addResourceLocations("file:"+avatarPath);
        //registry.addResourceHandler("/file/**").
        //        addResourceLocations("file:"+filePath);
        registry.addResourceHandler("/images/**").
                addResourceLocations("classpath:/static/images/");
    }

    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login/login").setViewName("login");
    }
}
