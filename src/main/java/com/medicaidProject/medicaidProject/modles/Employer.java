package com.medicaidProject.medicaidProject.modles;
import java.io.Serializable;

public class Employer implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String employerName;
    private String firstName;
    private String lastName;
    private String employerTaxId;
    private String email;
    private String password;
    private String verifyPassword;
    private String phone;
    private String address;
    private String zip;
    private String state;
    private String city;

    public Employer(String employerName, String firstName, String lastName, String employerTaxId, String email, String password, String phone, String address, String state,String city, String zip) {
        this.employerName = employerName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employerTaxId = employerTaxId;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.state = state;
        this.city = city;
        this.zip = zip;
    }

    public Employer(){};

    public Integer getId() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
