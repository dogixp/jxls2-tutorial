package com.test.demo;

import com.test.common.DummyDataGenerator;
import com.test.domain.Department;
import com.test.domain.Employee;
import com.test.domain.Rows;
import com.test.function.MyCellUpdater;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xml.XmlAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * Copyright 2019 YunPD jxls-tutorial Demo

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 *
 *
 */
@Controller
public class JxlsDemoController {
	
	
	/**
	 * Object Collection Demo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo1")
	public void demo1( HttpServletResponse response ) throws Exception{
		
	     List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();
	     
	    try(InputStream is = JxlsDemoController.class.getResourceAsStream("object_collection_template.xls")) {
	        
            Context context = new Context();
            context.putVar("employees", employees);
            
            setResponse( response , "object_collection_output.xls");
            
            JxlsHelper.getInstance().processTemplate(is, response.getOutputStream() , context);
	    }
		
	}
	
	/**
	 * Excel Formulas Demo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo2")
	public void demo2( HttpServletResponse response ) throws Exception{
		
	     List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();
	     
	    try(InputStream is = JxlsDemoController.class.getResourceAsStream("formulas_template.xls")) {
	        
            Context context = new Context();
            context.putVar("employees", employees);
            
            setResponse( response , "formulas_output.xls");
            
            JxlsHelper.getInstance().processTemplateAtCell(is, response.getOutputStream() , context, "Result!A1");
	    }
		
	}
	
	/**
	 * Dynamic Grid Demo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo3")
	public void demo3( HttpServletResponse response ) throws Exception{
		
		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();
		
		 try(InputStream is = JxlsDemoController.class.getResourceAsStream("grid_template.xls")) {
			 
            Context context = new Context();
            context.putVar("headers", Arrays.asList("Name", "Birthday", "Payment"));
            context.putVar("data", employees);
            
            setResponse( response , "grid_output.xls");
            
            JxlsHelper.getInstance().processGridTemplateAtCell(is, response.getOutputStream() , context, "name,birthDate,payment", "Sheet2!A1");
	    }
	}
	
	/**
	 * Dynamic Grid Demo + mergeCells
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo4")
	public void demo4( HttpServletResponse response ) throws Exception{
		
		//dummy data
		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();

		//mergeCells dummy data
		List<Rows> rows = Arrays.asList( new Rows(5, "YunPD Dev") );
		
		try(InputStream is = JxlsDemoController.class.getResourceAsStream("grid_template2.xls")) {
			
			Context context = new Context();
            context.putVar( "employees", employees);
            context.putVar( "rows", rows);
            
            setResponse( response , "grid_output2.xls");
            
            JxlsHelper.getInstance().processTemplate(is, response.getOutputStream() , context);
	    }
	}
	
	/**
	 * Object Collection Output with XML Builder Demo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo5")
	public void demo5( HttpServletResponse response ) throws Exception{

		//dummy data
		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();
		
		try(InputStream is = JxlsDemoController.class.getResourceAsStream("object_collection_xmlbuilder_template.xls")) {
	        
	        setResponse( response , "object_collection_xmlbuilder.xls");
			
            Transformer transformer = TransformerFactory.createTransformer(is, response.getOutputStream());
            
            try (InputStream configInputStream = JxlsDemoController.class.getResourceAsStream("object_collection_xmlbuilder.xml")) {
            	
                AreaBuilder areaBuilder = new XmlAreaBuilder(configInputStream, transformer);
                List<Area> xlsAreaList = areaBuilder.build();
                Area xlsArea = xlsAreaList.get(0);
                Context context = new Context();
                context.putVar("employees", employees);
                xlsArea.applyAt(new CellRef("Result!A1"), context);
                transformer.write();
                
            }
	    }
	}
	
	/**
	 * UpdateCell-Command
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo6")
	public void demo6( HttpServletResponse response ) throws Exception {
		//dummy data
		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();
		
        try(InputStream is = JxlsDemoController.class.getResourceAsStream("updatecell_template.xlsx")) {
	        
	        setResponse( response , "updatecell_output.xlsx");
            
            Context context = new Context();
            context.putVar( "employees", employees);
            context.putVar( "myCellUpdater", new MyCellUpdater());
            JxlsHelper.getInstance().processTemplate(is, response.getOutputStream() , context);
        }
	}
	
	/**
	 * Multiple sheets
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo7")
	public void demo7( HttpServletResponse response ) throws Exception {
		
		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();
		try(InputStream is = JxlsDemoController.class.getResourceAsStream("multisheet_template.xls")) {
	        
	        setResponse( response , "updatecell_output.xls" );
			
			Context context = new Context();
			context.putVar("employees", employees);
			context.putVar("sheetNames", Arrays.asList("Emp 1", "Emp 2", "Emp 3", "Emp 4", "Emp 5"));
			JxlsHelper.getInstance().processTemplate(is, response.getOutputStream() , context);
			
		}
	}

	/**
	 * NestedCommandDemo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo8")
	public void demo8( HttpServletResponse response ) throws Exception {

		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();

		try (InputStream is = JxlsDemoController.class.getResourceAsStream("nested_command_template.xls")){

			setResponse( response , "nested_command_output.xls");
			Context context = new Context();
			context.putVar("employees", employees);

			JxlsHelper.getInstance().processTemplate( is, response.getOutputStream() , context);
		}
	}

	/**
	 * response 다운로드 파일명 지정
	 * @param response
	 * @param downFileName
	 */
	public void setResponse( HttpServletResponse response  , String downFileName ) {

		response.setContentType( MediaType.APPLICATION_OCTET_STREAM_VALUE );
        response.setHeader( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downFileName );
	}
}



