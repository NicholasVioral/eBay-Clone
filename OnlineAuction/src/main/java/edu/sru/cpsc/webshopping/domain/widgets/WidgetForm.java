package edu.sru.cpsc.webshopping.domain.widgets;

import java.util.ArrayList;
import java.util.List;

public class WidgetForm {
		private String name;
		private String description;
		private List<AttributeFormEntry> entries = new ArrayList<>();

		public WidgetForm() {}
	
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public List<AttributeFormEntry> getEntries() {
			return entries;
		}
		public void setEntries(List<AttributeFormEntry> entries) {
			this.entries = entries;
		}
}
