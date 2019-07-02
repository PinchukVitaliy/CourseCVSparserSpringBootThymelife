package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue
    protected Integer id;
    private String name;
    @ManyToMany
    private List<User> userList;

    public Role() {
    }

    public Integer getId() {return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {  return name;  }

    public void setName(String name) {    this.name = name;   }

    public List<User> getUserList() {   return userList;  }

    public void setUserList(List<User> userList) {   this.userList = userList; }
}
