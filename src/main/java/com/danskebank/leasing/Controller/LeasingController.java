package com.danskebank.leasing.Controller;

import com.danskebank.leasing.Model.Person;
import com.danskebank.leasing.Model.VehicleAndDecision;
import com.danskebank.leasing.Repository.CoPersonRepo;
import com.danskebank.leasing.Repository.LeasingRepo;
import com.danskebank.leasing.Repository.VehicleRepo;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
public class LeasingController {

    static Logger log = Logger.getLogger(LeasingController.class.getName());

    final LeasingRepo leasingRepo;
    final CoPersonRepo coPersonRepo;
    final VehicleRepo vehicleRepo;
    public LeasingController(LeasingRepo leasingRepo, VehicleRepo vehicleRepo, CoPersonRepo coPersonRepo) {
        this.leasingRepo = leasingRepo;
        this.vehicleRepo = vehicleRepo;
        this.coPersonRepo = coPersonRepo;
    }

    @Transactional
    @PostMapping(value = "/sendInformation")
    public ResponseEntity<?> sendInformation(@RequestBody Person person){
        if(person == null){
            return ResponseEntity.badRequest().build();
        }

        if(person.getVehicle() == null){
            return ResponseEntity.badRequest().build();
        }

        VehicleAndDecision decision = new VehicleAndDecision();

        person.getVehicle().setPerson(person);
        person.getPerson().
                stream().
                forEach(s -> s.setPerson(person));
        leasingRepo.save(person);
        log.info("New data was saved.");

        Integer familyIncome = person
                .getPerson()
                .stream()
                .map(s -> s.getCoApplicatanIncome())
                .mapToInt(Integer::valueOf)
                .sum();
        familyIncome = familyIncome + person.getPersonIncome();


        if(familyIncome >= 600){
            log.info(person.getPerson_id()+" can get leasing.");

            decision.setDecision("Approved");
            decision.setMake(person.getVehicle().getMake());
            decision.setModel(person.getVehicle().getModel());
            decision.setVinNumber(person.getVehicle().getVinNumber());

            return ResponseEntity.ok(decision);
        }
        log.info(person.getPerson_id()+" can't get leasing.");
        
        decision.setDecision("Denied");
        decision.setMake(person.getVehicle().getMake());
        decision.setModel(person.getVehicle().getModel());
        decision.setVinNumber(person.getVehicle().getVinNumber());

        return ResponseEntity.ok(person.getVehicle());
    }
    @GetMapping(value = "/getInformation/{personIdString}")
    public ResponseEntity<?> getInformation(@PathVariable String personIdString){
        Integer personId;
        try{
             personId = Integer.parseInt(personIdString);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Wrong Person ID");
        }
        Person person = leasingRepo.findByPersonId(personId);
        if(person == null){
            return ResponseEntity.badRequest().body("Person ID Does Not Exists");
        }

        VehicleAndDecision decision = new VehicleAndDecision();

        Integer familyIncome = person
                .getPerson()
                .stream()
                .map(s -> s.getCoApplicatanIncome())
                .mapToInt(Integer::valueOf)
                .sum();
        familyIncome = familyIncome + person.getPersonIncome();
        if(familyIncome >= 600){

            decision.setDecision("Approved");
            decision.setMake(person.getVehicle().getMake());
            decision.setModel(person.getVehicle().getModel());
            decision.setVinNumber(person.getVehicle().getVinNumber());
            log.info(person.getPerson_id()+" can get leasing.");

            return ResponseEntity.ok(decision);
        }
        log.info(person.getPerson_id()+" can't get leasing.");
        decision.setDecision("Denied");
        decision.setMake(person.getVehicle().getMake());
        decision.setModel(person.getVehicle().getModel());
        decision.setVinNumber(person.getVehicle().getVinNumber());
        return ResponseEntity.ok(decision);
    }
}
