package com.medicaidProject.medicaidProject.modles;

import jakarta.persistence.*;

@Entity
@Table(name = "states")
public class States {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
     private String stateCode;

    @Column(name = "name")
    private String stateName;

    public Long getId() {
        return id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getStateName() {
        return stateName;
    }
}
