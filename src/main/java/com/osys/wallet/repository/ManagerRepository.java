// Copyright (c) 2024 Chris Jackson
// Distributed under the MIT software license
package com.osys.wallet.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import com.osys.wallet.model.Manager;

@RestResource
public interface ManagerRepository extends PagingAndSortingRepository<Manager, Long>{

	Manager save(Manager manager);

	Manager findByName(String name);

	Optional<Manager> findByEmail(String email);

	Manager findById(Long id);

	Manager findBySub(String sub);
}