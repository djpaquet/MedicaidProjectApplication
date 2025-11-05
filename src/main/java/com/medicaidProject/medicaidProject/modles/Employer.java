package com.medicaidProject.medicaidProject.modles;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.io.Serializable;

@Entity
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employerName;
    private String firstName;
    private String lastName;
    private String employerTaxId;
    private String email;
    private String password;
    @Transient
    private String verifyPassword;
    private String phone;

    public Employer(String employerName, String firstName, String lastName, String employerTaxId, String email, String password, String phone) {
        this.employerName = employerName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employerTaxId = employerTaxId;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Employer(){};

    public Long getId() {
        return id;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

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

    public String getEmployerTaxId() {
        return employerTaxId;
    }

    public void setEmployerTaxId(String employerTaxId) {
        this.employerTaxId = employerTaxId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
