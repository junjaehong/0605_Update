package com.DevOOPS.barrier.Controller;
import com.DevOOPS.barrier.DTO.ReportAPIdto;
import com.DevOOPS.barrier.DTO.dto;
import com.DevOOPS.barrier.Service.AdminService;
import com.DevOOPS.barrier.Service.TyphoonService;
import com.DevOOPS.barrier.Status.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import org.apache.ibatis.annotations.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@Slf4j
@RequestMapping(value = "/api")

public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired  //자동으로 생성
    TyphoonService typhoonService;
//
//    @RequestMapping(value = "/signup", method = RequestMethod.GET)
//    public String getSignUp() {
//        return "signup";
//    }
//
//    @RequestMapping(value = "/signup", method = RequestMethod.POST)
//    public void postSignUp(dto dt) {
//        adminService.createAdmin(dt);
//
//    }
//
//    @RequestMapping(value = "/api", method = RequestMethod.GET)
//    public String getReportAPI() { return "ReportAPI"; }

    @GetMapping("/load")
    public void postReportAPI() {
        String tmTo = "20230223";
        String tmFrom = "20230224";
        int HttpStatus;

        tmTo = String.valueOf(adminService.ServerTime() - 3);
        tmFrom = String.valueOf(adminService.ServerTime());

        log.info(tmTo + ", " + tmFrom);

        HttpStatus = adminService.load_save(tmTo, tmFrom);
//        adminService.TyphoonAnalyzed();
        log.info(String.valueOf(HttpStatus));

        String StatusMessage = StatusEnum.of(HttpStatus).getCode();
        log.info(StatusMessage);
    }

    @GetMapping("/typhoon")
    public void typhoonAPI() {
        String typtmTo = "20230223";
        String typFrom = "20230224";
        int HttpStatus;

        typtmTo = String.valueOf(typhoonService.ServerTime() - 3);
        typFrom = String.valueOf(typhoonService.ServerTime());

        log.info(typtmTo + ", " + typFrom);

        HttpStatus = typhoonService.load_Typhoon(typtmTo, typFrom);
//        adminService.TyphoonAnalyzed();
        log.info(String.valueOf(HttpStatus));

//        String StatusMessage = StatusEnum.of(HttpStatus).getCode();
//        log.info(StatusMessage);
    }
    //< 문제 -> xml형식으로 출력해서 발생한 문제 JSON방식으로 출력하면 해결되는 문제임,

}
