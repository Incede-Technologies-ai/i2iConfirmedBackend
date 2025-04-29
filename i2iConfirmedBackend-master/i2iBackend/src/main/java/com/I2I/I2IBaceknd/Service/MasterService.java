package com.I2I.I2IBaceknd.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2I.I2IBaceknd.Repository.MasterRepo;

@Service
public class MasterService {

    @Autowired
    MasterRepo masterRepo;

    public List<Map<String, Object>> get_master_stages() {
       return masterRepo.getAllStatuses();
       
    }

    public List<Map<String, Object>> get_master_stages_by_id(Integer status_id) {
        return masterRepo.getStatusById(status_id);
    }
    
}
