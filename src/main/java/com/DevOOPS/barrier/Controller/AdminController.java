package com.DevOOPS.barrier.Controller;
import com.DevOOPS.barrier.DTO.ReportAPIdto;
import com.DevOOPS.barrier.DTO.dto;
import com.DevOOPS.barrier.Service.AdminService;
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

@Controller
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;
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

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public String postReportAPI() {
        String tmTo = "20221231";
        String tmFrom = "20230103";
        adminService.load_save(tmTo, tmFrom);
        return "api"; //Status http 통신 코드 받아서 "안내문" 출력.

    }
}
