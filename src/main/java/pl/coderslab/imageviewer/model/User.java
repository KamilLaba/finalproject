package pl.coderslab.imageviewer.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;
    private String password;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RoleId", referencedColumnName = "Id")
    private Role role;

    public User(){

    }

    public User(int id, String name, String password){
        this.Id = id;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public int getId(){
        return Id;
    }

    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public Role getRole(){
        return role;
    }
    public void setRole(Role role){ this.role = role;};
}
