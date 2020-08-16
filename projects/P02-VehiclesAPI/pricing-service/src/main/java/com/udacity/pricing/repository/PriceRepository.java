package com.udacity.pricing.repository;

import com.udacity.pricing.entity.Price;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends CrudRepository <Price, Long> {

    List<Price> findByVehicleId(@Param("vehicleId") String name);

}
