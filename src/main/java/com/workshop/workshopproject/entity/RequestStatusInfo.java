package com.workshop.workshopproject.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workshop.workshopproject.enums.RequestStatus;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Table(name = "request_status_info")
@DynamicUpdate
public class RequestStatusInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "info")
    private String info;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "request_date")
    @NotNull(message = "This field can not be left empty")
    private Calendar requestedDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "response_date")
    private Calendar responseDate;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    public RequestStatusInfo() {

    }

    public RequestStatusInfo(String info, Calendar requestedDate, Calendar responseDate, RequestStatus requestStatus) {
        this.info = info;
        this.requestedDate = requestedDate;
        this.responseDate = responseDate;
        this.requestStatus = requestStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Calendar getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Calendar requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Calendar getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Calendar responseDate) {
        this.responseDate = responseDate;
    }
}
