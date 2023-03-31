package com.DevOOPS.barrier.Repository;


import com.DevOOPS.barrier.Entity.PastTypEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastTypRepository extends JpaRepository<PastTypEntity, Long> {

   List<PastTypEntity> findByTypName(String typName);
}
