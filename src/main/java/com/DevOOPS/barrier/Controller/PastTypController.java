package com.DevOOPS.barrier.Controller;

import com.DevOOPS.barrier.DTO.PastTypdto;
import com.DevOOPS.barrier.DTO.TypListdto;
import com.DevOOPS.barrier.Service.PastTypService;
import com.DevOOPS.barrier.Status.Message;
import com.DevOOPS.barrier.Status.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@Slf4j
@RequestMapping("/api/past")
public class PastTypController {
    @Autowired
    private PastTypService pastTypService;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime PresentDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime pastTypend;

    private int getRandomValue(int minIdx, int maxIdx) { //랜덤값 출력 함수
        Random random = new Random();
        return random.nextInt(maxIdx - minIdx + 1) + minIdx;
    }

    public PastTypController(PastTypService pastTypService) {
        this.pastTypService = pastTypService;
    }

//    public Message postPastTypAPI(){
//        List<PastTypdto> PastTypResultList = new ArrayList<>();
//        PastTypResultList = pastTypService.getPastTyp(PresentDate);
//        Message message = new Message(StatusEnum.OK, "성공", PastTypResultList);
//        return message;
//    }

//    @GetMapping("/list")
//    public TypListdto getTypList(@RequestParam("idx") TypListdto typListdto) {
//        return PastTypService.getTypList(typlistdto);
//    }



//    @GetMapping("/table/{idx}") //idx값 입력해서 리스트 확인
//    public ResponseEntity<TypListdto> getTableById(@PathVariable("idx") Integer idx) {
//        TypListdto typListdto = PastTypService.getTypList(idx);
//        if (typListdto == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        pastTypDate = typListdto.gettypStart();
//        log.info(String.valueOf(pastTypDate));
//        return new ResponseEntity<>(typListdto, HttpStatus.OK);
//
//    }

    @GetMapping("/table") //랜덤값 입력해서 리스트 확인
    public Message getRandomTable() {
        int minIdx = 1;
        int maxIdx = 50;
        int randomIdx = getRandomValue(minIdx, maxIdx);
        TypListdto typListdto = PastTypService.getTypList(randomIdx);
        if (typListdto == null) {
            Message message = new Message(StatusEnum.NOT_FOUND, "Object not found",typListdto);
            return message;
        }
        PresentDate = typListdto.gettypStart();
        pastTypend = typListdto.gettypEnd();
        log.info(String.valueOf(PresentDate));
        Message message = new Message(StatusEnum.OK, "Success", typListdto);
        return message;
    }

    //    @GetMapping("TyphoonInfo")
//    public Message postTyphoonInfo(@RequestParam("date")String date) throws TyphoonSearchException, TyphoonInfoNullException {
//        List<TyphoonInfoDTO> typhoonInfoDTOList = new ArrayList<TyphoonInfoDTO>();
//        typhoonInfoDTOList = adminService.PostTyphoonInfo(date);
//        Message message = new Message(StatusEnum.OK, "Successful post TyphoonInfo.", typhoonInfoDTOList);
//
//        return message;
//
//    }
    @GetMapping("/pastTyp")
    public List<Object> getTableByTypDate(LocalDateTime date) {
        List<Object> resultList = new ArrayList<>();
        log.info(String.valueOf(PresentDate));
        date = PresentDate;
        List<PastTypdto> pastTypdtos = PastTypService.getPastTyp(date);
        Message message = new Message(StatusEnum.OK, "Success", pastTypdtos);
        resultList.add(String.valueOf(PresentDate));
        resultList.add(message);
        PresentDate = PresentDate.plusHours(3);
        return resultList;
    }

}
