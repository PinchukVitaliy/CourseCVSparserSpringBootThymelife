package pink.coursework.csvparser.models;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Type;

import javax.persistence.*;
/**
 * Модель ссылка доступа
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
@Table(name = "Accesslinks")
public class AccessLink {
    //идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    //ссылка
    private String link;
    //доступ к чтению
    private boolean reading;
    //доступ к редактированию
    private boolean edit;
    //доступ к скачиванию
    private boolean dowload;
    //конструктор
    public AccessLink() {
        this.link="No link";
        this.reading = false;
        this.edit = false;
        this.dowload = false;
    }
    //геттеры и сеттеры
    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id;}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isRead() {
        return reading;
    }

    public void setRead(boolean reading) {
        this.reading = reading;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isDowload() {
        return dowload;
    }

    public void setDowload(boolean dowload) {
        this.dowload = dowload;
    }
}
