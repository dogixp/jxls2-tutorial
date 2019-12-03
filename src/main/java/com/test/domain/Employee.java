package com.test.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
	
    private String name;
    
    private Double payment;
    
    private Double bonus;
    
    private Date birthDate;
  
    
}