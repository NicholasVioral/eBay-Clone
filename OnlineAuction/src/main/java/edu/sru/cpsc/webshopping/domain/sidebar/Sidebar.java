package edu.sru.cpsc.webshopping.domain.sidebar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "sidebar")
public class Sidebar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tabID;

	@Column(name = "display_text")
	private String displayText;

	@Column(name = "link_to")
	private String link;

	@Column(name = "role")
	private String role;

	public Long getTabID() {
		return tabID;
	}

	public void setTabID(Long tabID) {
		this.tabID = tabID;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}