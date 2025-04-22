package com.I2I.I2IBaceknd.Service;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.I2I.I2IBaceknd.Repository.Eligiblityinq_Repo;


@Service
public class Eligiblityinq_Service {

    
    @Autowired
    private Eligiblityinq_Repo eligiblityinq_Repo;


    public Map<String, Object> save_inqury(JSONObject obj,MultipartFile eds1, MultipartFile eds2, MultipartFile eds3, MultipartFile eds4,MultipartFile paySLip) throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("id", obj.get("inquiryId"));
        param.put("first_name", obj.get("firstName"));
        param.put("middle_name", obj.get("middleName"));
        param.put("last_name", obj.get("lastName"));
        param.put("phn_num", obj.get("phone_Number"));
        param.put("email", obj.get("email"));
        param.put("residence_start_date", obj.get("residenceStartDate"));
        param.put("application_date", obj.get("application_date"));
        param.put("VisaCategory", obj.get("visaCategory"));
        param.put("grossIncome2022", obj.get("grossIncome2022"));
        param.put("grossIncome2023", obj.get("grossIncome2023"));
        param.put("grossIncome2024", obj.get("grossIncome2024"));
        param.put("grossIncome2025", obj.get("grossIncome2025"));
        param.put("family_members", obj.get("familyMembers"));
        param.put("eds2022", obj.get("eds2022"));
        param.put("eds2023", obj.get("eds2023"));
        param.put("eds2024", obj.get("eds2024"));
        param.put("eds2025", obj.get("eds2025"));
        param.put("latestPayslip", obj.get("latestPayslip"));
        param.put("family_numbers", obj.get("family_numbers"));
        param.put("apply_for_spouse", obj.get("apply_for_spouse"));
        param.put("dependent_children_count", obj.get("dpdt_chldn_cnt"));
        param.put("additional_details", obj.get("application_details"));
        param.put("status", obj.get("status"));
        if (eds1 != null && !eds1.isEmpty()) {
            param.put("eds1", eds1.getBytes());
        }
        if (eds2 != null && !eds2.isEmpty()) {
            param.put("eds2", eds2.getBytes());
        } 
        if (eds3 != null && !eds3.isEmpty()) {
            param.put("eds3", eds3.getBytes());
        }
         if (eds4 != null && !eds4.isEmpty()) {
            param.put("eds4", eds4.getBytes());
        }
        if (paySLip!= null && !paySLip.isEmpty() ){
            param.put("latest_payslip_bytea", paySLip.getBytes());
        }
        return (Map<String, Object>) eligiblityinq_Repo.save_inqury(param);
    }


    public List<Map<String, Object>> getInquiryList() {
       return eligiblityinq_Repo.getInquiry();
    }


    public List<Map<String, Object>> getDocuments(Integer id) {
        return eligiblityinq_Repo.getDocumentsById(id);
    }


    



    
}



