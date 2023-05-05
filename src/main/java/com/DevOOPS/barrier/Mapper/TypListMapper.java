package com.DevOOPS.barrier.Mapper;

import com.DevOOPS.barrier.DTO.PastTypdto;
import com.DevOOPS.barrier.DTO.TypListdto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TypListMapper {

    TypListdto getTypList(int idx);
    List<PastTypdto> getPastTyps(@Param("typ_date") LocalDateTime typ_date);




//    List getTypList(Integer idx);
}
