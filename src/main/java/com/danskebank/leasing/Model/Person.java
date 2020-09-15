package com.danskebank.leasing.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Table(name = "person")
@Data
@Entity(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "person_id")
    private int person_id;

    @Column(name = "person_income")
    private int personIncome;

    @Column(name = "requested_funding")
    private int requestedFunding;

    @OneToMany(
            targetEntity = CoPerson.class,
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CoPerson> person = new ArrayList<>();

    @OneToOne(
            targetEntity = Vehicle.class,
            mappedBy = "person",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Vehicle vehicle;
}
