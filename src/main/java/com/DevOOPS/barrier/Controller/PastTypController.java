package com.DevOOPS.barrier.Controller;

import com.DevOOPS.barrier.DTO.*;
import com.DevOOPS.barrier.Service.PastTypService;
import com.DevOOPS.barrier.Status.Message;
import com.DevOOPS.barrier.Status.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


@RestController
@Slf4j
@RequestMapping("/api/past")
public class PastTypController {
    private final double EARTH_RADIUS = 6371;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime presentDate;
    private LocalDateTime lastDate;
    private int randomIdx;
    private int count = 9;
    private double typLat;
    private double typLon;
    private double korLat = 35.9065;
    private double korLon = 131.8725;
    private double korDis = 303.68;
    private int aftertime;
    private int typrad;
    private String power;
    @Autowired
    PastTypService pastTypService;
    int Barrier_Order = 00;

    private int getRandomValue(int minIdx, int maxIdx){
        Random random = new Random();
        return random.nextInt(maxIdx - minIdx + 1) +minIdx;
    }

    public  double getDistance(double lat1, double lon1, double lat2, double lon2){
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS* c;    // Distance in km
        return d;
    }

//    @GetMapping("/pastTyp")
//    public List <PastTypEntity> getAllPastTyps(){
//        return pastTypRepository.findAll();
//    }


    @GetMapping("/information/{idx}")
    public Message getRandomTable(@PathVariable(value = "idx")int idx){
        log.info("idx: " + String.valueOf(idx));
        log.info("count: " + String.valueOf(count));
        if (idx == 0 || count == 7) {
            int minIdx = 1;
            int maxIdx = 50;
            randomIdx = getRandomValue(minIdx, maxIdx);
            TypListdto typListdto = pastTypService.getTypList(randomIdx);
            if(typListdto == null){
                Message message = new Message(StatusEnum.NOT_FOUND, "Object not found", typListdto);
                count = 0;
                return message;
            }
            presentDate = typListdto.gettypStart();
            lastDate = typListdto.gettypEnd();
            log.info(String.valueOf(presentDate));
            log.info(String.valueOf(lastDate));
//            Message message = new Message(StatusEnum.OK, "Success", typListdto);
            count = 0;
//            lastDate = lastDate.plusHours(3);
            return getTableByTypDate();
        }
        return getTableByTypDate();
    }

    public Message getTableByTypDate() {
        if(randomIdx > 38){
            count++;
            log.info(String.valueOf(count));
            return new Message(StatusEnum.NOT_FOUND, "Object not found", null);
        }
        log.info(String.valueOf(presentDate));
        LocalDateTime date =  presentDate;
        List<PastTypdto> pastTypdtos = pastTypService.getPastTyp(date);
        for(PastTypdto p : pastTypdtos) {
            log.info(p.toString());
        }
//            log.info(pastTypdtos.toString());

        if(pastTypdtos.isEmpty()){
//            Messaage message = new message(StatusEnum.OK)
            presentDate = presentDate.minusHours(3);
            LocalDateTime date1 = presentDate;
            List<PastTypdto> pastTypdtos1 = pastTypService.getPastTyp(date1);
            if(pastTypdtos1.isEmpty()){return getRandomTable(0);}
            Message message = new Message(StatusEnum.OK, "Past_Report", pastTypdtos1);
            presentDate = presentDate.plusHours(6);
//            List<PastTypdto> pastTypdtos2 = pastTypService.getPastTyp(date1);
//                if(pastTypdtos2.isEmpty()){
//
//                    return message;
//                }
//            if (presentDate == lastDate.plusHours(3)){
//                return getRandomTable(0);
//            }
//            return message;
            return message;
        }
        Message message = new Message(StatusEnum.OK, "Present_Report", pastTypdtos);
//            log.info(message.toString());
        presentDate = presentDate.plusHours(3);
        log.info(String.valueOf(presentDate));
//        return message;
        return message;
    }

//    @GetMapping("/information/table")
//    public Message getTypAllData(){
//        log.info(String.valueOf(presentDate));
//        LocalDateTime date2 = presentDate;
//        date2 = date2.minusHours(3);
//        List<TypDatadto> typDatadto = pastTypService.getTypData(date2);
//        if(typDatadto.isEmpty()){
//            date2 = date2.minusHours(3);
//            List<TypDatadto> typDatadto1 = pastTypService.getTypData(date2);
//            if(typDatadto1.isEmpty()){
//                Message message = new Message(StatusEnum.OK, "Typ_is_Not_Comming", typDatadto1);
//                return message;
//            }
//            Message message = new Message(StatusEnum.OK, "Typ_Past_All_data", typDatadto1);
//            return message;
//        }
//        Message message = new Message(StatusEnum.OK, "Typ_Present_All_data", typDatadto);
//        return message;
//    }

    @GetMapping("/information/danger")
    public Message getTypDanger(){
        int danger = 0;
        int dangerLevel =0;
        LocalDateTime date3 = presentDate;
        date3 = date3.minusHours(3);
        List<TypDangerdto> typDangerdtos = pastTypService.getTypDanger(date3);
        if(typDangerdtos.isEmpty()){
            date3 = date3.minusHours(3);
            List<TypDangerdto> typDangerdtos1 = pastTypService.getTypDanger(date3);
            for(TypDangerdto typDangerdto : typDangerdtos1){
                aftertime = typDangerdto.getAfter_time();
                log.info(String.valueOf(aftertime));
                typLat = typDangerdto.getTyp_lat();
                typLon = typDangerdto.getTyp_lon();
                typrad = typDangerdto.getTyprad();
                power = typDangerdto.getPower();
                double typkorDis = getDistance(typLat, typLon, korLat, korLon);
                log.info(String.valueOf(typkorDis));
                log.info(String.valueOf(typrad + korDis));
                if(aftertime<=36){
                    if( typkorDis <= (typrad + korDis) ){
                        danger++;
                        log.info(String.valueOf(danger));
                    }
                    else{
                        log.info(String.valueOf(danger));
                    }
                }
                log.info(typDangerdto.toString());
            }
        }
        for(TypDangerdto typDangerdto : typDangerdtos){
            aftertime = typDangerdto.getAfter_time();
            log.info(String.valueOf(aftertime));
            typLat = typDangerdto.getTyp_lat();
            typLon = typDangerdto.getTyp_lon();
            typrad = typDangerdto.getTyprad();
            power = typDangerdto.getPower();
            double typkorDis = getDistance(typLat, typLon, korLat, korLon);
            log.info(String.valueOf(typkorDis));
            log.info(String.valueOf(typrad + korDis));
            if(aftertime<=36){
                if( typkorDis <= (typrad + korDis) ){
                    danger++;
                    log.info(String.valueOf(danger));
                }
                else{
                    log.info(String.valueOf(danger));
                }
            }
            log.info(typDangerdto.toString());
        }
        if (danger == 2) {
            dangerLevel = 1;
        } else if(danger>=3){
            dangerLevel = 2;
        }
        log.info(String.valueOf(danger));
        log.info(String.valueOf(dangerLevel));
        Message message = new Message(StatusEnum.OK, "DangerLevel [ Safe : 0 ] [ Danger : 1 ] [ Superdanger : 2]", dangerLevel);
        return message;
    }

    @PostMapping("/information/iot")
    public Message postIotDanger(){
        int danger = 0;
        double dangerLevel = 0;
        LocalDateTime date4 = presentDate;
        date4 = date4.minusHours(3);
        List<IoTDangerdto> ioTDangerdtos = pastTypService.getIotDanger(date4);
        if(Barrier_Order == 10){Barrier_Order=11;}
        else if(Barrier_Order ==01) {Barrier_Order =00;}
        if(ioTDangerdtos.isEmpty()){
            date4 = date4.minusHours(3);
            List<IoTDangerdto> ioTDangerdtos1 = pastTypService.getIotDanger(date4);
            for(IoTDangerdto ioTDangerdto : ioTDangerdtos1){
                aftertime = ioTDangerdto.getAfter_time();
                log.info(String.valueOf(aftertime));
                typLat = ioTDangerdto.getTyp_lat();
                typLon = ioTDangerdto.getTyp_lon();
                typrad = ioTDangerdto.getTyprad();
                power = ioTDangerdto.getPower();
                double typkorDis = getDistance(typLat, typLon, korLat, korLon);
                log.info(String.valueOf(typkorDis));
                log.info(String.valueOf(typrad + korDis));
                if(aftertime<=36){
                    if( typkorDis <= (typrad + korDis) ){
                        danger++;
                        log.info(String.valueOf(danger));
                    }
                    else{
                        log.info(String.valueOf(danger));
                    }
                }
                log.info(ioTDangerdto.toString());
            }
        }
        for(IoTDangerdto ioTDangerdto : ioTDangerdtos){
            aftertime = ioTDangerdto.getAfter_time();
            log.info(String.valueOf(aftertime));
            typLat = ioTDangerdto.getTyp_lat();
            typLon = ioTDangerdto.getTyp_lon();
            typrad = ioTDangerdto.getTyprad();
            power = ioTDangerdto.getPower();
            double typkorDis = getDistance(typLat, typLon, korLat, korLon);
            log.info(String.valueOf(typkorDis));
            log.info(String.valueOf(typrad + korDis));
            if(aftertime<=36){
                if( typkorDis <= (typrad + korDis) ){
                    dangerLevel++;
                    log.info(String.valueOf(danger));
                }
                else{
                    log.info(String.valueOf(danger));
                }
            }
            log.info(ioTDangerdto.toString());
        }
        if(danger==0){ dangerLevel = 0; }
        else if (danger >= 3) { dangerLevel = 1; }


        if(Barrier_Order ==00){
            if(dangerLevel ==0){Barrier_Order=00;}
            else if(dangerLevel==1){Barrier_Order=10;}
        }
        else if(Barrier_Order ==11){
            if(dangerLevel ==0){Barrier_Order=01;}
            else if(dangerLevel ==1){Barrier_Order=11;}
        }

        Message message = new Message(StatusEnum.OK, "OFF 유지:00, UP:10, ON 유지:11, DOWN:01", Barrier_Order);
        return message;
    }
}
