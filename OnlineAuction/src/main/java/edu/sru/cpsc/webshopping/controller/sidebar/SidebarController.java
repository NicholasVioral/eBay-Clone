package edu.sru.cpsc.webshopping.controller.sidebar;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opencsv.bean.CsvToBeanBuilder;

//import edu.sru.cpsc.webshopping.domain.sidebar.Sidebar;
import edu.sru.cpsc.webshopping.domain.sidebar.SidebarCSVModel;
import edu.sru.cpsc.webshopping.repository.sidebar.SidebarRepository;

@Controller
public class SidebarController {
	@Autowired
	private SidebarRepository sidebarRepository;
	private final static String csvFilePath = "Documents\\Program Documents\\Excel-Sheets\\sidebar.csv";


	public SidebarController(SidebarRepository sidebarRepository) {
		this.sidebarRepository = sidebarRepository;
	}
	public SidebarRepository getSidebarRepository() {
		return sidebarRepository;
	}
	public void setSidebarRepository(SidebarRepository sidebarRepository) {
		this.sidebarRepository = sidebarRepository;
	}

	/*
	 * This is the code for the MySQL repository - I can't get it working
	 * since the repository keeps being initialized as null.
	 * 
	 * 
	 * public Iterable<Sidebar> getAllTabs() {
		Iterable<Sidebar> sidebar = sidebarRepository.findAll();
		return () ->
		StreamSupport.stream(sidebar.spliterator(), true).iterator();
	}*/

	/**
	 * This is a CSV based workaround to the repository issues I've been having.
	 * It reads data from file with name stored in String variable csvFilePath
	 * into a list named tabs, then returns tabs.
	 * 
	 * Prerequisites: csvFilePath provided, method called by controller
	 * Post-requisites: List of SidebarCSVModel called tabs is returned
	 * @return
	 */
	public List<SidebarCSVModel> readAllTabs() {
		List<SidebarCSVModel> tabs;
		try {
			tabs = new CsvToBeanBuilder(new FileReader(csvFilePath))
			       .withType(SidebarCSVModel.class).build().parse();

		}
		catch (FileNotFoundException e) {
			System.out.println("sidebar.csv is missing, please find it");
			tabs = new ArrayList<SidebarCSVModel>(); // make tabs an empty list
		}
		System.out.println(tabs);
		return tabs;
	}
	
	
	
}

	