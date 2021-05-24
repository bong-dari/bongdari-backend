package com.usbridge.bongdari.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String password;

    private String name;

    private String uniqueName;

    private String contact;

    private String city;

    private String gu;

    private String address;

    private Float latitude;

    private Float longitude;

    private LocalDate createdDate;

    @OneToMany(mappedBy = "institution")
    private List<Volunteer> volunteers;
}
