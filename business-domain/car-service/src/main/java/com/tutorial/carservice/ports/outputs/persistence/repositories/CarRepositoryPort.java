package com.tutorial.carservice.ports.outputs.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.tutorial.carservice.domain.V2.model.Car;

import jakarta.transaction.Transactional;

@Repository
public interface CarRepositoryPort extends JpaRepository<Car, Long> {

	List<Car> findByUserId(Long userId);

	@Modifying
	@Transactional
	void deleteByUserId(Long userId);
}
