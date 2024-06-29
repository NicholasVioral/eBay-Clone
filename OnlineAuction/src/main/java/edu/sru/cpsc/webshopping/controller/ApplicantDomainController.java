package edu.sru.cpsc.webshopping.controller;

import java.util.Optional;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;



@RestController
public class ApplicantDomainController {
	private ApplicantRepository Applicantrepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	public ApplicantDomainController(ApplicantRepository Applicantrepository) {
		this.Applicantrepository = Applicantrepository;
	}
	

	@RequestMapping("/get-Applicant/{id}")
	@Transactional
	public Applicant getApplicant(@PathVariable("id") long id) {
	
		Applicant Applicant = entityManager.find(Applicant.class, id);
		return Applicant;
	}
	

	@RequestMapping({"/get-all-Applicants"})
	public Iterable<Applicant> getAllBoxes()
	{
		Iterable<Applicant> applicants = Applicantrepository.findAll();
		return applicants;
	}
	

	@Transactional
	@PostMapping("/add-Applicant") 
	public void addApplicant(@Validated Applicant Applicant, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Applicant");
		}

		Applicantrepository.save(Applicant);
	}
	@Transactional
	@PostMapping("/add-Applicant-simple") 
	public void addApplicantSimple(@Validated Applicant Applicant) {

		Applicantrepository.save(Applicant);
	}

	@Transactional
	@PostMapping("/edit-Applicant")
	public void editApplicant(@Validated Applicant updatedApplicant) {
		Optional<Applicant> dbListing = Applicantrepository.findById(updatedApplicant.getId());
		Applicantrepository.save(dbListing.get());
	}

	@PostMapping("/delete-Applicant/{id}")
	public void deleteApplicant(@PathVariable Applicant applicant) {
		Applicantrepository.delete(applicant);
	}
}
