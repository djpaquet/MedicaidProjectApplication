/*Developed by Dana Paquet*/
/*v2 added employer tax is and pin number - Dana Paquet*/

package com.medicaidProject.medicaidProject.modles;
import java.time.LocalDate;
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String username;
    private String pin;

    private String employerTaxId;
    private String password;
    private String verifyPassword;

    public User(String lastName, String firstName, LocalDate dateOfBirth, String phone, String username, String email, String password, String pin){
        this();
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.pin = pin;
        this.employerTaxId = getEmployerTaxId();
    }

    public User(){}

    public int getId() {
        return id;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getPin() {return pin;}

    public void setPin(String pin) {this.pin = pin;}

    public void setEmployerTaxId(String employerTaxId) {
        this.employerTaxId = employerTaxId;
    }

    public String getEmployerTaxId() {return pin;}

    public void getEmployerTaxId(String pin) {this.pin = pin;}
}
