package com.danskebank.leasing.Repository;

import com.danskebank.leasing.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
}
