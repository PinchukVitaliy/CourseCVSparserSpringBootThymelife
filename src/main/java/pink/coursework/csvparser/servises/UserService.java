package pink.coursework.csvparser.servises;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.Role;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.RoleRepository;
import pink.coursework.csvparser.repositories.UserRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailSender mailSender;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "src\\main\\resources\\static\\icons_users\\";
    private static int USERPAGE = 9;

    public boolean addUser(User user){
        List<User> users = userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        List<Role> listRoles = roleRepository.findAll();
        for (int i = 0; i < listRoles.size(); i++) {
            if (listRoles.get(i).getName().equals("goust")) {
                user.getRoleList().add(listRoles.get(i));
            }
        }
        user.setActive(true);
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

    public List<User> listUsers(int page) {
        List<User> allUsers = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (int i = (page - 1) * USERPAGE; i < (page) * USERPAGE && i < allUsers.size(); i++) {
            users.add(allUsers.get(i));
        }
        return users;
    }
    public int pages(){
        return (int) Math.ceil((double) userRepository.findAll().size() / USERPAGE);
    }

    public User getDetails(Integer id) {return userRepository.getOne(id);}
    public void delete(User user) {
        userRepository.delete(user);
    }
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

    public void setUser(User user, MultipartFile file) {
        if(!file.isEmpty()){
            String resultFileName = generateUniqueFileName(file.getOriginalFilename());
            singleFileUpload(file, resultFileName);
            user.setIcon(resultFileName);
        }
        userRepository.save(user);
    }

    public void singleFileUpload(MultipartFile file, String resultFileName) {
        Path path = Paths.get(UPLOADED_FOLDER + resultFileName);
        byte[] bytes;
        try {
            bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    public boolean getActivate(String code) {
        User user = userRepository.findByActivationCode(code);

        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);

        return true;
    }
}

