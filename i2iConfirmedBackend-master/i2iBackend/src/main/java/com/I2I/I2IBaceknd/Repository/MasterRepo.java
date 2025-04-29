package com.I2I.I2IBaceknd.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.I2I.I2IBaceknd.Dao.dao;

@Repository
public class MasterRepo extends dao {

    public List<Map<String, Object>> getStatusById(Integer id) {
        String sql = "SELECT status_code, status_name, created_at, updated_at FROM status_master WHERE status_id = :id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return this.executeQuery(sql, param);
    }

    public List<Map<String, Object>> getAllStatuses() {
        String sql = "SELECT status_id, status_code, status_name, created_at, updated_at FROM status_master";
        return this.executeQuery(sql, new HashMap<>());
    }

  

    
}
