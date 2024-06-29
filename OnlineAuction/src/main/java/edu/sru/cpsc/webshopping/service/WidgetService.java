package edu.sru.cpsc.webshopping.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.sru.cpsc.webshopping.controller.MarketListingDomainController;
import edu.sru.cpsc.webshopping.controller.UserController;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetAttribute;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.widgets.CategoryRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetAttributeRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetImageRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;

/**
 * Service for handling widget, widget image, and market listing saving 
 */

@Service
public class WidgetService {

    private static final String UPLOAD_DIR = "src/main/resources/static/listingImages/";

    @Autowired
    private WidgetRepository widgetRepository;
    
    @Autowired
    private WidgetAttributeRepository widgetAttributeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WidgetImageRepository widgetImageRepository;

    @Autowired
    private MarketListingRepository marketListingRepos;

    @Autowired
    private UserController userController;

    @Autowired
    private MarketListingDomainController marketListingController;


    public void addWidget(Widget widget) {
        widgetRepository.save(widget);
    }
    
    public void addWidgetAttributes(Set<WidgetAttribute> widgetAttributes) {
        for (WidgetAttribute widgetAttribute : widgetAttributes) {
        	widgetAttributeRepository.save(widgetAttribute);
        }
    }

    public void addListing(MultipartFile coverImage, MultipartFile[] files, Long qty, MarketListing marketListing) {
        try {
            // Save the cover image
            String coverImageName = saveImage(coverImage);
            marketListing.setCoverImage(coverImageName);

            // Save additional images
            for (MultipartFile file : files) {
                String imageName = saveImage(file);
                WidgetImage widgetImage = new WidgetImage();
                widgetImage.setImageName(imageName);
                widgetImage.setMarketListing(marketListing);
                widgetImageRepository.save(widgetImage);
            }

            // Set the quantity available for the listing
            marketListing.setQtyAvailable(qty);

            // Save the market listing
            marketListingRepos.save(marketListing);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetPath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), targetPath);
        return fileName;
    }

}
