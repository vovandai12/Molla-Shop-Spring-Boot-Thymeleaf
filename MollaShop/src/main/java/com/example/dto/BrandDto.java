package com.example.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

	private Long id;
	
	@NotBlank(message = "{notblank.brandName}")
	private String name;
}
