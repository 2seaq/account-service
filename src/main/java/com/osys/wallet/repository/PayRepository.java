// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import com.osys.wallet.model.Pay;

public interface PayRepository extends PagingAndSortingRepository<Pay, Long>, CrudRepository<Pay, Long> {

	@PreAuthorize("#pay?.manager == null or #pay?.manager?.sub LIKE ?#{ principal?.name }")
	Pay save(@Param("pay") Pay pay);

	@PreAuthorize("@payRepository.findById(#id)?.manager?.sub LIKE ?#{ principal?.name }")
	void deleteById(@Param("id") Long id);

	@PreAuthorize("#pay?.manager?.sub LIKE ?#{ principal?.name }")
	void delete(@Param("pay") Pay pay);

	@Query("SELECT e FROM Pay e WHERE e.manager.sub LIKE ?#{ principal?.name }")	
	Iterable<Pay> findAllByManager();
	
	@Query("SELECT SUM(e.amountMsat) FROM Pay e WHERE e.manager.sub LIKE ?#{ principal?.name } AND e.status IN ('success','internalsuccess')")	
	Long totalAmount();	

	Optional<Pay> findByBolt11(@Param("bolt11") String bolt11);	

	@Override
	@Query("SELECT e FROM Pay e WHERE e.manager.sub LIKE ?#{ principal?.name }")	
	Page<Pay> findAll(Pageable pageable);
}
