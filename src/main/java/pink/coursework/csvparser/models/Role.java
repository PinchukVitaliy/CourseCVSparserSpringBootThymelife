package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Модель роль
 * все анотации в классе для работы с базой данных при помощи Hibernate
 * <p>
 * @Entity — Указывает, что данный бин (класс) является сущностью.
 * @Table — указывает на имя таблицы, которая будет отображаться в этой сущности.
 * @Id — id колонки.
 * @GeneratedValue — указывает, что данное свойство будет создаваться согласно указанной стратегии.
 * @Column — указывает на имя колонки, которая отображается в свойство сущности.
 * @ManyToMany — связь многие ко многим.
 * @JoinTable — указывает на связь с таблицей.
 * @JoinColumn  — применяется когда внешний ключ находится в одной из сущностей. Может применяться с обеих сторон взаимосвязи.
 * </p>
 */
@Entity
@Table(name = "Roles")
public class Role {
    //идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    //имя роли
    private String name;
    //связь много файлов и много пользователей
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="User_Role",
            joinColumns=@JoinColumn(name="Role_id"),
            inverseJoinColumns=@JoinColumn(name="User_id")
    )
    private List<User> userList = new ArrayList<>();
    //конструктор
    public Role() {
    }
    //геттеры и сеттеры
    public Integer getId() {return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {  return name;  }

    public void setName(String name) {    this.name = name;   }

    public List<User> getUserList() {   return userList;  }

    public void setUserList(List<User> userList) {   this.userList = userList; }
}
