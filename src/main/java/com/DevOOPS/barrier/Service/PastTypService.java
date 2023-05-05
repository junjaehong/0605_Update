package com.DevOOPS.barrier.Service;


import com.DevOOPS.barrier.DTO.PastTypdto;
import com.DevOOPS.barrier.DTO.TypListdto;
import com.DevOOPS.barrier.Mapper.TypListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service //Bean에 등록하는 annotation. 기본으로 싱글톤으로 등록한다 (유일하게 하나만 등록해서 공유한다)
@Slf4j
public class PastTypService {

//    @Autowired
//    TypListMapper typlistmapper;
//    TypListdto listdto;
//    public void selectTyplist(TypListdto listdto){ typlistmapper.selectTyplist(listdto);}

    @Autowired
    private static TypListMapper typhoonMapper;

    public PastTypService(TypListMapper typhoonMapper){
        this.typhoonMapper = typhoonMapper;
    }
    public static TypListdto getTypList(Integer idx) {
        return typhoonMapper.getTypList(idx);
    }

    public static List<PastTypdto> getPastTyp(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime typ_date) {
        List<PastTypdto> pastTypdtos = new ArrayList<>();
        pastTypdtos = (List<PastTypdto>) typhoonMapper.getPastTyps(typ_date);

        return pastTypdtos;
    }

//    public static List<TypListdto> getTypList(Integer idx) {
//        return typhoonMapper.getTypList(idx);
//    }

}
