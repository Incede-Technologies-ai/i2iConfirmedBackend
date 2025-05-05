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


    public Map<String, Object> save_inqury(JSONObject obj,MultipartFile eds1, MultipartFile eds2, MultipartFile eds3,MultipartFile paySLip) throws IOException {
        Map<String, Object> param = new HashMap<>();
        String elgbl_str = get_next_elgblity_no();
        System.out.println( obj.get("apply_for_spouse")+"helloooooo");
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
        param.put("eds_2022", "minio/eligibility/eds2022_" + elgbl_str + ".pdf");
        param.put("eds_2023", "minio/eligibility/eds2023_" + elgbl_str + ".pdf");
        param.put("eds_2024", "minio/eligibility/eds2024_" + elgbl_str + ".pdf");
        param.put("eds_2025", "minio/eligibility/eds2025_" + elgbl_str + ".pdf");
        param.put("latest_payslip", "minio/eligibility/latestPayslip_" + elgbl_str + ".pdf");
        
        param.put("family_numbers", obj.get("family_numbers"));
        boolean applyForSpouse = (boolean) obj.get("apply_for_spouse");
        param.put("apply_for_spouse",applyForSpouse);
        param.put("dependent_children_count", obj.get("dependentChildren"));
        param.put("additional_details", obj.get("message"));
        param.put("status", obj.get("status") != null ? obj.get("status") : "PENDING");
        param.put("elgbl_str", elgbl_str);
        if (eds1 != null && !eds1.isEmpty()) {
            param.put("eds1", eds1.getBytes());
        }
        if (eds2 != null && !eds2.isEmpty()) {
            param.put("eds2", eds2.getBytes());
        } 
        if (eds3 != null && !eds3.isEmpty()) {
            param.put("eds3", eds3.getBytes());
        }
        //  if (eds4 != null && !eds4.isEmpty()) {
        //     param.put("eds4", eds4.getBytes());
        // }
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

    public String get_next_elgblity_no() {
        String lastEligibilityNo = eligiblityinq_Repo.get_next_elgblity_no();
    
        if (lastEligibilityNo == null || lastEligibilityNo.isEmpty()) {
            return "AA001";
        }
    
        // Split prefix and number part
        String prefix = lastEligibilityNo.substring(0, 2); // e.g., "AA"
        String numberPart = lastEligibilityNo.substring(2); // e.g., "001"
    
        int number = Integer.parseInt(numberPart);
    
        if (number < 999) {
            // Increment the number part
            number++;
            return prefix + String.format("%03d", number); // e.g., "AA002"
        } else {
            // When number is 999, reset number to 000 and increment prefix
            char firstChar = prefix.charAt(0);
            char secondChar = prefix.charAt(1);
    
            if (secondChar == 'Z') {
                // Move first char ahead, reset second to A
                firstChar++;
                secondChar = 'A';
            } else {
                // Just move second char ahead
                secondChar++;
            }
    
            String newPrefix = "" + firstChar + secondChar;
    
            return newPrefix + "000";
        }
    }


    public List<Map<String, Object>> updateEligibilityStatus(JSONObject obj) {
        Map<String, Object> param2 = new HashMap<>();
        param2.put("id", obj.get("elgiblty_id"));
        param2.put("status", obj.get("status"));
        param2.put("eligibility_no", obj.get("eligibility_no"));
        param2.put("status_msg", obj.get("status_msg")); // include if needed in return
    
        return eligiblityinq_Repo.updateEligibilityStatus(param2);
    }
    
     

    



    
}



