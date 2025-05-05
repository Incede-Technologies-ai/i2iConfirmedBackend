package com.I2I.I2IBaceknd.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.I2I.I2IBaceknd.Dao.dao;

@Repository
public class Eligiblityinq_Repo extends dao{




    public Map<String, Object> save_inqury(Map<String, Object> param) {
        String sql = "";
        if (param.get("id") == null) {
            System.out.println("Creating new inquiry...");
            sql += "INSERT INTO eligibility_enquiry ( ";
            sql += "    first_name, middle_name, last_name, phone_number, email, residence_start_date, visa_category, ";
            sql += "    gross_income_2022, gross_income_2023, gross_income_2024, gross_income_2025, ";
            sql += "    eds_2022, eds_2023, eds_2024, eds_2025, latest_payslip, latest_payslip_bytea, ";
            sql += "    family_members, apply_for_spouse, dependent_children_count, application_date, additional_details, ";
            sql += "    status,eligibility_no, eds_1, eds_2, eds_3";
            sql += "    ) VALUES ( ";
            sql += "    :first_name, :middle_name, :last_name, :phn_num, :email, :residence_start_date, :VisaCategory, ";
            sql += "    :grossIncome2022, :grossIncome2023, :grossIncome2024, :grossIncome2025, ";
            sql += "    :eds_2022, :eds_2023, :eds_2024, :eds_2025, :latest_payslip, :latest_payslip_bytea, ";
            sql += "    :family_members, :apply_for_spouse, :dependent_children_count, :application_date, :additional_details, ";
            sql += "    :status,:elgbl_str, :eds1, :eds2, :eds3";
            sql += "    );";
        } else {
            System.out.println("Updating existing inquiry...");
            sql += "UPDATE eligibility_enquiry ";
            sql += "SET first_name = :first_name, ";
            sql += "    middle_name = :middle_name, ";
            sql += "    last_name = :last_name, ";
            sql += "    phone_number = :phn_num, ";
            sql += "    email = :email, ";
            sql += "    residence_start_date = :residence_start_date, ";
            sql += "    visa_category = :VisaCategory, ";
            sql += "    gross_income_2022 = :grossIncome2022, ";
            sql += "    gross_income_2023 = :grossIncome2023, ";
            sql += "    gross_income_2024 = :grossIncome2024, ";
            sql += "    gross_income_2025 = :grossIncome2025, ";
            sql += "    eds_2022 = :eds2022, ";
            sql += "    eds_2023 = :eds2023, ";
            sql += "    eds_2024 = :eds2024, ";
            sql += "    eds_2025 = :eds2025, ";
            sql += "    latest_payslip = :latestPayslip, ";
            sql += "    latest_payslip_bytea = :latest_payslip_bytea, ";
            sql += "    family_members = :family_members, ";
            sql += "    apply_for_spouse = :apply_for_spouse, ";
            sql += "    dependent_children_count = :dependent_children_count, ";
            sql += "    application_date = :application_date, ";
            sql += "    additional_details = :additional_details, ";
            sql += "    status = :status ";
            sql += "    eligibility_no:elgbl_str ";
            sql += "WHERE id = :id;";
        }
    
        Long id = this.Save(sql, param, "id");
        Map<String, Object> inputParam = new HashMap<>();
        if (param.get("id") != null) {
            inputParam.put("inquiryId", Long.parseLong(param.get("id").toString()));
            inputParam.put("message", "Updated Successfully");
            inputParam.put("eligibility No", param.get("elgbl_str"));
        } else {
            inputParam.put("inquiryId", id);
            inputParam.put("message", "Inserted successfully");
            inputParam.put("eligibility No", param.get("elgbl_str"));

        }
        return inputParam;
    }

// public Map<String, Object> save_inqury(Map<String, Object> param) {
//     String sql = "";
//     // Check if "id" exists to determine whether to perform an INSERT or UPDATE operation
//     if (param.get("id") == null) {
//         System.out.println("Creating new inquiry...");
//         sql += "INSERT INTO eligibility_enquiry ( ";
//         sql += "    first_name, middle_name, last_name, phone_number, email, residence_start_date, visa_category, ";
//         sql += "    gross_income_2022, gross_income_2023, gross_income_2024, gross_income_2025, ";
//         sql += "    eds_2022, eds_2023, eds_2024, eds_2025, latest_payslip, ";
//         sql += "    family_members, apply_for_spouse, dependent_children_count, application_date, additional_details, ";
//         sql += "    status,eds_1,eds_2,eds_3,eds_4,latest_payslip_bytea";
//         sql += "    ) VALUES ( ";
//         sql += "    :first_name, :middle_name, :last_name, :phn_num, :email, :residence_start_date, :VisaCategory, ";
//         sql += "    :grossIncome2022, :grossIncome2023, :grossIncome2024, :grossIncome2025, ";
//         sql += "    :eds2022, :eds2023, :eds2024, :eds2025, :latestPayslip, ";
//         sql += "    :family_members, :apply_for_spouse, :dependent_children_count, :application_date, :additional_details, ";
//         sql += "    :status,:eds1,:eds2,:eds3,:eds4,latest_payslip";
//         sql += "    );";
//     } else {
//         // UPDATE operation
//         System.out.println("Updating existing inquiry...");
//         sql += "UPDATE eligibility_enquiry ";
//         sql += "SET first_name = :first_name, ";
//         sql += "    middle_name = :middle_name, ";
//         sql += "    last_name = :last_name, ";
//         sql += "    phone_number = :phn_num, ";
//         sql += "    email = :email, ";
//         sql += "    residence_start_date = :residence_start_date, ";
//         sql += "    visa_category = :VisaCategory, ";
//         sql += "    gross_income_2022 = :grossIncome2022, ";
//         sql += "    gross_income_2023 = :grossIncome2023, ";
//         sql += "    gross_income_2024 = :grossIncome2024, ";
//         sql += "    gross_income_2025 = :grossIncome2025, ";
//         sql += "    eds_2022 = :eds2022, ";
//         sql += "    eds_2023 = :eds2023, ";
//         sql += "    eds_2024 = :eds2024, ";
//         sql += "    eds_2025 = :eds2025, ";
//         sql += "    latest_payslip = :latestPayslip, ";
//         sql += "    family_members = :family_members, ";
//         sql += "    apply_for_spouse = :apply_for_spouse, ";
//         sql += "    dependent_children_count = :dependent_children_count, ";
//         sql += "    application_date = :application_date, ";
//         sql += "    additional_details = :additional_details, ";
//         sql += "    status = :status ";
//         sql += "WHERE id = :id;";
//     }

//     // Execute the SQL query using the Save method
//     Long id = this.Save(sql, param, "id");

//     // Retrieve and return the updated or inserted record
//     Map<String, Object> inputParam = new HashMap<>();
//     if (param.get("id") != null) {
//         inputParam.put("inquiryId", Long.parseLong(param.get("id").toString()));
//         inputParam.put("message", "Updated Successfully");
      

//     } else {
//         inputParam.put("inquiryId", id);
//         inputParam.put("message", "inserted successfully");

//     }

//     return inputParam;
// }

public List<Map<String, Object>> getInquiry() {
    String sql = "SELECT " +
             "    id AS inquiryid, " +
             "    eligibility_no, "+
             "    first_name, " +
             "    middle_name, " +
             "    last_name, " +
             "    phone_number, " +
             "    email, " +
             "    residence_start_date, " +
             "    visa_category, " +
             "    gross_income_2022, " +
             "    gross_income_2023, " +
             "    gross_income_2024, " +
             "    gross_income_2025, " +
             "    eds_2022, " +
             "    eds_2023, " +
             "    eds_2024, " +
             "    eds_2025, " +
             "    latest_payslip, " +
             "    family_members, " +
             "    apply_for_spouse, " +
             "    dependent_children_count, " +
             "    application_date, " +
             "    additional_details, " +
             "    status, " +
             "    created_at " +
             "FROM eligibility_enquiry;";

    List<Map<String,Object>>  Inquiry_list = this.executeQuery(sql);
    return Inquiry_list;
}
public List<Map<String, Object>> getDocumentsById(Integer id) {
    String sql = "SELECT eds_2022,eds_2023,eds_2024,eds_2025 eds_1, eds_2, eds_3, eds_4 FROM eligibility_enquiry WHERE id = :id";
    Map<String, Object> param = new HashMap<>();
    param.put("id", id);
    return this.executeQuery(sql,param);
}

public String get_next_elgblity_no() {
    String sql = "SELECT eligibility_no FROM public.eligibility_enquiry ORDER BY id DESC LIMIT 1;";
    return this.execute(sql);
}

public List<Map<String, Object>> updateEligibilityStatus(Map<String, Object> param2) {
    String sql = "UPDATE public.eligibility_enquiry SET status = :status,status_message =:status_msg  WHERE id = :id;";
    Long id = this.Save(sql, param2, "id");

    Map<String, Object> inputParam = new HashMap<>();
    inputParam.put("inquiryId", id);
    inputParam.put("message", "Status updated successfully");
    
    if (param2.containsKey("eligibility_no")) {
        inputParam.put("eligibility_no", param2.get("eligibility_no"));
    }

    List<Map<String, Object>> result = new ArrayList<>();
    result.add(inputParam);

    return result;
}




    
}
