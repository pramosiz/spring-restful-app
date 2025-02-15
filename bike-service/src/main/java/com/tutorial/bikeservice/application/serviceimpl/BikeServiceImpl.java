package com.tutorial.bikeservice.application.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.bikeservice.adapters.outputs.feign.utils.FeignUtils;
import com.tutorial.bikeservice.application.services.BikeService;
import com.tutorial.bikeservice.domain.V2.dto.BikeDtoV2;
import com.tutorial.bikeservice.domain.V2.dto.NewBikeDtoV2;
import com.tutorial.bikeservice.domain.V2.mapper.BikeMapperV2;
import com.tutorial.bikeservice.domain.V2.model.BikeV2;
import com.tutorial.bikeservice.ports.outputs.feign.clients.UserFeignPortV2;
import com.tutorial.bikeservice.ports.outputs.persistence.repositories.BikeRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BikeServiceImpl implements BikeService {

	private final BikeRepositoryPort bikeRepository;

	private final BikeMapperV2 bikeMapperV2;

	private final UserFeignPortV2 userFeignClient;

	public List<BikeDtoV2> getAll() {
		//@formatter:off
		return bikeRepository.findAll()
					.stream()
					.map(bikeMapperV2::bikeV2_2_BikeDtoV2)
					.collect(Collectors.toList());
		//@formatter:on
	}

	public Optional<BikeDtoV2> getById(Long id) {
		return bikeRepository.findById(id).map(bikeMapperV2::bikeV2_2_BikeDtoV2);
	}

	public Optional<BikeDtoV2> saveNewBikeWithExternalCheck(NewBikeDtoV2 newBikeDTO) {
		return FeignUtils.safeFeignCall(() -> userFeignClient.getById(newBikeDTO.getUserId())
				.map(userFeignDto -> {
					BikeV2 bikeSaved = bikeRepository.save(bikeMapperV2.newBikeDtoV2_2_BikeV2(newBikeDTO));
					return bikeMapperV2.bikeV2_2_BikeDtoV2(bikeSaved);
				}).orElseThrow(() -> new RuntimeException("User not found")));
	}

	public List<BikeDtoV2> getByUserId(Long id) {
		return bikeRepository.findByUserId(id)
				.stream()
				.map(bikeMapperV2::bikeV2_2_BikeDtoV2)
				.collect(Collectors.toList());
	}

	public void deleteById(Long id) {
		bikeRepository.deleteById(id);
	}

	public void deleteByUserId(Long id) {
		bikeRepository.deleteByUserId(id);
	}
}
