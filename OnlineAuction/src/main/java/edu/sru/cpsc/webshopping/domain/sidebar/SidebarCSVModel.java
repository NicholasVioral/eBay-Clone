package edu.sru.cpsc.webshopping.domain.sidebar;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class SidebarCSVModel {

	@CsvBindByName(column = "tabid")
	private long tabid;

	@CsvBindByName(column = "display_text")
	private String displayText;

	@CsvBindByName(column = "link_to")
	private String linkTo;

	@CsvBindByName(column = "role")
	private String role;

	//lombok.Data covers all getters and setters
}