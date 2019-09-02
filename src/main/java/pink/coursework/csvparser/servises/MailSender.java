package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Сервис работы с почтой
 * @Service содержат бизнес-логику и вызывают методы на уровне хранилища.
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 */
@Service
public class MailSender {
    //Экземпляр класса JavaMailSender для работы с почтой
    @Autowired
    private JavaMailSender mailSender;
    //Настройки почты для рассылки сообщений
    @Value("${spring.mail.username}")
    private String username;

    /**<p>Установка значений отпраыителя и отправка сообщения</p>
     * @param emailTo имя почты
     * @param subject название сообщения
     * @param message само сообщение
     */
    public void send(String emailTo, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
