// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import com.osys.wallet.model.Invoice;

@RestResource
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {

	Invoice save(@Param("invoice") Invoice invoice);

	@PreAuthorize("@invoiceRepository.findById(#id)?.manager?.sub LIKE ?#{ principal?.name }")
	void deleteById(@Param("id") Long id);	
	
	@PreAuthorize("#invoice?.manager?.sub LIKE ?#{ principal?.name }")
	void delete(@Param("invoice") Invoice invoice);

	@PreAuthorize("#invoice?.manager == null or #invoice?.manager?.sub LIKE ?#{ principal?.name }")
	Optional<Invoice> findById(@Param("id") Long id);	
	
	@Override
	@Query("SELECT e FROM Invoice e WHERE e.manager.sub LIKE ?#{ principal?.name }")	
	Page<Invoice> findAll(Pageable pageable);

	@Query("SELECT SUM(e.amountMsat) FROM Invoice e WHERE e.manager.sub LIKE ?#{ principal?.name } AND e.status IN ('paid','internalpaid')")	
	Long totalAmount();	

	@Query("SELECT e FROM Invoice e WHERE e.status = 'pending'")	
	List<Invoice> findAllPending();

	Optional<Invoice> findByBolt11(@Param("bolt11") String bolt11);	

}