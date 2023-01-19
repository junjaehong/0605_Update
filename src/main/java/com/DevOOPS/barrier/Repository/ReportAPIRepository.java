package com.DevOOPS.barrier.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.DevOOPS.barrier.DTO.ReportAPIdto;
public interface ReportAPIRepository extends JpaRepository <ReportAPIdto, Integer> {
}
