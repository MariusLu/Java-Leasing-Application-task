package com.danskebank.leasing.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "vehicle")
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "vin_number")
    private String vinNumber;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_table_id")
    @JsonIgnore
    private Person person;
}
