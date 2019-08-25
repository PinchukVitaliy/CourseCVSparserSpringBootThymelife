package pink.coursework.csvparser.servises;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pink.coursework.csvparser.models.AccessLink;
import pink.coursework.csvparser.models.CsvModel;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.models.User;
import pink.coursework.csvparser.repositories.AccessLinkRepository;
import pink.coursework.csvparser.repositories.FileRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class FileService {
    //Save the uploaded file to this folder
    @Value("${file.path}")
    String filePath;
    private static int FILEPAGE = 7;
    private static int OPENFILEPAGE = 7;
    private static int CSVFILEPAGE = 50;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessLinkRepository accessLinkRepository;


    public Myfile getFile(Integer id){
        return fileRepository.getOne(id);
    }

    public void deleteFile(Integer myfileId){
        Myfile file = fileRepository.getOne(myfileId);
        User user = userRepository.getOne(file.getCreatorOfFile().getId());
        user.getListCreatedFiles().remove(file);
        userRepository.save(user);
        fileRepository.delete(file);
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
        /////////////test/////////////

        //files.get(1).setListReadUsers();
        ///////////////////////
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
            newfile.setAccessLink(new AccessLink());
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

    public void dowload(Integer fileId, HttpServletResponse response){
        Myfile myfile = fileRepository.getOne(fileId);
        //Добавить заголовок ответа HTTP с именем Content-Disposition
        //и присвойте ему значение attachment; filename=fileName,
        //где fileName — имя файла по умолчанию.
        Path file = Paths.get(filePath + myfile.getName());
        if (Files.exists(file)){
            response.setHeader("Content-disposition", "attachment;filename=" +  myfile.getOriginName());
            response.setContentType("application/vnd.ms-excel");
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }

    public Myfile addLink(Myfile file, Boolean read, Boolean edit, Boolean dowload) {
        AccessLink link = accessLinkRepository.getOne(file.getAccessLink().getId());
        if(read == null && edit == null && dowload == null){
            link.setRead(false);
            link.setEdit(false);
            link.setDowload(false);
            link.setLink("No link");
            file.setAccessLink(link);
            fileRepository.save(file);
            return  file;
        }
        if(read != null){
            link.setRead(read);
        }else{
            link.setRead(false);
        }
           if(edit != null){
               link.setEdit(edit);
           }else{
               link.setEdit(false);
           }
               if(dowload != null){
                   link.setDowload(dowload);
               }else{
                   link.setDowload(false);
               }
                    link.setLink(UUID.randomUUID().toString());
                    file.setAccessLink(link);
                    fileRepository.save(file);

       return file;
    }


    public List<Myfile> getOpenFiles(Integer idUser, int page) {
        User user = userRepository.getOne(idUser);
        List<Myfile> files = new ArrayList<>();
        for (int i = (page - 1) * OPENFILEPAGE; i < (page) * OPENFILEPAGE && i < user.getListOpenFiles().size(); i++) {
            files.add(user.getListOpenFiles().get(i));
        }
        return files;
    }
    public int openPages(Integer idUser) {
        return (int) Math.ceil((double)  userRepository.getOne(idUser).getListOpenFiles().size() / OPENFILEPAGE);
    }

    public Boolean addLinkUser(Integer idUser, String link) {
        AccessLink access = accessLinkRepository.findByLink(link);
        if(access == null){
            return false;
        }
        Myfile file = fileRepository.findByAccessLink(access);
        User user = userRepository.getOne(idUser);
        if(user.getListOpenFiles().contains(file)){
            return false;
        }
        user.getListOpenFiles().add(file);
        userRepository.save(user);
        return true;
    }

    public void removeFile(Integer idFile, Integer idUser) {
        Myfile file = fileRepository.getOne(idFile);
        User user = userRepository.getOne(idUser);
        user.getListOpenFiles().remove(file);
        userRepository.save(user);
    }

    public CsvModel openCSV(Integer idFile, int page) throws Exception {
        Myfile fileCsv = fileRepository.getOne(idFile);
        CsvModel csvModel = new CsvModel();
        csvModel.getListRowsData(filePath + fileCsv.getName(), page, CSVFILEPAGE);
        return csvModel;
    }
    public int csvPages(Integer idFile) throws Exception {
        Myfile fileCsv = fileRepository.getOne(idFile);
        CsvModel csvModel = new CsvModel();
        return (int) Math.ceil((double) csvModel.countRows(filePath + fileCsv.getName())/ CSVFILEPAGE);
    }

    public void getCsvModelSave(List<String> title, List<String> dataList, List<Integer> idList, Myfile file) throws Exception{
        Myfile fileCsv = fileRepository.getOne(file.getId());
        CsvModel csvModel = new CsvModel();
        csvModel.writeModifiedData(title,dataList,idList);
        csvModel.saveCsv( filePath + fileCsv.getName());
    }
}