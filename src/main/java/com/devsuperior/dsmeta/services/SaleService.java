package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.repositories.SaleSummaryProjection;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	public List<SaleSummaryMinDTO> summary(String minDate, String maxDate) {
		LocalDate min;
		LocalDate max;
		try {
			min = LocalDate.parse(minDate, DateTimeFormatter.ISO_DATE);
		} catch (DateTimeParseException e) {
			min = LocalDate.now().minusYears(1L);
		}

		try {
			max = LocalDate.parse(maxDate, DateTimeFormatter.ISO_DATE);
		} catch (DateTimeParseException e) {
			max = LocalDate.now();
		}
		List<SaleSummaryProjection> listProjection = repository.summary(min, max);
		List<SaleSummaryMinDTO> listDto = listProjection.stream().map(x -> new SaleSummaryMinDTO(x)).collect(Collectors.toList());
		 return listDto;
	}

	public Page<SaleReportMinDTO> report(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate min;
		LocalDate max;
		try {
			min = LocalDate.parse(minDate, DateTimeFormatter.ISO_DATE);
		} catch (DateTimeParseException e) {
			min = LocalDate.now().minusYears(1L);
		}

		try {
			max = LocalDate.parse(maxDate, DateTimeFormatter.ISO_DATE);
		} catch (DateTimeParseException e) {
			max = LocalDate.now();
		}
		return repository.report(min, max, name, pageable);
	}
}
