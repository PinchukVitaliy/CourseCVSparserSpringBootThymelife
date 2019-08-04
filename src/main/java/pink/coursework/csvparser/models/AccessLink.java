package pink.coursework.csvparser.models;

import javax.persistence.*;

@Entity
@Table(name = "Accesslinks")
public class AccessLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    private String link;
    private Boolean read;
    private Boolean edit;
    private Boolean dowload;

    public AccessLink() {
        this.link="No link";
        this.read = false;
        this.edit = false;
        this.dowload = false;
    }

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
