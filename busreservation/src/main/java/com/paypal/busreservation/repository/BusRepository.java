package com.paypal.busreservation.repository;

import com.paypal.busreservation.entity.Bus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BusRepository extends JpaRepository<Bus, Long>, JpaSpecificationExecutor<Bus>,
    PagingAndSortingRepository<Bus,Long> {

  Optional<Bus> findByBusNumber(String busNumber);
}
