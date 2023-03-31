package com.DevOOPS.barrier.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;


@Entity
@Table(name = "past_typ")
public class PastTypEntity {
    private long id;
    private LocalDateTime date;      //발표 날짜
    private int after_time; //예측 시간
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

    public PastTypEntity() {

    }

    public PastTypEntity(long id, LocalDateTime date, int after_time, String typName,
                         double typLat, double typLon, int typPs, int mperSec,
                         int kmperHour, int typrad, String power, String size,
                         String typDir, int typSp, int radPr) {
        this.id = id;
        this.date = date;
        this.after_time=after_time;
        this.typName=typName;
        this.typLat=typLat;
        this.typLon=typLon;
        this.typPs=typPs;
        this.mperSec=mperSec;
        this.kmperHour=kmperHour;
        this.typrad=typrad;
        this.power=power;
        this.size=size;
        this.typDir=typDir;
        this.typSp=typSp;
        this.radPr=radPr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name="date", nullable = false)
    public LocalDateTime getdate(){ return date; }
    public void setdate(LocalDateTime date){
        this.date=date;
    }

    @Column(name="after_time", nullable = false)
    public int getafter_time(){
        return after_time;
    }
    public void setafter_time(int after_time){
        this.after_time=after_time;
    }

    @Column(name="typName", nullable = false)
    public String gettypName(){
        return typName;
    }
    public void settypName(String typName){
        this.typName = typName;
    }

    @Column(name="TypLat", nullable = false)
    public double gettypLat(){
        return typLat;
    }
    public void settypLat(double typLat){
        this.typLat=typLat;
    }

    @Column(name="TypLon", nullable = false)
    public double gettypLon(){
        return typLon;
    }
    public void settypLon(double typLon){
        this.typLon=typLon;
    }

    @Column(name="TypPs", nullable = true)
    public int gettypPs(){
        return typPs;
    }
    public void settypPs(int typPs){
        this.typPs=typPs;
    }

    @Column(name="mperSec", nullable = true)
    public int getmperSec(){
        return mperSec;
    }
    public void setmperSec(int mperSec){
        this.mperSec=mperSec;
    }

    @Column(name="kmperHour", nullable = true)
    public int getkmperHour(){
        return kmperHour;
    }
    public void setkmperHour(int kmperHour){
        this.kmperHour=kmperHour;
    }

    @Column(name="typrad", nullable = true)
    public int gettyprad(){
        return typrad;
    }
    public void settyprad(int typrad){
        this.typrad=typrad;
    }

    @Column(name="power", nullable = true)
    public String getpower(){
        return power;
    }
    public void setpower(String power){
        this.power=power;
    }

    @Column(name="size", nullable = true)
    public String getsize(){
        return size;
    }
    public void setsize(String size){
        this.size=size;
    }

    @Column(name="typDir", nullable = true)
    public String gettypDir(){
        return typDir;
    }
    public void settypDir(String typDir){
        this.typDir=typDir;
    }

    @Column(name="typSp", nullable = true)
    public int gettypSp(){
        return typSp;
    }
    public void settypSp(int typSp){
        this.typSp=typSp;
    }

    @Column(name="radPr", nullable = true)
    public int getradPr(){
        return radPr;
    }
    public void setradPr(int radPr ){
        this.radPr=radPr;
    }
}
