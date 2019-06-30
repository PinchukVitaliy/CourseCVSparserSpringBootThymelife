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
import java.util.List;

@Service
public class UserService {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://Users//Zigo//Desktop//csvparser//src//main//resources//static//images//";
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }
    public User getDetails(Integer id) {return userRepository.getOne(id);}
    public void delete(User user) { userRepository.delete(user);}
    public void setUser(User user, MultipartFile file){
        if(!file.isEmpty()){
            singleFileUpload(file);
            user.setIcon(file.getOriginalFilename());
        }
        userRepository.save(user);
    }

    public void singleFileUpload(MultipartFile file) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                Thread.sleep(2000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
