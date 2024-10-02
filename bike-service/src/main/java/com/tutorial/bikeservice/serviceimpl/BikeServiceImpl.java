package com.tutorial.bikeservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.bikeservice.repository.domains.Bike;
import com.tutorial.bikeservice.repository.repositories.BikeRepository;
import com.tutorial.bikeservice.service.BikeService;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;
import com.tutorial.bikeservice.serviceimpl.mapper.BikeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BikeServiceImpl implements BikeService {

	private final BikeRepository bikeRepository;

	private final BikeMapper bikeMapper;

	public List<BikeDTO> getAll() {
		//@formatter:off
		return bikeRepository.findAll()
					.stream()
					.map(bikeMapper::bike_2_BikeDTO)
					.collect(Collectors.toList());
		//@formatter:on
	}

	public Optional<BikeDTO> getById(Long id) {
		return bikeRepository.findById(id).map(bikeMapper::bike_2_BikeDTO);
	}

	public BikeDTO saveNewBike(NewBikeDTO newBikeDTO) {
		Bike bikeSaved = bikeRepository.save(bikeMapper.newBikeDto_2_Bike(newBikeDTO));
		return bikeMapper.bike_2_BikeDTO(bikeSaved);
	}

}
