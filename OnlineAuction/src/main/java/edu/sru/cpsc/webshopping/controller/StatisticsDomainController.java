package edu.sru.cpsc.webshopping.controller;



import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.repository.stats.StatsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;



@RestController
public class StatisticsDomainController {
	private StatsRepository Statisticsrepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	public StatisticsDomainController(StatsRepository Statisticsrepository) {
		this.Statisticsrepository = Statisticsrepository;
	}
	

	@RequestMapping("/get-Statistics/{id}")
	@Transactional
	public Statistics getStatistics(@PathVariable("id") long id) {
	
		Statistics Statistics = entityManager.find(Statistics.class, id);
		return Statistics;
	}
	public Statistics[] getStatisticsByHour(LocalDateTime date1,LocalDateTime date2,StatsCategory category) {
		Statistics[] stats = null;
		Statistics[] statTemp = null;


		for(int i = date1.getHour();i <= date2.getHour();i++) {
			
			statTemp = Statisticsrepository.findByHour(i);
			stats = Stream.concat(Arrays.stream(stats), 
					Arrays.stream(statTemp))
				.toArray(Statistics[]::new);
		}
		return stats;
	}


	

	@Transactional
	@PostMapping("/add-Statistics") 
	public void addStatistics(@Validated Statistics Statistics, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException("Invalid Statistics");
		}
		
		Statisticsrepository.save(Statistics);
	}
	@PostMapping("/add-Statistics-no-binding") 
	public void addStatistics(@Validated Statistics Statistics) {
	
		
		Statisticsrepository.save(Statistics);
	}
	@Transactional
	@GetMapping("get-stats-category")
	public Statistics[] getStatsByCategory(@Validated StatsCategory category) {
		return Statisticsrepository.findByCategory(category);
	}
	@Transactional
	@GetMapping("get-stats-date")
	public Statistics[] getStatsByDate(@Validated LocalDateTime date) {
		return Statisticsrepository.findByDate(date);
	}

	@PostMapping("/delete-Statistics/{id}")
	public void deleteStatistics(@PathVariable long id) {
		Statisticsrepository.deleteById(id);
	}
}
