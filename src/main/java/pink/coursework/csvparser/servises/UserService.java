package pink.coursework.csvparser.servises;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.RoleRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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
    //объекта сессии в сервлете объекта HttpServletRequest
    @Autowired
    private HttpSession httpSession;
    //обьект BCryptPasswordEncoder для шифрования паролей
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //путь к папке где хранятся аватары пользователей
    @Value("${avatar.path}")
    String avatarPath;
    //количество отображаемых пользователей при пеженации
    private static int USERPAGE = 9;

    /**<p>Добавление нового пользователя</p>
     * @param user обьек пользователь
     * @return true нет такого и создается пользователь false есть такой и ничего не создавать
     */
    public boolean saveUser(User user){
        if(userRepository.findByEmail(user.getEmail()) != null){
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        user.getRoleList().add(roleRepository.findByName("goust"));
        user.setIcon("no_user.jpg");
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
               "Hello, %s! \n" +
                       "Welcom to Csv-Parser. \n" +
                       "Please click on the following link to activate your account! \n" +
                       "http:localhost:8080/registration/active/%s",
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
    public User getDetails(Integer id) {;return userRepository.getOne(id);}

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
        User userReal = userRepository.getOne(user.getId());
        if(!file.isEmpty()){
            String resultFileName = generateUniqueFileName(file.getOriginalFilename());
            singleFileUpload(file, resultFileName);
            userReal.setIcon(resultFileName);
        }
        userReal.setLogin(user.getLogin());
        userReal.setEmail(user.getEmail());
        userReal.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userReal);
        User userSession = (User)httpSession.getAttribute("user");
        if(userSession.getEmail().equals(userReal.getEmail())){
            httpSession.setAttribute("user", userReal);
        }
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
        String password =generatePassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Here is your new password: '%s'\n" +
                            "Do not tell anyone!",
                    user.getLogin(),
                    password
            );

            mailSender.send(user.getEmail(), "Password recovery", message);
        }
        return true;
    }

    /**<p>Генератор случайных паролей</p>
     * @return случайный пароль
     */
    private String generatePassword(){
        //И символы из диапазона ASCII 33-122, которые являются специальными символами, цифрами в верхнем и нижнем регистре.
        int size = 0;
        String password ="";
        int random_number = 1 + (int) (Math.random() * 3);
        while(size < 8){
            if(random_number == 1) {
                password += new Random().ints(1, 65, 90).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
            }else if(random_number == 2) {
                password += new Random().ints(1, 97, 122).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
            }else{
                password += new Random().ints(1, 48, 57).collect(StringBuilder::new,
                        StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
            }
            random_number = 1 + (int) (Math.random() * 3);
            size++;
        }
        return password;
    }
    /**<p>Поиск пользователя по почте</p>
     * @param email имя почты пользователя
     * @return обьект пользователя
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**<p>Блокировка и разблокировка пользователя</p>
     * @param idUser идентификатор пользователя
     */
    public void blockUser(Integer idUser) {
        User user = userRepository.getOne(idUser);
        if(user.isActive()){
            user.setActive(false);
        }else{
            user.setActive(true);
        }
        userRepository.save(user);
    }
}

