package com.danskebank.leasing.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "coperson")
public class CoPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "co_applicant_person_id")
    private String CoApplicatanPersonId;

    @Column(name = "co_applicant_person_income")
    private String CoApplicatanIncome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_table_id")
    @JsonIgnore
    private Person person;
}
