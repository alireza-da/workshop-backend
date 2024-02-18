package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.*;
import com.workshop.workshopproject.enums.ContactType;
import com.workshop.workshopproject.enums.Gender;
import com.workshop.workshopproject.service.UserServiceImpl;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
@DynamicUpdate
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", insertable = false, updatable = false)
//    private int id;

    @Id
    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "password")
    private String password;

//    @NotNull(message = "This field can not be left empty")
//    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birth_date")
    private Calendar birthDate;

    @Column(name = "image_url")
    private String imageUrl;

    //    @JsonManagedReference(value = "user-contact")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<ContactPoint> contactPoints = new ArrayList<>();

    @Column(name = "oauth")
    private String oauthId;



//    @ManyToMany(fetch=FetchType.LAZY,
//            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinTable(
//            name="role_user",
//            joinColumns=@JoinColumn(name="user_id"),
//            inverseJoinColumns=@JoinColumn(name="role_id")
//    )

    //    @Any(metaDef = "role", metaColumn = @Column(name = "role_type"))
//    @AnyMetaDef(idType = "integer", metaType = "string", // and this for interface
//        metaValues = {
//                @MetaValue(targetEntity = Student.class, value = "student"),
//                @MetaValue(targetEntity = Grader.class, value = "grader"),
//                @MetaValue(targetEntity = Accountant.class, value = "accountant"),
//                @MetaValue(targetEntity = Supervisor.class, value = "supervisor"),
//                @MetaValue(targetEntity = Manager.class, value = "manager"),
//        })
////    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinColumn(name = "user_id")
//    @ManyToAny(
//            metaColumn = @Column( name = "id" ) )
//    @AnyMetaDef(
//            idType = "integer",
//            metaType = "string",
//            metaValues = {
//                    @MetaValue(targetEntity = Student.class, value = "user"),
//                    @MetaValue(targetEntity = Grader.class, value = "grader"),
//                    @MetaValue(targetEntity = Accountant.class, value = "accountant"),
//                    @MetaValue(targetEntity = Supervisor.class, value = "supervisor"),
//                    @MetaValue(targetEntity = Manager.class, value = "manager"),
//            })
//    //    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    @Any(metaDef = "role", metaColumn = @Column(name = "id"))
//    @AnyMetaDef(idType = "integer", metaType = "string",
//            metaValues = {
//                    @MetaValue(targetEntity = Student.class, value = "user"),
//                    @MetaValue(targetEntity = Grader.class, value = "grader"),
//                    @MetaValue(targetEntity = Accountant.class, value = "accountant"),
//                    @MetaValue(targetEntity = Supervisor.class, value = "supervisor"),
//                    @MetaValue(targetEntity = Manager.class, value = "manager"),
//            })
//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
//    @JoinColumn(name = "user_id")
//    @ManyToAny( // from here to join table is the answer
//            metaDef = "RoleMetaDef",
//            metaColumn = @Column(name = "role_title")
//    )
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @JoinTable(name = "role_user",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    @JsonManagedReference(value = "user-role")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles = new ArrayList<>();

    public User() {

    }

    public User(@NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String username, @NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String firstName, @NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String lastName, @NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String password, Gender gender, Calendar birthDate, String imageUrl, List<ContactPoint> contactPoints, List<Role> roles) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
        this.contactPoints = contactPoints;
        this.roles = roles;
    }

    //    public User(String firstName, String lastName, String username, String password, Gender gender, Calendar birthDate, List<ContactPoint> contactPoints, Role role, List<Role> roles) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.username = username;
//        this.password = password;
//        this.gender = gender;
//        this.birthDate = birthDate;
//        this.contactPoints = contactPoints;
//        this.role = role;
//        this.roles = roles;
//    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public List<ContactPoint> getContactPoints() {
        return contactPoints;
    }

    public void setContactPoints(List<ContactPoint> contactPoints) {
        this.contactPoints = contactPoints;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    public Role hasRole(Class role) {
        for (Role userRole : this.roles) {
            if (role.isInstance(userRole)) {
                return userRole;
            }
        }
        return null;
    }

    //    @ManyToAny(
//            metaColumn = @Column( name = "id" ) )
//    @AnyMetaDef(
//            idType = "integer",
//            metaType = "string",
//            metaValues = {
//                    @MetaValue(targetEntity = Student.class, value = "user"),
//                    @MetaValue(targetEntity = Grader.class, value = "grader"),
//                    @MetaValue(targetEntity = Accountant.class, value = "accountant"),
//                    @MetaValue(targetEntity = Supervisor.class, value = "supervisor"),
//                    @MetaValue(targetEntity = Manager.class, value = "manager"),
//            })
////    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @JoinColumn(name = "user_id")
////    @Any(metaDef = "role", metaColumn = @Column(name = "id"))
////    @AnyMetaDef(idType = "integer", metaType = "string",
////            metaValues = {
////                    @MetaValue(targetEntity = Student.class, value = "user"),
////                    @MetaValue(targetEntity = Grader.class, value = "grader"),
////                    @MetaValue(targetEntity = Accountant.class, value = "accountant"),
////                    @MetaValue(targetEntity = Supervisor.class, value = "supervisor"),
////                    @MetaValue(targetEntity = Manager.class, value = "manager"),
////            })
////    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
////    @JoinColumn(name = "user_id")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void updateContactPoint(ContactType contactType, String contactData) {
        boolean found = false;
        for (ContactPoint contactPoint : this.contactPoints) {
            if (contactPoint.getContactType() == contactType) {
                found = true;
                contactPoint.setContactData(contactData);
            }
        }
        if (!found) {
            ContactPoint contactPoint = new ContactPoint();
            contactPoint.setContactType(contactType);
            contactPoint.setContactData(contactData);
            this.contactPoints.add(contactPoint);
        }
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}
