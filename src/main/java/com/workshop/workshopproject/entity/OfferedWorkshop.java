package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.*;
import com.workshop.workshopproject.utils.DateHandler;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "offered_workshop")
@DynamicUpdate
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@JsonIgnoreProperties("requests")
public class OfferedWorkshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
    private List<WorkshopFormRelation> workshopFormRelations = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
//    private List<GraderRequest> graderRequests = new ArrayList<>();
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
//    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
    private List<Registration> registrations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
    private List<GraderRequest> graderRequests = new ArrayList<>();

    @JsonManagedReference(value = "workshop-group")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
    private List<StudentGroup> studentGroups = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredWorkshop")
    private List<FilledEvaluationForm> filledEvaluationForms;

    //    @JsonBackReference(value = "workshop-supervisor")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "supervisor_id")
    private Supervisor supervisor;

    @JsonBackReference(value = "workshop-offered")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    //    @Column(name = "workshop_time")
//    @ElementCollection
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar startTime;

//    @ElementCollection
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Calendar endTime;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "image")
    private String imageUrl;

    @NotNull(message = "This field can not be left empty")
    @Column(name = "price")
    private BigDecimal price; // string

    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "place")
    private String location;

    @NotNull(message = "This field can not be left empty")
    private int numberOfInstallments;

    public OfferedWorkshop() {

    }

//    public OfferedWorkshop(List<WorkshopFormRelation> workshopFormRelations, List<Request> requests, List<StudentGroup> studentGroups, Supervisor supervisor, Workshop workshop, List<Calendar> startTimes, List<Calendar> endTimes, String description, int capacity) {
//        this.workshopFormRelations = workshopFormRelations;
//        this.requests = requests;
//        this.studentGroups = studentGroups;
//        this.supervisor = supervisor;
//        this.workshop = workshop;
//        this.startTimes = startTimes;
//        this.endTimes = endTimes;
//        this.description = description;
//        this.capacity = capacity;
//    }


    //    public OfferedWorkshop(List<WorkshopFormRelation> workshopFormRelations, List<GraderRequest> graderRequests, List<Registration> registrations, List<StudentGroup> studentGroups, List<Calendar> dates, String description, int capacity) {
//        this.workshopFormRelations = workshopFormRelations;
//        this.graderRequests = graderRequests;
//        this.registrations = registrations;
//        this.studentGroups = studentGroups;
//        this.dates = dates;
//        this.description = description;
//        this.capacity = capacity;
//    }
//
//    public OfferedWorkshop(List<WorkshopFormRelation> workshopFormRelations, List<GraderRequest> graderRequests, List<Registration> registrations, List<StudentGroup> studentGroups, Supervisor supervisor, Workshop workshop, List<Calendar> dates, String description, int capacity) {
//        this.workshopFormRelations = workshopFormRelations;
//        this.graderRequests = graderRequests;
//        this.registrations = registrations;
//        this.studentGroups = studentGroups;
//        this.supervisor = supervisor;
//        this.workshop = workshop;
//        this.dates = dates;
//        this.description = description;
//        this.capacity = capacity;
//    }

    //    public OfferedWorkshop(List<GraderEvaluationForm> graderEvaluationForms, List<GraderRequest> graderRequests, List<Registration> registrations, List<Calendar> dates, String description, int capacity) {
//        this.graderEvaluationForms = graderEvaluationForms;
//        this.graderRequests = graderRequests;
//        this.registrations = registrations;
//        this.dates = dates;
//        this.description = description;
//        this.capacity = capacity;
//    }


    public OfferedWorkshop(List<WorkshopFormRelation> workshopFormRelations, List<Registration> registrations, List<GraderRequest> graderRequests, List<StudentGroup> studentGroups, List<FilledEvaluationForm> filledEvaluationForms, Supervisor supervisor, Workshop workshop, Calendar startTime, Calendar endTime, String description, int capacity, String imageUrl, @NotNull(message = "This field can not be left empty") BigDecimal price, @NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String location, @NotNull(message = "This field can not be left empty") int numberOfInstallments) {
        this.workshopFormRelations = workshopFormRelations;
        this.registrations = registrations;
        this.graderRequests = graderRequests;
        this.studentGroups = studentGroups;
        this.filledEvaluationForms = filledEvaluationForms;
        this.supervisor = supervisor;
        this.workshop = workshop;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.capacity = capacity;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
        this.numberOfInstallments = numberOfInstallments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public List<GraderEvaluationForm> getGraderEvaluationForms() {
//        return graderEvaluationForms;
//    }
//
//    public void setGraderEvaluationForms(List<GraderEvaluationForm> graderEvaluationForms) {
//        this.graderEvaluationForms = graderEvaluationForms;
//    }


    public List<WorkshopFormRelation> getWorkshopFormRelations() {
        return workshopFormRelations;
    }

    public void setWorkshopFormRelations(List<WorkshopFormRelation> workshopFormRelations) {
        this.workshopFormRelations = workshopFormRelations;
    }

//    public List<GraderRequest> getGraderRequests() {
//        return graderRequests;
//    }
//
//    public void setGraderRequests(List<GraderRequest> graderRequests) {
//        this.graderRequests = graderRequests;
//    }
//
//    public List<Registration> getRegistrations() {
//        return registrations;
//    }
//
//    public void setRegistrations(List<Registration> registrations) {
//        this.registrations = registrations;
//    }


    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public List<Registration> getRequests() {
        return registrations;
    }

    public void setRequests(List<Registration> requests) {
        this.registrations = requests;
    }

    public List<GraderRequest> getGraderRequests() {
        return graderRequests;
    }

    public void setGraderRequests(List<GraderRequest> graderRequests) {
        this.graderRequests = graderRequests;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<FilledEvaluationForm> getFilledEvaluationForms() {
        return filledEvaluationForms;
    }

    public void setFilledEvaluationForms(List<FilledEvaluationForm> filledEvaluationForms) {
        this.filledEvaluationForms = filledEvaluationForms;
    }

    public boolean hasEnrolled(Student student) {
        for (Registration registration : this.registrations) {
            for (Registration studentRegistration : student.getRegistrations()) {
                if (registration.getId() == studentRegistration.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<OfferedWorkshop> hasCoincidence(OfferedWorkshop offeredWorkshop, Student student) {
        List<OfferedWorkshop> offeredWorkshops = new ArrayList<>();
        for (Registration registration : student.getRegistrations()) {
            if (DateHandler.isCoincident(registration.getOfferedWorkshop().getStartTime(), registration.getOfferedWorkshop().getEndTime(), offeredWorkshop.getStartTime(), offeredWorkshop.getEndTime())) {
                offeredWorkshops.add(offeredWorkshop);
            }
        }
        return offeredWorkshops;
    }

    public boolean hasRequested(Grader grader) {
        for (GraderRequest graderRequest : this.graderRequests) {
            for (GraderRequest request : grader.getGraderRequests()) {
                if (graderRequest.getId() == request.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public GraderWorkshopRelation findGraderRole(Grader grader) {
        for (GraderRequest graderRequest : this.getGraderRequests()) {
            for (GraderRequest request : grader.getGraderRequests()) {
                if (graderRequest == request) {
                    return request.getGraderWorkshopRelation();
                }
            }
        }
        return null;
    }

//    public boolean isQualified(OfferedWorkshop offeredWorkshop, Student student) {
//        List<WorkshopDetail> prerequisites = offeredWorkshop.getWorkshop().getWorkshopDetails();
//        for (Registration registration : student.getRegistrations()) {
//            for (WorkshopDetail prerequisite : prerequisites) {
//                if (prerequisite.getRelatedWorkshop() == registration.getOfferedWorkshop().getWorkshop()) {
//
//                }
//            }
//        }
//    } // check if student has participated in prerequisites of a workshop
}
