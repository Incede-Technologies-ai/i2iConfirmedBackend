package com.I2I.I2IBaceknd.Repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.I2I.I2IBaceknd.Entitiy.PaymentMaster;

public interface PaymentMasterRepo extends JpaRepository<PaymentMaster, Long> 
{
	List<PaymentMaster> findByMainframe(String mainframe);
}
