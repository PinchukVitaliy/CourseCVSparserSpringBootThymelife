package pink.coursework.csvparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**<p>Конфигурация над емейл компонентами</p>
 *<p>Класс MailConfig представляет собой класс конфигурации, совсем как XML-файл.
 * Это достигается путем использования аннотации @Configuration , которая размещается над классом.
 * Данная аннотация размещается над методом, который создает экземпляр bean-компонента и устанавливает зависимость.
 * @Value аннотация позволяет нам использовать значения из вне в поля в bean-компонентах.
 * @Bean аннотация позволяет определить bean-компоненты.
 * </p>
 */
@Configuration
public class MailConfig {
    //имя хоста (почты которую использую)
    @Value("${spring.mail.host}")
    private String host;
    //имя почты (pinchukvitaliy@ukr.net)
    @Value("${spring.mail.username}")
    private String username;
    //пароль от почты (pinchukvitaliy@ukr.net)
    @Value("${spring.mail.password}")
    private String password;
    //порт по которому пересылаются данные с почты
    @Value("${spring.mail.port}")
    private int port;
    //протокол по которому идет обмен информации
    @Value("${spring.mail.protocol}")
    private String protocol;
    //поле true или false позволяет дебажить события
    @Value("${mail.debug}")
    private String debug;

    /**
     * <p>Заполнение JavaMailSender параметрамы и создание обьекта сервера для отправки сообщений</p>
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", protocol);
        //для дебага в консоли
        //properties.setProperty("mail.debug", debug);

        return  mailSender;
    }
}
