package com.test.demo;

import com.test.common.DummyDataGenerator;
import com.test.common.SimpleAreaListener;
import com.test.domain.Department;
import com.test.domain.Employee;
import com.test.domain.Rows;
import com.test.function.MyCellUpdater;
import org.jxls.area.Area;
import org.jxls.area.XlsArea;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xml.XmlAreaBuilder;
import org.jxls.command.Command;
import org.jxls.command.EachCommand;
import org.jxls.command.IfCommand;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * Copyright ⓒ2019 YunPD jxls-tutorial Demo

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

	static Logger logger = LoggerFactory.getLogger(JxlsDemoController.class);
	
	
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
	        
	        setResponse( response , "multisheet_output.xls" );

			Context context = new Context();
			context.putVar("employees", employees);
			context.putVar("sheetNames", Arrays.asList("Emp 1", "Emp 2", "Emp 3", "Emp 4", "Emp 5"));
			JxlsHelper.getInstance().processTemplate(is, response.getOutputStream() , context);
			
		}
	}

	/**
	 * Multiple sheets2
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo8")
	public void demo8( HttpServletResponse response )  {

		List<Department> departments = DummyDataGenerator.createDepartments();

		try (InputStream is = JxlsDemoController.class.getResourceAsStream( "multisheet_template2.xlsx")){

			setResponse( response , "multisheet_output2.xlsx");

			List<String> sheetNames = new ArrayList<>();

			for( Department d : departments ){
				sheetNames.add( d.getName());
			}

			Context context = new Context();
			context.putVar("departments", departments);
			context.putVar("sheetNames", sheetNames);

			JxlsHelper.getInstance().processTemplate( is, response.getOutputStream() , context);
		} catch( Exception e ){
			e.printStackTrace();
		}
	}

	/**
	 * multisheet_markup_demo
	 * @param response
	 */

	@GetMapping("demo9")
	public void demo9( HttpServletResponse response ){

		List<Department> departments = DummyDataGenerator.createDepartments();

		try (InputStream is = JxlsDemoController.class.getResourceAsStream( "multisheet_markup_demo.xls")){

			setResponse( response , "multisheet_markup_output.xls");

			List<String> sheetNames = new ArrayList<>();

			for( Department d : departments ){
				sheetNames.add( d.getName());
			}

			Context context = PoiTransformer.createInitialContext();
			context.putVar("departments", departments);
			context.putVar("sheetNames", sheetNames);

			//with multi sheets it is better to use StandardFormulaProcessor by disabling the FastFormulaProcessor
			JxlsHelper
					.getInstance()
					.setUseFastFormulaProcessor(false)
					.setDeleteTemplateSheet(true)
					.processTemplate(is, response.getOutputStream() , context);

			//JxlsHelper.getInstance().processTemplate( is, response.getOutputStream() , context);
		} catch( Exception e ){
			e.printStackTrace();
		}

	}

	/**
	 * NestedCommandDemo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("demo10")
	public void demo10( HttpServletResponse response ) throws Exception {

		List<Employee> employees = DummyDataGenerator.generateSampleEmployeeData();

		try (InputStream is = JxlsDemoController.class.getResourceAsStream("nested_command_template.xls")){

			setResponse( response , "nested_command_output.xls");
			Context context = new Context();
			context.putVar("employees", employees);

			JxlsHelper.getInstance().processTemplate( is, response.getOutputStream() , context);
		}
	}

	/**
	 * java code each_if_demo
	 * @param response
	 * @throws Exception
	 */

	@GetMapping("demo11")
	public void demo11( HttpServletResponse response ) throws Exception {

		List<Department> departments = DummyDataGenerator.createDepartments();

		try (InputStream is = JxlsDemoController.class.getResourceAsStream("each_if_demo_template.xls")){

			setResponse( response , "each_if_demo_output.xls");

			Transformer transformer = TransformerFactory.createTransformer(is, response.getOutputStream() );
			System.out.println("Creating area");
			XlsArea xlsArea = new XlsArea("Template!A1:G15", transformer);
			XlsArea departmentArea = new XlsArea("Template!A2:G12", transformer);
			EachCommand departmentEachCommand = new EachCommand("department", "departments", departmentArea);
			XlsArea employeeArea = new XlsArea("Template!A9:F9", transformer);
			XlsArea ifArea = new XlsArea("Template!A18:F18", transformer);
			XlsArea elseArea = new XlsArea("Template!A9:F9", transformer);
			IfCommand ifCommand = new IfCommand("employee.payment <= 2000",
					ifArea,
					elseArea);
			ifArea.addAreaListener(new SimpleAreaListener(ifArea));
			elseArea.addAreaListener(new SimpleAreaListener(elseArea));
			employeeArea.addCommand(new AreaRef("Template!A9:F9"), ifCommand);
			Command employeeEachCommand = new EachCommand("employee", "department.staff", employeeArea);
			departmentArea.addCommand(new AreaRef("Template!A9:F9"), employeeEachCommand);
			xlsArea.addCommand(new AreaRef("Template!A2:F12"), departmentEachCommand);
			Context context = new Context();
			context.putVar("departments", departments);
			logger.info("Applying at cell " + new CellRef("Down!A1"));
			xlsArea.applyAt(new CellRef("Down!A1"), context);
			xlsArea.processFormulas();
			logger.info("Setting EachCommand direction to Right");
			departmentEachCommand.setDirection(EachCommand.Direction.RIGHT);
			logger.info("Applying at cell " + new CellRef("Right!A1"));
			xlsArea.reset();
			xlsArea.applyAt(new CellRef("Right!A1"), context);
			xlsArea.processFormulas();
			logger.info("Complete");
			transformer.write();
			logger.info("written to file");

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



