package pink.coursework.csvparser.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pink.coursework.csvparser.models.Myfile;
import pink.coursework.csvparser.repositories.FileRepository;
import pink.coursework.csvparser.repositories.UserRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
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
        if (!search.isEmpty()){
            List<Myfile> fileList = fileRepository.findAll();
            searchList = new ArrayList<Myfile>();
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).getOriginName().regionMatches(true, 0, search, 0, search.length())
                        ||
                fileList.get(i).getCreatorOfFile().getLogin().regionMatches(true,0,search,0,search.length())) {
                    searchList.add(fileList.get(i));
                }
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
}
