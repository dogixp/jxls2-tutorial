package com.test.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Department {
	private String name;
    private Employee chief;
    private List<Employee> staff = new ArrayList<Employee>();
    private String link;
    
    public Department( String name ) {
    	this.name = name;
    }

	public void addEmployee(Employee employee) {
		// 
		staff.add( employee);
	}

    public int getHeadcount() {
        return staff.size();
    }
}
