package pl.coderslab.imageviewer.model;


import pl.coderslab.imageviewer.repository.RoleRepository;

import javax.persistence.*;
import java.util.Base64;


@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private String title;
    private String description;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RoleId", referencedColumnName = "Id")
    private Role role;


    public Image(){
    }

    public Image(int id, byte[] image, String title, String description){
        this.Id = id;
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public Image(byte[] image, String title, String description){
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getId(){
        return Id;
    }

    public String getImage(){
        return Base64.getEncoder().encodeToString(image);
    }
    public void setImage(byte[] image){
        this.image = image;
    }
    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
    public Role getRole(){return role;};
    public void setRole(Role role){ this.role = role;};

}