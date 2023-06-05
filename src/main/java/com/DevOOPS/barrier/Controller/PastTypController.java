package com.DevOOPS.barrier.Controller;

import com.DevOOPS.barrier.DTO.*;
import com.DevOOPS.barrier.Service.PastTypService;
import com.DevOOPS.barrier.Status.Message;
import com.DevOOPS.barrier.Status.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    String dangerLevel = "";

    private String enterAdress;
    private WebClient client = WebClient.create(enterAdress);



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
                presentDate = presentDate.now();
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
            presentDate = presentDate.now();
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
//        try {
//            int danger = 0;
//            int dcount = 0;
//            int dangerLevel = 0;
//            LocalDateTime date3 = presentDate;
//            List<TypDangerdto> typDangerdtos = pastTypService.getTypDanger(date3);
//            if (typDangerdtos.isEmpty()) {
//                date3 = date3.minusHours(3);
//                List<TypDangerdto> typDangerdtos1 = pastTypService.getTypDanger(date3);
//                for (TypDangerdto typDangerdto : typDangerdtos1) {
//                    aftertime = typDangerdto.getAfter_time();
//                    log.info(String.valueOf(aftertime));
//                    typLat = typDangerdto.getTyp_lat();
//                    typLon = typDangerdto.getTyp_lon();
//                    typrad = typDangerdto.getTyprad();
//                    power = typDangerdto.getPower();
////                double typkorDis = getDistance(typLat, typLon, korLat, korLon);
////                log.info(String.valueOf(typkorDis));
////                log.info(String.valueOf(typrad + korDis));
////                if(aftertime<=36){
////                    if( typkorDis <= (typrad + korDis) ){
////                        danger++;
////                        log.info(String.valueOf(danger));
////                    }
////                    else{
////                        log.info(String.valueOf(danger));
////                    }
////                }
//                    log.info(String.valueOf(power));
//                    if (aftertime <= 36) {
//                        switch (power) {
//                            case "약":
//                                danger = danger + 1;
//                                break;
//                            case "중":
//                                danger = danger + 2;
//                                break;
//                            case "강":
//                                danger = danger + 3;
//                                break;
//                            case "매우 강":
//                                danger = danger + 4;
//                                break;
//                            case "초강력":
//                                danger = danger + 5;
//                                break;
//                        }
//                    }
//                    log.info(String.valueOf(danger));
//                    log.info(typDangerdto.toString());
//                }
//            }
//            for (TypDangerdto typDangerdto : typDangerdtos) {
//                aftertime = typDangerdto.getAfter_time();
//                log.info(String.valueOf(aftertime));
//                typLat = typDangerdto.getTyp_lat();
//                typLon = typDangerdto.getTyp_lon();
//                typrad = typDangerdto.getTyprad();
//                power = typDangerdto.getPower();
//                double typkorDis = getDistance(typLat, typLon, korLat, korLon);
////            log.info(String.valueOf(typkorDis));
////            log.info(String.valueOf(typrad + korDis));
////            if(aftertime<=36){
////                if( typkorDis <= (typrad + korDis) ){
////                    danger++;
////                    log.info(String.valueOf(danger));
////                }
////                else{
////                    log.info(String.valueOf(danger));
////                }
////            }
//
//                log.info(String.valueOf(danger));
//                log.info(typDangerdto.toString());
//            }
////        if (danger == 2) {
////            dangerLevel = 1;
////        } else if(danger>=3){
////            dangerLevel = 2;
////        }
////        log.info(String.valueOf(danger));
//            if (danger <= 4) {
//                dangerLevel = 0;
//            } else if (danger <= 8) {
//                dangerLevel = 1;
//            } else {
//                dangerLevel = 2;
//            }
//            log.info(String.valueOf(dangerLevel));
//            Message message = new Message(StatusEnum.OK, "DangerLevel [ Safe : 0 ] [ Danger : 1 ] [ Superdanger : 2]", dangerLevel);
//            return message;
//        }catch (Exception e){
//            return new Message(StatusEnum.OK, "DangerLevel [ Safe : 0 ] [ Danger : 1 ] [ Superdanger : 2]", 0);
//        }
        int danger = 0;
        int checkPoint = 0;
        log.info("이모티콘: "+presentDate.toString());
        presentDate=presentDate.minusHours(3);
        log.info("이모티콘: "+presentDate.toString());
        List<PastTypdto> pastTypdtos = pastTypService.getPastTyp(presentDate);
        if(pastTypdtos.isEmpty())
        {
            presentDate=presentDate.minusHours(3);
            checkPoint = 1;
        }
        pastTypdtos = new ArrayList<>();
        pastTypdtos.addAll(pastTypService.getPastTyp(presentDate));

        for(PastTypdto p:pastTypdtos)
        {
            if(p.getAfter_time()<=24)
            {
                try {
                    p.getPower();
                }catch (Exception e)
                {
                    continue;
                }
                if(p.getPower().equals("강")||p.getPower().equals("매우강")||p.getPower().equals("매우 강")||p.getPower().equals("초강력"))
                {
                    danger=2;
                }
                if(p.getPower().equals("약")&&danger!=2||p.getPower().equals("중")&&danger!=2)
                {
                    danger=1;
                }
                if(p.getPower().isEmpty()&&danger==0){
                    danger=0;
                }
                log.info("파워 : "+ p + " / 위험도 : " + danger);
            }
        }
        if(checkPoint==1)
        {
            presentDate=presentDate.plusHours(3);
        }
        presentDate=presentDate.plusHours(3);
        return new Message(StatusEnum.OK, "위험도 : 0 = happy / 1 = sad / 2 = angry " , danger);
    }

    @PostMapping("/information/iot")
    public Message getIotDanger(){
//        int danger = 0;
//        LocalDateTime date4 = presentDate;
//        date4 = date4.minusHours(3);
//        List<IoTDangerdto> ioTDangerdtos = pastTypService.getIotDanger(date4);
//        if(Barrier_Order == 10){Barrier_Order=11;}
//        else if(Barrier_Order ==01) {Barrier_Order =00;}
//        if(ioTDangerdtos.isEmpty()){
//            date4 = date4.minusHours(3);
//            List<IoTDangerdto> ioTDangerdtos1 = pastTypService.getIotDanger(date4);
//            for(IoTDangerdto ioTDangerdto : ioTDangerdtos1){
//                aftertime = ioTDangerdto.getAfter_time();
//                log.info(String.valueOf(aftertime));
//                typLat = ioTDangerdto.getTyp_lat();
//                typLon = ioTDangerdto.getTyp_lon();
//                typrad = ioTDangerdto.getTyprad();
//                power = ioTDangerdto.getPower();
//                double typkorDis = getDistance(typLat, typLon, korLat, korLon);
//                log.info(String.valueOf(typkorDis));
//                log.info(String.valueOf(typrad + korDis));
//                if(aftertime<=36){
//                    if( typkorDis <= (typrad + korDis) ){
//                        danger++;
//                        log.info(String.valueOf(danger));
//                    }
//                    else{
//                        log.info(String.valueOf(danger));
//                    }
//                }
//                log.info(ioTDangerdto.toString());
//            }
//        }
//        for(IoTDangerdto ioTDangerdto : ioTDangerdtos){
//            aftertime = ioTDangerdto.getAfter_time();
//            log.info(String.valueOf(aftertime));
//            typLat = ioTDangerdto.getTyp_lat();
//            typLon = ioTDangerdto.getTyp_lon();
//            typrad = ioTDangerdto.getTyprad();
//            power = ioTDangerdto.getPower();
//            double typkorDis = getDistance(typLat, typLon, korLat, korLon);
//            log.info(String.valueOf(typkorDis));
//            log.info(String.valueOf(typrad + korDis));
//            if(aftertime<=36){
//                if( typkorDis <= (typrad + korDis) ){
//                    danger++;
//                    log.info(String.valueOf(danger));
//                }
//                else{
//                    log.info(String.valueOf(danger));
//                }
//            }
//            log.info(ioTDangerdto.toString());
//        }
//        if(danger==0){ dangerLevel = "wallAlertDeactivation"; }
//        else if (danger >= 1) { dangerLevel ="wallAlertActivation"; }
//
//
////        if(Barrier_Order ==00){
////            if(dangerLevel ==0){Barrier_Order=00;}
////            else if(dangerLevel==1){Barrier_Order=10;}
////        }
////        else if(Barrier_Order ==11){
////            if(dangerLevel ==0){Barrier_Order=01;}
////            else if(dangerLevel ==1){Barrier_Order=11;}
////        }
//
//        Message message = new Message(StatusEnum.OK, "OFF 유지:00, UP:10, ON 유지:11, DOWN:01", dangerLevel);
//        return message;
        String danger = "wallAlertDeactivation";
        int point = 0;
        log.info("이모티콘: "+presentDate.toString());
        presentDate=presentDate.minusHours(3);
        log.info("이모티콘: "+presentDate.toString());
        List<PastTypdto> pastTypdtos = pastTypService.getPastTyp(presentDate);
        if(pastTypdtos.isEmpty())
        {
            presentDate=presentDate.minusHours(3);
            point = 1;
        }
        pastTypdtos = new ArrayList<>();
        pastTypdtos.addAll(pastTypService.getPastTyp(presentDate));

        for(PastTypdto p:pastTypdtos)
        {
            if(p.getAfter_time()<=24)
            {
                try {
                    p.getPower();
                }catch (Exception e)
                {
                    continue;
                }
                if(p.getPower().equals("강")||p.getPower().equals("매우 강")||p.getPower().equals("초강력"))
                {
                    danger="wallAlertActivation";
                }
                if(p.getPower().equals("약")&&danger!="wallAlertActivation"||p.getPower().equals("중")&&danger!="wallAlertActivation" || p.getPower().isEmpty()&&danger!="wallAlertActivation")
                {
                    danger="wallAlertDeactivation";
                }
                log.info("파워 : "+p + "차수벽 행동 지시 : " + danger);
            }
        }

        Message message = new Message(StatusEnum.OK, "", danger);

        Mono<String> response = client.method(HttpMethod.POST).uri("http://192.168.200.103:9998/data").contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(message)).
                retrieve().bodyToMono(String.class);
        String responseBody = response.block();
        log.info(responseBody);


        if(point==1)
        {
            presentDate=presentDate.plusHours(3);
        }
        presentDate=presentDate.plusHours(3);
        return message;
    }
}
