package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.FileRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    //Save the uploaded file to this folder
    @Value("${file.path}")
    String filePath;
    private static int FILEPAGE = 7;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;

    public Myfile getFile(Integer id){
        return fileRepository.getOne(id);
    }

    public void deleteFile(Myfile myfile){
        fileRepository.delete(myfile);
    }
    public List<Myfile> paginationFiles(int page) {
        List<Myfile> allUsers = fileRepository.findAll();
        List<Myfile> users = new ArrayList<>();
        for (int i = (page - 1) * FILEPAGE; i < (page) * FILEPAGE && i < allUsers.size(); i++) {
            users.add(allUsers.get(i));
        }
        return users;
    }
    public int pages(){
        return (int) Math.ceil((double) fileRepository.findAll().size() / FILEPAGE);
    }

    public List<Myfile> searchList(String search){
        List<Myfile> searchList = null;
        if(search.isEmpty()){
            return searchList;
        }
            List<Myfile> fileList = fileRepository.findAll();
            searchList = new ArrayList<Myfile>();
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getOriginName().regionMatches(true, 0, search, 0, search.length())
                        ||
                fileList.get(i).getCreatorOfFile().getLogin().regionMatches(true,0,search,0,search.length())) {
                    searchList.add(fileList.get(i));
                }
            }
        return  searchList;
    }


    public List<Myfile> listUserFiles(Integer id, int page) {
        List<Myfile> filesUser = userRepository.getOne(id).getListCreatedFiles();
        List<Myfile> files = new ArrayList<>();
        for (int i = (page - 1) * FILEPAGE; i < (page) * FILEPAGE && i < filesUser.size(); i++) {
            files.add(filesUser.get(i));
        }
        return files;
    }
    public int myPages(Integer id){
        return (int) Math.ceil((double)  userRepository.getOne(id).getListCreatedFiles().size() / FILEPAGE);
    }

    public List<Myfile> searchListMyfiles(Integer id, String search) {
        List<Myfile> searchList = null;
        if(search.isEmpty()){
            return searchList;
        }
        List<Myfile> fileList = userRepository.getOne(id).getListCreatedFiles();
        searchList = new ArrayList<Myfile>();
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getOriginName().regionMatches(true, 0, search, 0, search.length())) {
                    searchList.add(fileList.get(i));
                }
            }
        return  searchList;
    }

    public void add(MultipartFile file, Integer idUser) {
        if(!file.isEmpty()){
            Myfile newfile = new Myfile();
            User user = userRepository.getOne(idUser);
            String resultFileName = generateUniqueFileName(file.getOriginalFilename());
            singleFileUpload(file, resultFileName);

            newfile.setOriginName(file.getOriginalFilename());
            newfile.setName(resultFileName);
            newfile.setCreatorOfFile(user);
            newfile.setListDeleteUsers(null);
            newfile.setListReadUsers(null);
            newfile.setListEditUsers(null);
            fileRepository.save(newfile);

            user.getListCreatedFiles().add(newfile);
            userRepository.save(user);
        }
    }
    public void singleFileUpload(MultipartFile file, String resultFileName) {
        Path path = Paths.get(filePath + resultFileName);

        try {
            byte[] bytes = file.getBytes();
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

    public void dowload(Integer fileId){
        Myfile myfile = fileRepository.getOne(fileId);
        Path path = Paths.get(filePath + myfile.getName());
        //File file = new File(filePath + myfile.getName());
        try {
            //byte[] bytes = file.getBytes();
            Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
