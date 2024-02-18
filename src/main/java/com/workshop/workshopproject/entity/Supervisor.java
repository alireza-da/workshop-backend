package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.workshop.workshopproject.enums.RequestStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "supervisor")
@DynamicUpdate
@DiscriminatorValue(value = "supervisor")
public class Supervisor extends Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JoinColumn(name = "id")
//    private int id;

    //    @JsonManagedReference(value = "workshop-supervisor")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supervisor", fetch = FetchType.LAZY)
    private List<OfferedWorkshop> offeredWorkshops;

    public Supervisor() {
    }

    public Supervisor(List<OfferedWorkshop> offeredWorkshops) {
        this.offeredWorkshops = offeredWorkshops;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public List<OfferedWorkshop> getOfferedWorkshops() {
        return offeredWorkshops;
    }

    public void setOfferedWorkshops(List<OfferedWorkshop> offeredWorkshops) {
        this.offeredWorkshops = offeredWorkshops;
    }

    public void setGraderRequestStatus(int requestId, RequestStatusInfo requestStatusInfo) {
        for (OfferedWorkshop offeredWorkshop : this.offeredWorkshops) {
            for (GraderRequest graderRequest : offeredWorkshop.getGraderRequests()) {
                if (graderRequest.getId() == requestId) {
                    graderRequest.setRequestStatusInfo(requestStatusInfo);
                }
            }
        }
    }

    public OfferedWorkshop hasWorkshop(int workshopId) {
        for (OfferedWorkshop offeredWorkshop : offeredWorkshops) {
            if (offeredWorkshop.getId() == workshopId) {
                return offeredWorkshop;
            }
        }
        return null;
    }
}
