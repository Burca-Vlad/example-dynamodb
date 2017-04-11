package com.example.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.models.User;

@EnableScan
@EnableScanCount
public interface UserRepository extends PagingAndSortingRepository<User, String> {
	Page<User> findByLastName(String lastName, Pageable pageable);
	public Page<User> findAll(Pageable pageable);
}
