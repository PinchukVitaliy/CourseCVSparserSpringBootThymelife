package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.Date;
/**
 * Модель статистики
 * все анотации в классе для работы с базой данных при помощи Hibernate
 * <p>
 * @Entity — Указывает, что данный бин (класс) является сущностью.
 * @Table — указывает на имя таблицы, которая будет отображаться в этой сущности.
 * @Id — id колонки.
 * @GeneratedValue — указывает, что данное свойство будет создаваться согласно указанной стратегии.
 * @Column — указывает на имя колонки, которая отображается в свойство сущности.
 * </p>
 */
@Entity
@Table(name = "Statistics")
public class Statistic {
    //идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    //уникальное имя файла
    private String fileName;
    //оригинальное имя файла
    private String fileNameOriginal;
    //почта пользователя
    private String userName;
    //поле отвечает за загрузку файла на сервер
    private Boolean userCreate;
    //событие которое произошло с файлом
    private String fileAction;
    //дата
    private Date date;
    //конструктор
    public Statistic() {
        this.userCreate = false;
    }
    //геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Boolean userCreate) {
        this.userCreate = userCreate;
    }

    public String getFileAction() {
        return fileAction;
    }

    public void setFileAction(String fileAction) {
        this.fileAction = fileAction;
    }

    public String getFileNameOriginal() {
        return fileNameOriginal;
    }

    public void setFileNameOriginal(String fileNameOriginal) {
        this.fileNameOriginal = fileNameOriginal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
