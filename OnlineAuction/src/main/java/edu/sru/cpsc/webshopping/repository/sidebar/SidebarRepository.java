package edu.sru.cpsc.webshopping.repository.sidebar;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.sru.cpsc.webshopping.domain.sidebar.Sidebar;

@Repository
public interface SidebarRepository extends CrudRepository<Sidebar, Long> {
	//Iterable<Sidebar> getAllTabs();
}