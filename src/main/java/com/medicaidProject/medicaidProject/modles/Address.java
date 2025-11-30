package com.medicaidProject.medicaidProject.modles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zip;

    public Address() {}

    public Address(String street, String city, String state, String zip, User user ,Employer employer) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.user = user;
        this.employer = employer;
    }

    // Address belongs to either a User OR an Employer
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

   @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    // Getters & Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public User getUser() { return user; }
    public void setUser(User user) {
        this.user = user;
        this.employer = null; // ensure only one is set
    }

    public Employer getEmployer() { return employer; }
    public void setEmployer(Employer employer) {
        this.employer = employer;
        this.user = null; // ensure only one is set
    }
}

