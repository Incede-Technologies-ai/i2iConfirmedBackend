package com.I2I.I2IBaceknd.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.I2I.I2IBaceknd.Entitiy.PaymentMaster;
import com.I2I.I2IBaceknd.Service.PaymentMasterService;

@RestController
@RequestMapping({"api/v0/PaymentMaster", "api/v1/PaymentMaster"})
public class PaymentMasterController 
{
//	({"api/v0/get_master_stage_by_id", "api/v1/get_master_stage_by_id"})
//    private List<Map<String,Object>>get_master_stages_by_id(@RequestParam Integer status_id){
//        return masterService.get_master_stages_by_id(status_id);
//    }
	@Autowired
    private PaymentMasterService mainService;

    @PostMapping("/add")
    public PaymentMaster addMain(@RequestBody PaymentMaster mainEntity) {
    
        return mainService.saveMain(mainEntity);
    }

    @GetMapping("/all")
    public List<PaymentMaster> getAll() {
        return mainService.getAllMain();
    }
    @PutMapping("/update/{id}")
    public PaymentMaster updateMain(@PathVariable Long id, @RequestBody PaymentMaster updatedData) {
        return mainService.updateMain(id, updatedData);
    }
    
    @GetMapping("/findByMainframe/{mainframe}")
    public List<PaymentMaster> getByMainframe(@PathVariable String mainframe) {
        return mainService.getByMainframe(mainframe);
    }
}
