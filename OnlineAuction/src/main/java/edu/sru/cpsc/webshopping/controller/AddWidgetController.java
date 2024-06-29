package edu.sru.cpsc.webshopping.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import edu.sru.cpsc.webshopping.controller.billing.StateDetailsController;
import edu.sru.cpsc.webshopping.domain.market.Auction;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.MarketListingCSVModel;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Attribute;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeFormEntry;
import edu.sru.cpsc.webshopping.domain.widgets.AttributeSuggestion;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetForm;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.widgets.AttributeSuggestionRepository;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetImageRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.service.AttributeService;
import edu.sru.cpsc.webshopping.service.CategoryService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.WidgetService;


/**
 * Controller for creating widgets and listings.
 * @author NICK
 * 
 */
@Controller
public class AddWidgetController
{
	@Autowired
	private WidgetService widgetService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AttributeService attributeService;

	@Autowired
	private UserService userService;

	@Autowired
	private StateDetailsController stateDetailsController;
	
	@Autowired
	private AttributeSuggestionRepository attributeSuggestionRepository;

	WidgetRepository widgetRepository;
	CategoryRepository categoryRepository;
	WidgetImageRepository widgetImageRepository;
	MarketListingRepository marketListingRepos;
	WidgetController widgetController;
	MarketListingDomainController marketListingController;
	WidgetImageController widgetImageController;
	UserRepository userRepo;
	Widget widget;
	Set<WidgetImage> listingImages = new HashSet<>();
	CategoryController categories;
	AttributeController attributeController;
	MarketListing marketListing;
	private Widget widgetStorage;
	private Category category;
	private WidgetImage tempImage = new WidgetImage();
	private String page;
	
	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public AddWidgetController(WidgetRepository widgetRepository, CategoryRepository categoryRepository, CategoryController categories,
	 		AttributeController attributeController, WidgetImageRepository widgetImageRepository, WidgetImageController widgetImageController,
			MarketListingRepository marketListingRepos, WidgetController widgetController, MarketListingDomainController marketListingController, UserRepository userRepo)
	{
		this.categories = categories;
		this.attributeController = attributeController;
		this.widgetRepository = widgetRepository;
		this.categoryRepository = categoryRepository;
		this.marketListingRepos = marketListingRepos;
		this.widgetController = widgetController;
		this.marketListingController = marketListingController;
		this.userRepo = userRepo;
		this.widgetImageRepository = widgetImageRepository;
		this.widgetImageController = widgetImageController;
	}

	@RequestMapping("/addWidget")
	public String addWidget(Model model, Principal principal)
	{
		if (principal  == null)
		{
			throw new IllegalStateException("Not logged in.");
		}

		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		
		setPage("widgets");
		model.addAttribute("categories", categories.getAllCategories());
		model.addAttribute("user", user);
		model.addAttribute("page", "addWidget");
		return "addWidget";
	}
	
	@RequestMapping("/createWidget")
	public String createWidget(Model model, Principal principal)
	{
		Category category = categoryRepository.findById(Long.parseLong("1"))
		        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		
		WidgetForm widgetForm = new WidgetForm();
		List<Attribute> attributes = categoryService.getTopRecommendedAttributes(category, 0);

		
		for (Attribute attribute : attributes) {
			WidgetAttribute widgetAttribute = new WidgetAttribute(attribute);
			AttributeFormEntry entry = new AttributeFormEntry(attribute, widgetAttribute);
			widgetForm.getEntries().add(entry);
		}
		
		ArrayList<String> yearSuggestions = new ArrayList<String>();
		ArrayList<String> makeSuggestions = new ArrayList<String>();
		ArrayList<String> modelSuggestions = new ArrayList<String>();
		ArrayList<String> submodelSuggestions = new ArrayList<String>();
		ArrayList<String> engineSuggestions = new ArrayList<String>();
		
		for(AttributeSuggestion suggestion : attributeSuggestionRepository.findAllByAssociatedAttribute(attributes.get(0))) {
			yearSuggestions.add(suggestion.getValue());
		}
		for(AttributeSuggestion suggestion : attributeSuggestionRepository.findAllByAssociatedAttribute(attributes.get(2))) {
			makeSuggestions.add(suggestion.getValue());
		}
		for(AttributeSuggestion suggestion : attributeSuggestionRepository.findAllByAssociatedAttribute(attributes.get(3))) {
			modelSuggestions.add(suggestion.getValue());
		}
		for(AttributeSuggestion suggestion : attributeSuggestionRepository.findAllByAssociatedAttribute(attributes.get(5))) {
			submodelSuggestions.add(suggestion.getValue());
		}
		for(AttributeSuggestion suggestion : attributeSuggestionRepository.findAllByAssociatedAttribute(attributes.get(6))) {
			engineSuggestions.add(suggestion.getValue());
		}
		
		List<Integer> requiredFields = Arrays.asList(0,1,2,3);
//		System.out.println(requiredFields.toString());
		model.addAttribute("requiredFields", requiredFields);

		String username = principal.getName();
		User user = userService.getUserByUsername(username);

		model.addAttribute("yearSuggestions", yearSuggestions.toArray());
		model.addAttribute("makeSuggestions", makeSuggestions.toArray());
		model.addAttribute("modelSuggestions", modelSuggestions.toArray());
		model.addAttribute("submodelSuggestions", submodelSuggestions.toArray());
		model.addAttribute("engineSuggestions", engineSuggestions.toArray());
		
		model.addAttribute("category", category);
		model.addAttribute("entries", widgetForm.getEntries());
		model.addAttribute("user", user);
		
		return "createWidgetTemplate";
	}
		
	@RequestMapping("/createWidgetListing") 
	public String createWidgetListing(Model model, @ModelAttribute WidgetForm widgetForm, BindingResult result, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);

		if (result == null) {
			result = new BeanPropertyBindingResult(widgetForm, "widgetForm");
		}

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println(error.getDefaultMessage());
			}
			return "createWidgetTemplate";  // return to the form page if there are errors
		}
	
		Category category = categoryRepository.findById(Long.parseLong("1"))
				.orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
		
		// create widget
		Widget widget = new Widget();
		widget.setCategory(category);
		widget.setName(widgetForm.getName());
		widget.setDescription(widgetForm.getDescription());

		Set<WidgetAttribute> widgetAttributes = new HashSet<>();
		Set<Attribute> attributes = new HashSet<>();

		for (AttributeFormEntry entry : widgetForm.getEntries()) {
			System.out.println(entry);
			Attribute attribute = entry.getAttribute();
			attribute = attributeService.associateAttributeWithCategory(attribute.getAttributeKey(), attribute.getDataType().toString(), category);
			WidgetAttribute widgetAttribute = entry.getWidgetAttribute();
			System.out.println(attribute.getAttributeKey() + " - " + attribute.getDataType() + " - " + widgetAttribute.getValue());
			widgetAttribute.setWidget(widget);
			widgetAttribute.setAttribute(attribute);
			widgetAttributes.add(widgetAttribute);
		}

		widget.setAttributes(widgetAttributes);
	
		this.widget = widget;
		
		model.addAttribute("Widget", widget);
		model.addAttribute("user", user);
		
		
		widgetService.addWidget(widget);
		widgetService.addWidgetAttributes(widgetAttributes);
		
	
		return "redirect:createListing"; 
	}

	@RequestMapping("/createListing")
	public String createListing(Model model, Principal principal)
	{
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		marketListing = new MarketListing();
		marketListing.setAuction(new Auction());
		model.addAttribute("states", stateDetailsController.getAllStates());
		model.addAttribute("listing", marketListing);
		model.addAttribute("Category", category);
		model.addAttribute("user", user);
		
		return "createListing";
	}
	
	
	/**
	 * 
	 * @param model
	 * @param coverImage Image to be displayed when browsing
	 * @param files all images associated with the current listing
	 * @param qty
	 * @param attributes
	 * @param marketListing
	 * @param result
	 * @return
	 */
	@RequestMapping("/addListing")
	public String addListing(Model model, @RequestParam("listingCoverImage") MultipartFile coverImage, @RequestParam("imageUpload") MultipartFile[] files, RedirectAttributes attributes, @Valid @ModelAttribute MarketListing marketListing, @RequestParam("stateId") String stateId, BindingResult result, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		//TODO UNCOMMENT AND REMOVE LINE BELOW WHEN AUCTIONS ARE RETURNED TO LISTINGS
//		marketListing.getAuction().setCurrentBid(marketListing.getAuction().getStartingBid());
		marketListing.getAuction().setCurrentBid(BigDecimal.ONE);
		marketListing.setSeller(user);
		marketListing.setWidgetSold(widget);
		marketListing.setDeleted(false);
		marketListing.getLocalPickup().getLocation().setState(stateDetailsController.getState(stateId));
		marketListing.getLocalPickup().setListing(marketListing);
		
		String coverImagePath = marketListing.getSeller().getId() + StringUtils.cleanPath(coverImage.getOriginalFilename());
//		String coverImagePath = marketListing.getSeller().getId() + StringUtils.cleanPath(files[0].getOriginalFilename());
		marketListing.setCoverImage(coverImagePath);
		
		Date date = new Date();
		marketListing.setCreationDate(date);
		
		System.out.println(marketListing.getCreationDate().toString());

		Set<String> allImageNames = new HashSet<>();
		List<MultipartFile> allImages = new ArrayList<>();
		
		allImages.add(coverImage);
		allImages.addAll(Arrays.asList(files));
		
		marketListingController.addMarketListing(marketListing);

		BigDecimal oneCent = new BigDecimal("0.01");

		if (marketListing.getPricePerItem() == null || marketListing.getPricePerItem().compareTo(oneCent) < 0) {
			setPage("error2");
			result.addError(new FieldError("pricePerItem", "pricePerItem", "Price per item must be greater than 0.01"));
		}

		if (marketListing.getAuction().getStartingBid() == null || marketListing.getAuction().getStartingBid().compareTo(oneCent) < 0) {
			setPage("error2");
			result.addError(new FieldError("auctionPrice", "auctionPrice", "Auction Price must be greater than 0.01"));
		}

		if (Long.valueOf(marketListing.getQtyAvailable()).compareTo((long) 0) <= 0) {
			setPage("error3");
			result.addError(new FieldError("pricePerItem", "pricePerItem", "Quantity must be greater than 0"));
		}
		
		//TODO REMOVE THE COMMENTS WHEN AUCTIONING IS ADDED BACK IN
//		if (result.hasErrors()) {
//			setWidgetStorage(widget);
//			widgetController.deleteWidget(getWidgetStorage().getId());
//			model.addAttribute("Category", category);
//			model.addAttribute("user", user);
//			model.addAttribute("listing", marketListing);
//			return "createListing";
//		}

		for (MultipartFile file : allImages) {
			if(!allImageNames.contains(file.getOriginalFilename()) && file.getOriginalFilename() != "") {
				allImageNames.add(file.getOriginalFilename());
				setListingImage(file, marketListing);
				System.out.println(allImageNames.toString());
			}
			
		}
		marketListing.setImages(listingImages);

		model.addAttribute("user", user);
		return "redirect:homePage";
	}
	
	/**
	 * Deletes the added widget from the widgets database and then returns to that widgets add page
	 * @param model
	 * @return
	 */
	
	@RequestMapping("/back-and-delete")
	public String backAndDelete(Model model)
	{
		widgetController.deleteWidget(widget.getId());
		return "redirect:createWidget";
	}
	
	/**
	 * adds to the widgetImage database and sets the foreign key of marketListing
	 * @param file incremented list of images to be associated with the market listing
	 * @param marketListing
	 */
	
	public void setListingImage(MultipartFile file, MarketListing marketListing)
	{
		//WidgetImage newImage = new WidgetImage();
		tempImage = new WidgetImage();
		tempImage.setImageName(marketListing.getSeller().getId() + StringUtils.cleanPath(file.getOriginalFilename()));
		tempImage.setMarketListing(marketListing);
		
		try {
			tempImage.setImageData(BlobProxy.generateProxy(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		widgetImageController.addWidgetImage(tempImage);
		listingImages.add(tempImage);
		
//		try {
//			String fileLocationTemp = new ClassPathResource("static/listingImages").getFile().getAbsolutePath() + "/" + tempImage.getImageName();
//			
//			try (FileOutputStream output = new FileOutputStream(fileLocationTemp)) {
//				output.write(file.getBytes());
//			}
//	
//			System.out.println("Upload successful, file saved at: " + fileLocationTemp);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("Upload failed");
//		}
	}

	@PostMapping({"/uploadComputerDataFile"})
	public String uploadComputerDataFile(@RequestParam("file") MultipartFile file) throws IOException {
		this.saveWidgetFromCSV(file, "computer");
		return "redirect:addComputer";
	}

	@PostMapping({"/uploadMarketListingDataFile"})
	public String uploadMarketListingDataFile(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		this.saveMarketListingFromCSV(file, user);
		return "redirect:homePage";
	}

	private void saveMarketListingFromCSV(final MultipartFile file, final User user) throws IOException {

		InputStreamReader streamReader =
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
		CsvToBean<MarketListingCSVModel> listingsCsvBean = new CsvToBeanBuilder<MarketListingCSVModel>(streamReader)
				.withType(MarketListingCSVModel.class).withIgnoreLeadingWhiteSpace(true).build();
		List<MarketListingCSVModel> marketListingCSVModels = listingsCsvBean.parse();

		marketListingCSVModels.forEach(marketListingCSVModel -> {
			MarketListing newMarketListing = new MarketListing();
			newMarketListing.setPricePerItem(marketListingCSVModel.getPricePerItem());
			newMarketListing.setQtyAvailable(marketListingCSVModel.getQtyAvailable());
			Set<WidgetImage> WI = new HashSet<WidgetImage>();
			WidgetImage image = new WidgetImage();
			image.setImageName("TempImage");
			WI.add(image);
			newMarketListing.setCoverImage("tempCoverImage");
			newMarketListing.setImages(WI);
			newMarketListing.setSeller(user);
			Optional<Widget> widgetSoldOptional = widgetRepository.findById(marketListingCSVModel.getWidgetId());
			if (widgetSoldOptional.isPresent()) {
				Widget widgetSold = widgetSoldOptional.get();
				newMarketListing.setWidgetSold(widgetSold);
			}
			newMarketListing.setDeleted(false);
			marketListingController.addMarketListing(newMarketListing);
		});

	}

	private void saveWidgetFromCSV(final MultipartFile file, final String subcategory) throws IOException {
		InputStreamReader streamReader =
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
		CsvToBeanBuilder<Widget> widgetsCsvBeanBuilder = new CsvToBeanBuilder<>(streamReader);
		CsvToBean<Widget> widgetsCsvBean =
				widgetsCsvBeanBuilder.withIgnoreLeadingWhiteSpace(true).build();
		List<Widget> widgets = widgetsCsvBean.parse();

		widgets.parallelStream().forEach(widgetRepository::save);
	}
	
	public Widget getWidgetStorage()
	{
		return widgetStorage;
	}

	public void setWidgetStorage(Widget widgetStorage)
	{
		this.widgetStorage = widgetStorage;
	}
}