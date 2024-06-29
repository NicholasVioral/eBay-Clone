package edu.sru.cpsc.webshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeRepository;
import edu.sru.cpsc.webshopping.service.AttributeService;

@RestController
@RequestMapping("/api/attributes")
public class AttributeController {
    
    @Autowired
    private AttributeService attributeService;

    @Autowired
    private AttributeRepository attributeRepository;

    @PostMapping("/add")
    public Attribute addAttribute(@Validated @RequestBody String name, String dataType, Category category, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Validation errors");
        }
        return attributeService.associateAttributeWithCategory(name, dataType, category);
    }

    /*
	 * gets entry of a attribute by ID
	 * @param Id of attribute to be retrieved
	 * @return attribute entry associated with ID
	 */
    @GetMapping("/{id}")
    public Attribute getAttribute(@PathVariable long id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attribute ID"));
    }
    
    // Read all Attributes
    @GetMapping("/all")
    public Iterable<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }

}