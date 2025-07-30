// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.osys.wallet.model.BitcoinAddress;

@RepositoryRestResource
public interface BitcoinAddressRepository extends PagingAndSortingRepository<BitcoinAddress, Long>, CrudRepository<BitcoinAddress, Long> {

	BitcoinAddress save(BitcoinAddress bitcoinaddress);

   	@Override
	@Query("SELECT e FROM BitcoinAddress e WHERE e.manager.sub LIKE ?#{ principal?.name }")	
	Page<BitcoinAddress> findAll(Pageable pageable);

	@Query("SELECT e FROM BitcoinAddress e WHERE e.manager.sub LIKE ?#{ principal?.name }")	
	Iterable<BitcoinAddress> findAllByManager();

}
