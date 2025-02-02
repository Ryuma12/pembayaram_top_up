package com.pembayaran.controller;

import com.pembayaran.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MasterController {

    @Autowired
    MasterService masterService;

    @GetMapping("/banner")
    public Map getMasterBanner(){
        return masterService.getMasterBanner();
    }

    @GetMapping("/service")
    public Map getMasterService(){
        return masterService.getMasterService();
    }
}
