package edu.sru.cpsc.webshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;

/** Controller for interacting with the Widgets database table */
@RestController
@RequestMapping("/api/widgets")
public class WidgetController {
  //private final WidgetRepository<Widget> widgetRepository;
	private StatisticsDomainController statControl;
	
	//TODO:
	//ADD back stats on widget add
	
  // Widget CRUD functions
  @Autowired
  private WidgetRepository widgetRepository;
  
  @Autowired
  private CategoryRepository categoryRepository;

  // Create a new Widget
  @PostMapping("/add")
  public Widget addWidget(@Validated @RequestBody Widget widget, BindingResult result) {
	  Statistics stats = new Statistics(StatsCategory.WIDGETCREATED, 1); 
	  stats.setDescription("Widget: " + widget.getName() + " was created"); 
	  //statControl.addStatistics(stats);
      if (result.hasErrors()) {
          throw new IllegalArgumentException("Validation errors");
      }
      
      // Retrieve the existing Category from the database
      Category existingCategory = categoryRepository.findById(widget.getCategory().getId())
          .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
      
      // Set the existing Category into the Widget
      widget.setCategory(existingCategory);
      System.out.println(widget.getName());
      System.out.println(widget.getDescription());
      
      return widgetRepository.save(widget);
  }
  
  @RequestMapping("/add-simple") public Widget addWidgetnobinding(@Validated Widget widget) {
	 return widgetRepository.save(widget);
  }

  // Read a single Widget by ID
  @GetMapping("/{id}")
  public Widget getWidget(@PathVariable long id) {
      return widgetRepository.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Invalid widget ID"));
  }

  // Read all Widgets
  @GetMapping("/all")
  public Iterable<Widget> getAllWidgets() {
      return widgetRepository.findAll();
  }

  // Update a Widget by ID
  @PutMapping("/update/{id}")
  public Widget updateWidget(@PathVariable long id, @Validated @RequestBody Widget newWidget, BindingResult result) {
      if (result.hasErrors()) {
          throw new IllegalArgumentException("Validation errors");
      }
      return widgetRepository.findById(id)
              .map(widget -> {
                  widget.setName(newWidget.getName());
                  widget.setDescription(newWidget.getDescription());
                  widget.setCategory(newWidget.getCategory());
                  widget.setAttributes(newWidget.getAttributes());
                  return widgetRepository.save(widget);
              })
              .orElseThrow(() -> new IllegalArgumentException("Invalid widget ID"));
  }

  // Delete a Widget by ID
  @DeleteMapping("/delete/{id}")
  public void deleteWidget(@PathVariable long id) {
      Widget widget = widgetRepository.findById(id)
              .orElseThrow(() -> new IllegalArgumentException("Invalid widget ID"));
      widgetRepository.delete(widget);
  }
  
	/*
	 * @RequestMapping("add-widget") public Widget addWidget(@Validated Widget
	 * widget, BindingResult result) { Statistics stats = new
	 * Statistics(Category.WIDGETCREATED, 1); stats.setDescription("Widget: " +
	 * widget.getName() + " was created"); statControl.addStatistics(stats); return
	 * widgetRepository.save(widget); }
	 * 
	 * @RequestMapping("add-widget-simple") public Widget
	 * addWidgetnobinding(@Validated Widget widget) {
	 * 
	 * return widgetRepository.save(widget); }
	 * 
	 * @RequestMapping({"get-widget"}) public Widget getWidget(@PathVariable("id")
	 * long id) { Widget widget = widgetRepository .findById(id) .orElseThrow(() ->
	 * new IllegalArgumentException("Invalid ID specified to get-widget")); return
	 * widget; }
	 * 
	 * @RequestMapping({"/get-all-widgets"}) public Iterable<Widget> getAllWidgets()
	 * { Iterable<Widget> widgets = widgetRepository.findAll(); return () ->
	 * StreamSupport.stream(widgets.spliterator(), false) .filter( widget ->
	 * PreLoad.subCategoryConfiguration().stream()
	 * .anyMatch(widget.getSubCategory()::equalsIgnoreCase)) .iterator(); }
	 * 
	 * @PostMapping({"update-widget/{id}"}) public void updateWidget(
	 * 
	 * @Validated Widget widget, BindingResult result, @PathVariable("id") long id)
	 * { if (result.hasErrors()) { throw new
	 * IllegalArgumentException("BindResult error in WidgetController.updateWidget"
	 * ); } widgetRepository .findById(id) .orElseThrow( () -> new
	 * IllegalArgumentException(
	 * "Invalid ID provided for updating widget in WidgetController.updateWidget"));
	 * widgetRepository.save(widget); }
	 * 
	 * @GetMapping({"delete-widget/{id}"}) public void
	 * deleteWidget(@PathVariable("id") long id) { Widget widget =
	 * widgetRepository.findById(id).orElseThrow(() -> new
	 * IllegalArgumentException("Invalid ID provided for deleting widget."));
	 * widgetRepository.delete(widget); }
	 * 
	 */
}
