package com.DevOOPS.barrier.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PastTypdto {
    private LocalDateTime typDate;      //발표 날짜
    private int afterTime; //예측 시간
    private String typName; //태풍명
    private String power;   //태풍 강도
    private String typSize;    //태풍 크기
}