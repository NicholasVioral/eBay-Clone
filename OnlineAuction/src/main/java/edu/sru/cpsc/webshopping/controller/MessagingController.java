package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.OfferNotification;
import edu.sru.cpsc.webshopping.domain.misc.SocialMessage;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;
import edu.sru.cpsc.webshopping.repository.market.OfferNotificationRepository;
import edu.sru.cpsc.webshopping.repository.misc.MessageSocialRepository;
import edu.sru.cpsc.webshopping.service.MessageService;
import edu.sru.cpsc.webshopping.service.NotificationService;
import edu.sru.cpsc.webshopping.service.UserService;

/**
 * Controller for messaging and offer notifications
 * @author Tiffany
 */

@Controller
public class MessagingController {
	
	Logger log = LoggerFactory.getLogger(MessagingController.class);
	
    private MessageService messageService;
    private MessageSocialRepository messageSocialRepository;
    private NotificationService notificationService;
    private StatisticsDomainController statControl;
    private UserService userService;
    private MarketListingRepository marketListingRepository;
    private OfferNotificationRepository notificationRepository;
    private User internalMessaging;
    
    @Autowired
    public MessagingController(MessageService messageService, UserService userService, NotificationService notificationService, StatisticsDomainController statControl, MessageSocialRepository messageSocialRepository, MarketListingRepository marketListingRepository, OfferNotificationRepository notificationRepository) {
    	this.messageService = messageService;
    	this.userService = userService;
    	this.notificationService = notificationService;
    	this.statControl = statControl;
    	this.messageSocialRepository = messageSocialRepository;
    	this.marketListingRepository = marketListingRepository;
    	this.notificationRepository = notificationRepository;
    	
//    	internalMessaging = userService.getUserByUsername("CustomerService");
    }
    
    /**
     * Method for sending messages to sellers on offers they are being sent
     * @param id
     * @param listingId
     * @param content
     * @param offerAmount
     * @param principal
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/messageSeller", method = RequestMethod.POST)
    public String sendMessage(
    		@RequestParam long id, 
    		@RequestParam long listingId, 
    		@RequestParam(required = false) String content, 
    		@RequestParam(required = false) String offerAmount, 
    		Principal principal, 
    		RedirectAttributes redirectAttributes
		) {
    	
    	User potentialBuyer = userService.getUserByUsername(principal.getName());
    	
    	MarketListing listing = marketListingRepository.findById(listingId).get();
    	String listingName = listing.getWidgetSold().getName();
    	
    	SocialMessage message = new SocialMessage();
    	
    	
    	if (offerAmount != null) {
    		message.setContent(principal.getName() + " has sent you an offer of $" + offerAmount + " for the listing " + listingName + ". " + "You can view your offers on the listing page.");
    		OfferNotification notification = new OfferNotification(principal.getName(), potentialBuyer.getId(), offerAmount, listingId);
    		notificationRepository.save(notification);
    		
    		redirectAttributes.addFlashAttribute("offerSentSuccess", true);
		}
    	
    	if (content != null) {
    		message.setContent("Message recieved about listing " + listingName + ". " + content);
    		redirectAttributes.addFlashAttribute("messageSentSuccess", true);
		}
    	
    	if (message.getContent() == null) {
    		redirectAttributes.addFlashAttribute("failedToSendOffer", true);
    		return "redirect:/viewMarketListing/" + listingId;
    	}
    	
    	User user = userService.getUserByUsername(principal.getName());
    	message.setSender(user);
    	message.setReceiver(userService.getUserById(id));
    	messageService.saveMessage(message);
    	
    	Statistics stats = new Statistics(StatsCategory.MESSAGES, 1);
        stats.setDescription(principal.getName() + " messaged " + message.getReceiver().getId() + ": " + message.getContent());
        statControl.addStatistics(stats);

        // notificationService.createNotification(message.getReceiver().getId(), principal.getName() + " messaged you about " + listingName + "!");
        
    	return "redirect:/viewMarketListing/" + listingId;
    } 
    
    /**
     * Notifies the potential buyer if their offer was accepted
     * @param offer
     * @param itemName
     */
    public void notifyOfferAccepted(OfferNotification offer, String itemName) {
    	log.info("OFFER ACCEPTED IN THE AMOUNT OF: " + offer.getOfferAmount());
    	User potentialBuyer = userService.getUserById(offer.getPotentialBuyerUserId());
    	
    	SocialMessage message = new SocialMessage();
    	
    	message.setSender(userService.getUserByUsername("CustomerService"));
//    	message.setSender(internalMessaging);
    	message.setReceiver(potentialBuyer);
    	
    	String messageBody = "Your offer for " + itemName + " for the amount of $" + offer.getOfferAmount() + " has been accepted! Navigate to the page listing to buy now.";
    	message.setContent(messageBody);
    	
    	messageService.saveMessage(message);
    }
    
    /**
     * Notifies the potential buyer if their offer was rejected
     * @param offer
     * @param itemName
     */
    public void notifyOfferRejection(OfferNotification offer, String itemName) {
    	User potentialBuyer = userService.getUserById(offer.getPotentialBuyerUserId());
    	
    	SocialMessage message = new SocialMessage();
    	
    	message.setSender(userService.getUserByUsername("CustomerService"));
//    	message.setSender(internalMessaging);
    	message.setReceiver(potentialBuyer);
    	
    	String messageBody = "Your offer for " + itemName + " for the amount of $" + offer.getOfferAmount() + " has been rejected. This is an automated message please do not respond.";
    	message.setContent(messageBody);
    	
    	messageService.saveMessage(message);
    }
}


