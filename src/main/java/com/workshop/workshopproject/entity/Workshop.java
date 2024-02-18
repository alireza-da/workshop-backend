package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workshop")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
@DynamicUpdate
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id")
    private int id;

    @NotNull(message = "This field can not be left empty")
    @NotEmpty(message = "This field can not be left empty")
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonManagedReference(value = "workshop-offered")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workshop", fetch = FetchType.LAZY)
    private List<OfferedWorkshop> offeredWorkshops = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workshop")
    private List<WorkshopDetail> workshopDetails = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Workshop() {

    }

    public Workshop(@NotNull(message = "This field can not be left empty") @NotEmpty(message = "This field can not be left empty") String title, String description, String imageUrl, List<OfferedWorkshop> offeredWorkshops, List<WorkshopDetail> workshopDetails, Manager manager) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.offeredWorkshops = offeredWorkshops;
        this.workshopDetails = workshopDetails;
        this.manager = manager;
    }

    //    public Workshop(String title, BigDecimal price, String imageUrl, List<OfferedWorkshop> offeredWorkshops, List<WorkshopDetail> workshopDetails) {
//        this.title = title;
//        this.price = price;
//        this.imageUrl = imageUrl;
//        this.offeredWorkshops = offeredWorkshops;
//        this.workshopDetails = workshopDetails;
//    }
//
//    public Workshop(String title, BigDecimal price, String imageUrl, List<OfferedWorkshop> offeredWorkshops, List<WorkshopDetail> workshopDetails, Manager manager) {
//        this.title = title;
//        this.price = price;
//        this.imageUrl = imageUrl;
//        this.offeredWorkshops = offeredWorkshops;
//        this.workshopDetails = workshopDetails;
//        this.manager = manager;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    //    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }



    public List<OfferedWorkshop> getOfferedWorkshops() {
        return offeredWorkshops;
    }

    public void setOfferedWorkshops(List<OfferedWorkshop> offeredWorkshops) {
        this.offeredWorkshops = offeredWorkshops;
    }

    public List<WorkshopDetail> getWorkshopDetails() {
        return workshopDetails;
    }

    public void setWorkshopDetails(List<WorkshopDetail> workshopDetails) {
        this.workshopDetails = workshopDetails;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
