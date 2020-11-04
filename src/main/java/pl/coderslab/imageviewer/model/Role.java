package pl.coderslab.imageviewer.model;

import javax.persistence.*;

@Entity
@Table(name="Roles")
public class Role {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String role;

    public Role(){

    }

    public Role(Integer id, String role){
        this.Id = id;
        this.role = role;
    }

    public Role(String role){
        this.role = role;
    }

    public int getId(){
        return Id;
    }
    public String getRole(){return role;}
}
