package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    private String login;
    private String email;
    private String password;
    private String icon;
    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="User_Role",
            joinColumns=@JoinColumn(name="User_id"),
            inverseJoinColumns=@JoinColumn(name="Role_id")
    )
    private List<Role> roleList = new ArrayList<>();


    public User() {
    }

    public List<Role> getRoleList() {return roleList;}

    public void setRoleList(List<Role> roleList) { this.roleList = roleList;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String name) {
        this.login = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {return icon; }

    public void setIcon(String icon) {this.icon = icon; }
}