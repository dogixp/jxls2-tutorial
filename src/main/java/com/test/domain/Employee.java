package com.test.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
	
	 public Employee(String name, double payment, double bonus, Date birthDate) {
		//
		 this.name = name;
		 this.payment = payment;
		 this.bonus = bonus;
		 this.birthDate = birthDate;
	}

	public Employee(String name, int age , int payment, double bonus) {
		//
		this.name = name;
		this.age = age;
		this.payment = (double)payment;
		this.bonus = bonus;
	}

	public Employee(String name, Date birthDate, int payment, double bonus) {
		// 
		this.name = name;
		this.birthDate = birthDate;
		this.payment = (double) payment;
		this.bonus = bonus;
	}

	private String name;
	
	private Double payment;
	
	private Double bonus;
	
	private Date birthDate;
	
	private int age;
	
	private Employee superior;
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}