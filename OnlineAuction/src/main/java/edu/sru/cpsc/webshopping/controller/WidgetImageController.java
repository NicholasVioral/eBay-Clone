package edu.sru.cpsc.webshopping.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetImageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@RestController
public class WidgetImageController {
	private WidgetImageRepository widgetImageRepository;
	
	@PersistenceContext private EntityManager entityManager;
	WidgetImageController(
			WidgetImageRepository widgetImageRepository){
		this.widgetImageRepository = widgetImageRepository;
	}
	
	@RequestMapping("/get-widget-image/{id}")
	@Transactional
	public WidgetImage getWidgetImage(@PathVariable("id") long id) {
		WidgetImage widgetImage = entityManager.find(WidgetImage.class, id);
		return widgetImage;
	}
	
	/**
	 * return all widgetImages associated with a marketlisting
	 * @param listing
	 * @return
	 */
	@RequestMapping("/get-widget-image-by-listing")
	public WidgetImage[] getwidgetImageByMarketListing(@PathVariable("marketListing") MarketListing listing) {
		List<WidgetImage> widgetImages = widgetImageRepository.findByMarketListing(listing);
		return widgetImages.toArray(new WidgetImage[widgetImages.size()]);
	}
	
	/**
	 * add images to the widgetimage database
	 * @param widgetImage
	 * @return
	 */
	@PostMapping("/add-widget-image")
	@Transactional
	public WidgetImage addWidgetImage(@Validated WidgetImage widgetImage) {
		MarketListing marketListing = entityManager.find(MarketListing.class, widgetImage.getMarketListing().getId());
		System.out.println(widgetImage.getImageName());
		widgetImage.setMarketListing(marketListing);
		return widgetImageRepository.save(widgetImage);
	}
	
	@Transactional
	public void deleteWidgetImage(long id)
	{
		widgetImageRepository.delete(getWidgetImage(id));
	}
}
