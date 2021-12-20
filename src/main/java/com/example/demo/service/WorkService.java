package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Work;

@Repository
public interface WorkService extends JpaRepository<Work, Long> {
	
	 @Query(value = "SELECT w FROM Work w")
	  Page<Work> getListWork(Pageable pageable);
}
