package com.medicaidProject.medicaidProject.modles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class UserEmploymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Employer name is required.")
    private String employerName;

    @NotBlank(message = "Employer Tax ID is required.")
    @Pattern(regexp = "\\d{9}", message = "Tax ID must be 9 digits.")
    private String employerTaxId;

    @NotBlank(message = "TIN is required.")
    @Pattern(regexp = "\\d{9}", message = "TIN must be 9 digits.")
    private String tin;

    @NotBlank(message = "Street address is required.")
    private String address;

    @NotBlank(message = "State is required.")
    private String state;

    @NotBlank(message = "ZIP code is required.")
    @Pattern(regexp = "\\d{5}", message = "ZIP must be 5 digits.")
    private String zip;

    @NotBlank(message = "City is required.")
    private String city;

    @NotBlank(message = "Country is required.")
    private String country;

    @NotNull(message = "Employment status is required.")
    private Boolean isCurrentlyEmployed;

    @NotBlank(message = "Hours worked is required.")
    private String hoursWorked;

    @NotBlank(message = "Employment length is required.")
    private String employmentLength;

    private String notes;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isVerified = false;

    @NotNull(message ="Must verify information is accurate to continue")
    private Boolean certified;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserEmploymentInfo(String employerName, String employerTaxId, String tin, String address,
                              String state, String zip, String city, String country,
                              Boolean isCurrentlyEmployed, String hoursWorked, String employmentLength,
                              Boolean isVerified, User user) {
        this.employerName = employerName;
        this.employerTaxId = employerTaxId;
        this.tin = tin;
        this.address = address;
        this.state = state;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.isCurrentlyEmployed = isCurrentlyEmployed;
        this.hoursWorked = hoursWorked;
        this.employmentLength = employmentLength;
        this.isVerified = isVerified;
        this.user = user;
    }

    public UserEmploymentInfo(){};

    public Long getId() {
        return id;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerTaxId() {
        return employerTaxId;
    }

    public void setEmployerTaxId(String employerTaxId) {
        this.employerTaxId = employerTaxId;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public Boolean getIsCurrentlyEmployed() {  // match the field name
        return isCurrentlyEmployed;
    }

    public void setIsCurrentlyEmployed(Boolean isCurrentlyEmployed) {
        this.isCurrentlyEmployed = isCurrentlyEmployed;
    }


    public String getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(String hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getEmploymentLength() {
        return employmentLength;
    }

    public void setEmploymentLength(String employmentLength) {
        this.employmentLength = employmentLength;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() { return user; }

    public void setUser(User user) {
        this.user = user;
    }

    public  Boolean getIsVerified() {return isVerified;}

    public void setIsVerified(Boolean isVerified){this.isVerified = isVerified;}

    public Boolean getCertified() { return certified; }

    public void setCertified(Boolean certified) { this.certified = certified; }
}
