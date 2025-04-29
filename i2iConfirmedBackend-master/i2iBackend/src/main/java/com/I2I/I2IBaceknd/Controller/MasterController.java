package com.I2I.I2IBaceknd.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.I2I.I2IBaceknd.Service.MasterService;

@RestController
public class MasterController {


    @Autowired
    MasterService masterService;
    
    @GetMapping({"api/v0/get_master_stages", "api/v1/get_master_stages"})
    private List<Map<String,Object>>get_master_stages(){
        return masterService.get_master_stages();
    }

    @GetMapping({"api/v0/get_master_stage_by_id", "api/v1/get_master_stage_by_id"})
    private List<Map<String,Object>>get_master_stages_by_id(@RequestParam Integer status_id){
        return masterService.get_master_stages_by_id(status_id);
    }

}
