package edu.sru.cpsc.webshopping.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeSuggestion;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRepository;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeSuggestionRepository;

/**
 * Service to load suggestions from a URL or XLSX file
 * @author Wolfgang
 */

@Service
public class SuggestionService {

	@Autowired
	private AttributeRepository attributeRepository;
	
	@Autowired
	private AttributeSuggestionRepository suggestionRepository;
	
	private HashSet<Object> suggestions = new HashSet<Object>();
	
	/**
	 * Loads car part suggestions from the given url.
	 * @param url
	 * @author Wolfgang
	 */
	
	public void loadSuggestions(String url) {
		Resource excelFile;
		try {
			downloadFromURL(url, "/Website_Car_Parts_Categories.xlsx");
			excelFile = new ClassPathResource("Website_Car_Parts_Categories.xlsx");
			loadSuggestions(excelFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads car part suggestions from the given excel file
	 * @param file
	 * @throws Exception
	 */
	
	public void loadSuggestions(Resource file) throws Exception {
		if(IterableUtils.size(suggestionRepository.findAll()) != 0) {
			System.out.println("Data already found, skipping load function.");
			return;
		}
		String year;
		String make;
		String model;
		String submodel;
		String engine;
		
		System.out.println("---Now Loading Suggestions from Excel File---");
		
		try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
			 Sheet sheet = workbook.getSheetAt(0);
			 for(Row row : sheet) {
				 if(row.getRowNum() == 0) {
					 continue;
				 }
				 year = getData(row.getCell(0));
				 make = getData(row.getCell(1));
				 model = getData(row.getCell(2));
				 submodel = getData(row.getCell(3));
				 engine = getData(row.getCell(4));
				 
				 checkAndSave(year, "Year");
				 checkAndSave(make, "Make");
				 checkAndSave(model, "Model");
				 checkAndSave(submodel, "Submodel");
				 checkAndSave(engine, "Engine");
			 }
			 System.out.println("---Finished Loading Suggestions from Excel File---");
		}
	}
	
	/**
	 * Gets the appropriate value from the given cell.
	 * @param cell
	 * @return
	 * @author Wolfgang
	 */
	private String getData(Cell cell) {
		if(cell == null) {
			return "";
		}
		else if (cell.getCellType().toString() == "NUMERIC") {
			return Integer.toString((int)cell.getNumericCellValue());
		}
		else if(cell.getCellType().toString() == "BLANK") {
			return "";
		}
		else {
			return cell.getStringCellValue();
		}
	}
	
	/**
	 * Checks if the attribute is already contained in the database. 
	 * If it isn't it saves the attribute suggestion to the database
	 * @param data
	 * @param attributeType
	 * @author Wolfgang
	 */
	private void checkAndSave(String data, String attributeType) {
		if(!suggestions.contains(data) && data != "") {
			AttributeSuggestion suggestion = new AttributeSuggestion();
			Attribute attribute = attributeRepository.findFirstByAttributeKey(attributeType).get();
			suggestion.setAssociatedAttribute(attribute);
			suggestion.setValue(data);
			suggestionRepository.save(suggestion);
			suggestions.add(data);			
		}
	}
	
	/**
	 * Handles the downloading of the file from the URL
	 * @param urlString
	 * @param filepath
	 * @throws IOException
	 * @author Wolfgang
	 */
	private void downloadFromURL(String urlString, String filepath) throws IOException {
		URL url = new URL(urlString);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(filepath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
	}
}