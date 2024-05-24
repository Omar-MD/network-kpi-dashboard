package com.tools.dashboard.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tools.dashboard.dto.Kpi;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Integer>{
	List<Kpi> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
	List<Kpi> findByLatencyBetween(double low, double high);
	List<Kpi> findByThroughputBetween(double low, double high);
	List<Kpi> findByErrorRateBetween(double low, double high);
}