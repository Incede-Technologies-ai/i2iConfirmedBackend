package com.I2I.I2IBaceknd.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2I.I2IBaceknd.Entitiy.PaymentMaster;
import com.I2I.I2IBaceknd.Repository.PaymentMasterRepo;

@Service
public class PaymentMasterService 
{
	 @Autowired
	    private PaymentMasterRepo mainRepository;

	    public PaymentMaster saveMain(PaymentMaster entity)
	    {
	        return mainRepository.save(entity);
	    }

	    public List<PaymentMaster> getAllMain() {
	        return mainRepository.findAll();
	    }
	    public PaymentMaster updateMain(Long id, PaymentMaster updatedData) {
	        return mainRepository.findById(id).map(existing -> {
	            existing.setMainframe(updatedData.getMainframe()); // adjust fields accordingly
	            existing.setAmount(updatedData.getAmount()); // if applicable
	            return mainRepository.save(existing);
	        }).orElseThrow(() -> new RuntimeException("MainEntity not found with id: " + id));
	    }
	    public List<PaymentMaster> getByMainframe(String mainframe) {
	        return mainRepository.findByMainframe(mainframe);
	    }


}
