package edu.sru.cpsc.webshopping.domain.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Applicant {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String firstName;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String lastName;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String phoneNumber;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String email;

		private String applicationDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String roleAppliedfor;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String answer1;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String answer2;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String answer3;
		@Size(max=220, message = "Maximum Length is 220 chars")
		private String answer4;
		
		private Boolean reviewed = false;

		public String getEmail() { 
			return email; 
		}
		
		public void setEmail(String email) {
			this.email = email; 
		}

		public String getApplicationDate() { 
			return applicationDate; 
		}
		
		public void setApplicationDate(String applicationDate) { 
			this.applicationDate = applicationDate; 
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getRoleAppliedfor() {
			return roleAppliedfor;
		}

		public void setRoleAppliedfor(String roleAppliedfor) {
			this.roleAppliedfor = roleAppliedfor;
		}

		public String getAnswer1() {
			return answer1;
		}

		public void setAnswer1(String answer1) {
			this.answer1 = answer1;
		}

		public String getAnswer2() {
			return answer2;
		}

		public void setAnswer2(String answer2) {
			this.answer2 = answer2;
		}

		public String getAnswer3() {
			return answer3;
		}

		public void setAnswer3(String answer3) {
			this.answer3 = answer3;
		}

		public String getAnswer4() {
			return answer4;
		}

		public void setAnswer4(String answer4) {
			this.answer4 = answer4;
		}

		public Boolean getReviewed() {
			return reviewed;
		}

		public void setReviewed(Boolean reviewed) {
			this.reviewed = reviewed;
		}
		
}
