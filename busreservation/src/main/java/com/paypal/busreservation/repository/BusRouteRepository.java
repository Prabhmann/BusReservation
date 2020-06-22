package com.paypal.busreservation.repository;

import com.paypal.busreservation.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

}
