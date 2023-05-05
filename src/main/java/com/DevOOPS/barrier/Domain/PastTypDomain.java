package com.DevOOPS.barrier.Domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class PastTypDomain {
    private long id;
    private LocalDateTime typDate;      //발표 날짜
    private int afterTime; //예측 시간
    private String typName; //태풍명
    private double typLat;  //태풍 위도
    private double typLon;  //태풍 경도
    private int typPs;      //태풍 중심기압
    private int mperSec;    //태풍 이동속도 m/s
    private int kmperHour;  //태풍 이동속도 km/h
    private int typrad;     //강풍 반경
    private String power;   //태풍 강도
    private String size;    //태풍 크기
    private String typDir;  //태풍 진행방향
    private int typSp;      //태풍 속도
    private int radPr;   //70% 확률 반경

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime gettypDate(){ return typDate; }
    public void settypDate(LocalDateTime typDate){
        this.typDate=typDate;
    }

    public int getafterTime(){
        return afterTime;
    }
    public void setafterTime(int after_time){
        this.afterTime=after_time;
    }

    public String gettypName(){
        return typName;
    }
    public void settypName(String typName){
        this.typName = typName;
    }

    public double gettypLat(){
        return typLat;
    }
    public void settypLat(double typLat){
        this.typLat=typLat;
    }

    public double gettypLon(){
        return typLon;
    }
    public void settypLon(double typLon){
        this.typLon=typLon;
    }

    public int gettypPs(){
        return typPs;
    }
    public void settypPs(int typPs){
        this.typPs=typPs;
    }

    public int getmperSec(){
        return mperSec;
    }
    public void setmperSec(int mperSec){
        this.mperSec=mperSec;
    }

    public int getkmperHour(){
        return kmperHour;
    }
    public void setkmperHour(int kmperHour){
        this.kmperHour=kmperHour;
    }

    public int gettyprad(){
        return typrad;
    }
    public void settyprad(int typrad){
        this.typrad=typrad;
    }

    public String getpower(){
        return power;
    }
    public void setpower(String power){
        this.power=power;
    }

    public String getsize(){
        return size;
    }
    public void setsize(String size){
        this.size=size;
    }

    public String gettypDir(){
        return typDir;
    }
    public void settypDir(String typDir){
        this.typDir=typDir;
    }

    public int gettypSp(){
        return typSp;
    }
    public void settypSp(int typSp){
        this.typSp=typSp;
    }

    public int getradPr(){
        return radPr;
    }
    public void setradPr(int radPr ){
        this.radPr=radPr;
    }
}
