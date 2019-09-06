package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель пользователя
 * все анотации в классе для работы с базой данных при помощи Hibernate
 * <p>
 * @Entity — Указывает, что данный бин (класс) является сущностью.
 * @Table — указывает на имя таблицы, которая будет отображаться в этой сущности.
 * @Id — id колонки.
 * @GeneratedValue — указывает, что данное свойство будет создаваться согласно указанной стратегии.
 * @Column — указывает на имя колонки, которая отображается в свойство сущности.
 * @ManyToMany — связь многие ко многим.
 * @OneToMany — указывает на связь один ко многим. Применяется с другой стороны от сущности с @ManyToOne.
 * @JoinTable — указывает на связь с таблицей.
 * @JoinColumn  — применяется когда внешний ключ находится в одной из сущностей. Может применяться с обеих сторон взаимосвязи.
 * </p>
 */
@Entity
@Table(name = "Users")
public class User {
    //идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    //Логин
    private String login;
    //почта
    private String email;
    //пароль
    private String password;
    //картинка(аватар)
    private String icon;
    //активация пользователя
    private boolean active;
    //код активации
    private String activationCode;

    //связь многие ко многим пользователь и роли (список ролей)
    @ManyToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name="User_Role",
            joinColumns=@JoinColumn(name="User_id"),
            inverseJoinColumns=@JoinColumn(name="Role_id")
    )
    private List<Role> roleList = new ArrayList<>();
    //связь один пользователь много его личных файлов
    @OneToMany(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Myfile> listCreatedFiles = new ArrayList<>();
    //связь один пользователь много файлов с открытым доступом
    @OneToMany
    private List<Myfile> listOpenFiles = new ArrayList<>();
    //конструктор
    public User() {
    }
    //геттеры и сеттеры
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
    public List<Myfile> getListCreatedFiles() { return listCreatedFiles;}
    public void setListCreatedFiles(List<Myfile> listCreatedFiles) {this.listCreatedFiles = listCreatedFiles; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active;  }

    public String getActivationCode() {  return activationCode; }
    public void setActivationCode(String activationCode) {  this.activationCode = activationCode; }

    public List<Myfile> getListOpenFiles() {
        return listOpenFiles;
    }

    public void setListOpenFiles(List<Myfile> listOpenFiles) {
        this.listOpenFiles = listOpenFiles;
    }

}