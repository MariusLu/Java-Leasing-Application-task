package com.danskebank.leasing.Repository;

import com.danskebank.leasing.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LeasingRepo extends JpaRepository<Person, Long> {
    @Query(value = "SELECT * FROM person  where person.person_id = :personId", nativeQuery = true)
    Person findByPersonId(@Param("personId") Integer personId);
}
