package com.tutorial.bikeservice.repository.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bikes", schema = "user-app")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String brand;

	@Column
	private String model;

	@Column(name = "user_id")
	private Long userId;
}
