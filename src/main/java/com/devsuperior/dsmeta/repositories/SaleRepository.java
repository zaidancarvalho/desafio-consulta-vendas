package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	
	@Query(nativeQuery = true, value = "SELECT "
	        + "tb_seller.name AS sellerName, "
	        + "SUM(CASE WHEN tb_sales.date >= :minDate AND tb_sales.date <= :maxDate THEN tb_sales.amount ELSE 0 END) AS total "
	        + "FROM tb_sales "
	        + "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
	        + "WHERE tb_sales.date >= CASE WHEN :minDate IS NULL THEN DATEADD('MONTH', -12, CURRENT_TIMESTAMP) ELSE :minDate END "
	        + "AND tb_sales.date <= CASE WHEN :maxDate IS NULL THEN CURRENT_TIMESTAMP ELSE :maxDate END "
	        + "GROUP BY tb_seller.name "
	        + "ORDER BY tb_seller.name; ")
	List<SaleSummaryProjection> summary(LocalDate minDate, LocalDate maxDate);

	//Query em jpql
/*	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryMinDTO(s.seller.name, SUM(s.amount) AS amount) "
			+ "FROM Sale s " + "JOIN s.seller "
			+ "WHERE (:minDate IS NULL OR s.date >= :minDate) AND (:maxDate IS NULL OR s.date <= :maxDate) "
			+ "GROUP BY s.seller.name")
	public List<SaleSummaryMinDTO> summary(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);*/

	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportMinDTO(s.id, s.date, s.amount, se.name AS sellerName) "
			+ "FROM Sale s "
			+ "JOIN s.seller se " 
			+ "WHERE (s.date >= :minDate AND s.date <= :maxDate) "
			+ "AND UPPER(se.name) LIKE CONCAT('%', UPPER(:name), '%')")
	public Page<SaleReportMinDTO> report(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
}
