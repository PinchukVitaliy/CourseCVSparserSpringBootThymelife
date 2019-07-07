package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    private String name;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="User_Role",
            joinColumns=@JoinColumn(name="Role_id"),
            inverseJoinColumns=@JoinColumn(name="User_id")
    )
    private List<User> userList = new ArrayList<>();

    public Role() {
    }

    public Integer getId() {return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {  return name;  }

    public void setName(String name) {    this.name = name;   }

    public List<User> getUserList() {   return userList;  }

    public void setUserList(List<User> userList) {   this.userList = userList; }
}
