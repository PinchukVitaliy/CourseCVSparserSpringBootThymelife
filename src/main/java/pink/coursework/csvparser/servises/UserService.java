package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "src\\main\\resources\\static\\icons_users\\";
    @Autowired
    private UserRepository userRepository;


    public List<User> listUsers() {
        return userRepository.findAll();
    }
    public User getDetails(Integer id) {return userRepository.getOne(id);}
    public void delete(User user) { userRepository.delete(user);}
    public void setUser(User user, MultipartFile file){
        if(!file.isEmpty()){
            singleFileUpload(file);
            user.setIcon(generateUniqueFileName(file.getOriginalFilename()));
        }
        userRepository.save(user);
    }

    public void singleFileUpload(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            //к имени файла дбавлять емейл пльзователя что бы не повторялись картинки
            Path path = Paths.get( UPLOADED_FOLDER + generateUniqueFileName(file.getOriginalFilename()));
            Files.write(path, bytes);
            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
}
