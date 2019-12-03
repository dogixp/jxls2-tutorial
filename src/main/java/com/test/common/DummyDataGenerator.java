package com.test.common;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.test.domain.Employee;

public class DummyDataGenerator {
	  
    public static List<Employee> generateSampleEmployeeData(){
    	
    	Employee e1 = new Employee( "TEST1" , 100.0 , 0.2 , new Date());
		Employee e2 = new Employee( "TEST2" , 150.0 , 0.3 , new Date());
		Employee e3 = new Employee( "TEST3" , 200.0 , 0.4 , new Date());
		
		return Arrays.asList( e1 , e2 , e3 );
    }
}
