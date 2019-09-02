package pink.coursework.csvparser.models;

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
    private Boolean read;
    //доступ к редактированию
    private Boolean edit;
    //доступ к скачиванию
    private Boolean dowload;
    //конструктор
    public AccessLink() {
        this.link="No link";
        this.read = false;
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

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public Boolean getDowload() {
        return dowload;
    }

    public void setDowload(Boolean delete) {
        this.dowload = delete;
    }
}
