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
    //имя почты (zigo@ukr.net-от этого имени будут приходить сообщения)
    @Value("${spring.mail.username}")
    private String username;
    //пароль от почты (zigo@ukr.net - пароль этой почты)
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
        mailSender.setPassword(decode(password.getBytes(), "meow"));

        Properties properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.transport.protocol", protocol);
        //для дебага в консоли
        //properties.setProperty("mail.debug", debug);

        return  mailSender;
    }

    /**<p>метод для шифровки текста с помощью XOR</p>
     * @param secret строка которую шифруем
     * @param key ключ для шифрования
     * @return зашифрованная строка
     */
    private byte[] encode(String secret, String key) {
        byte[] btxt = null;
        byte[] bkey = null;

        btxt = secret.getBytes();
        bkey = key.getBytes();

        byte[] result = new byte[secret.length()];

        for (int i = 0; i < btxt.length; i++) {
            result[i] = (byte) (btxt[i] ^ bkey[i % bkey.length]);
        }
        return result;
    }

    /**<p>метод для расшифровки текста</p>
     * @param secret строка которую шифруем
     * @param key ключ для шифрования
     * @return получам реальную строку
     */
    private String decode(byte[] secret, String key) {
        byte[] result = new byte[secret.length];
        byte[] bkey = key.getBytes();

        for (int i = 0; i < secret.length; i++) {
            result[i] = (byte) (secret[i] ^ bkey[i % bkey.length]);
        }
        return new String(result);
    }
}
