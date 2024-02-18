package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "role")
@DiscriminatorColumn(name = "title")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "title"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "student"),
        @JsonSubTypes.Type(value = Supervisor.class, name = "supervisor"),
        @JsonSubTypes.Type(value = Manager.class, name = "manager"),
        @JsonSubTypes.Type(value = Accountant.class, name = "accountant"),
        @JsonSubTypes.Type(value = Grader.class, name = "grader"),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@DynamicUpdate
public abstract class Role {
    //    int getId();
//    void setId(int id);
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;

//    @JsonBackReference(value = "user-role")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;


//

    public Role() {
    }

    public Role(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    @Column(name = "title")
//    private String title;

//    @ManyToMany(fetch=FetchType.LAZY,
//            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinTable(
//            name="role_user",
//            joinColumns=@JoinColumn(name="role_id"),
//            inverseJoinColumns=@JoinColumn(name="user_id")
//    )
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;

//    public Role() {
//
//    }
//
//    public Role(String title) {
//        this.title = title;
//    }
//
//    public Role(String title, List<User> users) {
//        this.title = title;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

}
