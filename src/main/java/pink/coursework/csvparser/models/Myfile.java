package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Модель файла
 * все анотации в классе для работы с базой данных при помощи Hibernate
 * <p>
 * @Entity — Указывает, что данный бин (класс) является сущностью.
 * @Table — указывает на имя таблицы, которая будет отображаться в этой сущности.
 * @Id — id колонки.
 * @GeneratedValue — указывает, что данное свойство будет создаваться согласно указанной стратегии.
 * @Column — указывает на имя колонки, которая отображается в свойство сущности.
 * @OneToOne —  указывает на связь между таблицами «один к одному».
 * @ManyToOne — указывает на связь многие к одному.
 * @JoinTable — указывает на связь с таблицей.
 * @JoinColumn  — применяется когда внешний ключ находится в одной из сущностей. Может применяться с обеих сторон взаимосвязи.
 * </p>
 */
@Entity
@Table(name = "Myfiles")
public class Myfile {
    //идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    //оригинальное имя файла
    private String originName;
    //уникальное имя файла
    private String name;
    //связь один файл и одина ссылка для доступа к нему
    @OneToOne(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    private AccessLink accessLink;
    //связь много файлов и один пользователь который загрузил их
    @ManyToOne
    private User creatorOfFile;
    //конструктор
    public Myfile() {
    }
    //геттеры и сеттеры
    public Integer getId() {return id;}
    public void setId(Integer id) {  this.id = id;}
    public String getOriginName() {return originName; }
    public void setOriginName(String originName) { this.originName = originName; }
    public String getName() {    return name;  }
    public void setName(String name) { this.name = name; }
    public User getCreatorOfFile() {  return creatorOfFile;  }
    public void setCreatorOfFile(User creatorOfFile) {  this.creatorOfFile = creatorOfFile; }
    public AccessLink getAccessLink() {return accessLink; }
    public void setAccessLink(AccessLink accessLink) {  this.accessLink = accessLink;}
}
