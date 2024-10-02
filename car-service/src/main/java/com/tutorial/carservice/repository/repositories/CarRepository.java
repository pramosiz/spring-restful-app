package com.tutorial.carservice.repository.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.carservice.repository.domains.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	List<Car> findByUserId(Long userId);
}
