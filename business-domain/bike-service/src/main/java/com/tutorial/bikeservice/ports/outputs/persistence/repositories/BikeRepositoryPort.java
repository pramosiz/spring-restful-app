package com.tutorial.bikeservice.ports.outputs.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.tutorial.bikeservice.domain.V2.model.BikeV2;

import jakarta.transaction.Transactional;

@Repository
public interface BikeRepositoryPort extends JpaRepository<BikeV2, Long> {

	List<BikeV2> findByUserId(Long userId);

	@Modifying
	@Transactional
	void deleteByUserId(Long userId);
}
