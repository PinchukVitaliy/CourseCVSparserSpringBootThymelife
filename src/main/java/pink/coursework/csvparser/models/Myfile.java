package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Myfiles")
public class Myfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    private String originName;
    private String name;
    private HashMap<String,List<User>> listReadUsers = new HashMap<>();
    private HashMap<String,List<User>> listEditUsers = new HashMap<>();
    private HashMap<String,List<User>> listDeleteUsers = new HashMap<>();

    @ManyToOne
    private User creatorOfFile;

    public Myfile() {
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {  this.id = id;}
    public String getOriginName() {return originName; }
    public void setOriginName(String originName) { this.originName = originName; }
    public String getName() {    return name;  }
    public void setName(String name) { this.name = name; }
    public User getCreatorOfFile() {  return creatorOfFile;  }
    public void setCreatorOfFile(User creatorOfFile) {  this.creatorOfFile = creatorOfFile; }

    public HashMap<String, List<User>> getListReadUsers() {
        return listReadUsers;
    }

    public void setListReadUsers(HashMap<String, List<User>> listReadUsers) {
        this.listReadUsers = listReadUsers;
    }

    public HashMap<String, List<User>> getListEditUsers() {
        return listEditUsers;
    }

    public void setListEditUsers(HashMap<String, List<User>> listEditUsers) {
        this.listEditUsers = listEditUsers;
    }

    public HashMap<String, List<User>> getListDeleteUsers() {
        return listDeleteUsers;
    }

    public void setListDeleteUsers(HashMap<String, List<User>> listDeleteUsers) {
        this.listDeleteUsers = listDeleteUsers;
    }
}
