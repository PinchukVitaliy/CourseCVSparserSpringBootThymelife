package pink.coursework.csvparser.servises;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.RoleRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * <p>Ссервис описывающий логику работы пользователя</p>
 * @Service содержат бизнес-логику и вызывают методы на уровне хранилища.
 * @Value аннотация позволяет нам использовать значения из вне в поля в bean-компонентах.
 * @Autowired обеспечивает контроль над тем, где и как автосвязывание должны быть осуществленно.
 */
@Service
public class UserService {
    //Экземпляр репозитория юзер
    @Autowired
    private UserRepository userRepository;
    //Экземпляр репозитория роли
    @Autowired
    private RoleRepository roleRepository;
    //Экземпляр репозитория почты
    @Autowired
    private MailSender mailSender;
    //@Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    //путь к папке где хранятся аватары пользователей
    @Value("${avatar.path}")
    String avatarPath;
    //количество отображаемых пользователей при пеженации
    private static int USERPAGE = 9;

    /**<p>Добавление нового пользователя</p>
     * @param user обьек пользователь
     * @return true нет такого и создается пользователь false есть такой и ничего не создавать
     */
    public boolean addUser(User user){
        if(userRepository.findByEmail(user.getEmail()) != null){
            return false;
        }

        //String password = user.getPassword();
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.getRoleList().add(roleRepository.findByName("goust"));
        //user.setActive(true);
        user.setIcon("no_user.jpg");
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
               "Hello, %s! \n" +
                       "Welcom to Sweater. Please, visit next link: http:localhost:8080/registration/active/%s",
                    user.getLogin(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    /**<p>Список пользователей с пеженацией</p>
     * @param page текущая страница
     * @return список пользователей
     */
    public List<User> listUsers(int page) {
        List<User> allUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (int i = (page - 1) * USERPAGE; i < (page) * USERPAGE && i < allUsers.size(); i++) {
            users.add(allUsers.get(i));
        }
        return users;
    }

    /**<p>Количество страниц при пеженации</p>
     * @return количество страниц
     */
    public int pages(){
        return (int) Math.ceil((double) userRepository.findAll().size() / USERPAGE);
    }

    /**<p>Информация по пользователю</p>
     * @param id идентификатор пользователя
     * @return пользователь
     */
    public User getDetails(Integer id) {return userRepository.getOne(id);}

    /**<p>Удаление пользователя</p>
     * @param user обьект пользователя
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**<p>Поиск пользователей</p>
     * @param search фильтр поиска
     * @return список найдених пользователей
     */
    public List<User> searchList(String search)
    {
        List<User> searchList = null;
        if(search.isEmpty()){
            return searchList;
        }
        if (!search.isEmpty()){
            List<User> userList = userRepository.findAll();
            searchList = new ArrayList<User>();
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getLogin().regionMatches(true, 0, search, 0, search.length())) {
                    searchList.add(userList.get(i));
                }
            }
        }
        return searchList;
    }

    /**<p>Сохранение изменений пользователя</p>
     * @param user измененный пользователь
     * @param file передаваемый файл
     */
    public void setUser(User user, MultipartFile file) {
        if(!file.isEmpty()){
            String resultFileName = generateUniqueFileName(file.getOriginalFilename());
            singleFileUpload(file, resultFileName);
            user.setIcon(resultFileName);
        }
        userRepository.save(user);
    }

    /**<p>Загрузка файла на сервер</p>
     * @param file загружаемый файл
     * @param resultFileName уникальное имя загружаемого файла
     */
    public void singleFileUpload(MultipartFile file, String resultFileName) {
        Path path = Paths.get(avatarPath + resultFileName);

        try {
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**<p>Создания уникального имени файла</p>
     * @param filename оригинальное имя файла
     * @return уникальное имя файла
     */
    public String generateUniqueFileName(String filename) {
        //определяем типфайла
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i+1);
        }
        //генерируем имя файла с текущей датой
        long millis = System.currentTimeMillis();
        String datetime = new Date().toString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        filename = datetime + "_" + millis;
        return filename+"."+extension;
    }

    /**<p>Подтверждения регистрации пользователя по ссылке которая приходит ему на почту</p>
     * @param code ссылка с почты
     * @return если такая ссылка есть то присваиваем пользователю роль user и обнуляем ссылку - true
     * в противном случае false
     */
    public boolean getActivate(String code) {
        User user = userRepository.findByActivationCode(code);

        if(user == null){
            return false;
        }
        user.getRoleList().add(roleRepository.findByName("user"));
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    /**<p>Восстановление пароля через почту</p>
     * @param email имя почты пользователя
     * @return нет такого пользователя - false, true - есть и присылаем новый пароль на почту
     */
    public boolean recovery(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }
        /**/
        user.setPassword("new password");
        userRepository.save(user);
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Here is your new password: '%s'\n" +
                            "Do not tell anyone!",
                    user.getLogin(),
                    user.getPassword()
            );

            mailSender.send(user.getEmail(), "Password recovery", message);
        }
        return true;
    }
}

