package com.tutorial.bikeservice.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.bikeservice.repository.entities.Bike;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {

	List<Bike> findByUserId(Long userId);
}
