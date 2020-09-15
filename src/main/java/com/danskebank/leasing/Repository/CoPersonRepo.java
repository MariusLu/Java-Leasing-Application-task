package com.danskebank.leasing.Repository;

import com.danskebank.leasing.Model.CoPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoPersonRepo extends JpaRepository<CoPerson, Long> {
}
