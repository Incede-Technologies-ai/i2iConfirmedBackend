package com.I2I.I2IBaceknd.Entitiy;



import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PaymentMaster")
@Data
public class PaymentMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long primaryKey;
    private String mainframe;
    private Double amount;
   

}
