package pink.coursework.csvparser.models;

import javax.persistence.*;
import java.util.HashMap;

@Entity
@Table(name = "Myfiles")
public class Myfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Integer id;
    private String originName;
    private String name;

    @OneToOne
    private AccessLink accessLink;

    @ManyToOne
    private User creatorOfFile;

    public Myfile() {
    }

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
