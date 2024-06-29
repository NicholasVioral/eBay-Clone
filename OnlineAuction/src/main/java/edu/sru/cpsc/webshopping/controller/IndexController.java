package edu.sru.cpsc.webshopping.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.secure.TwoFactorAuthentication;
import edu.sru.cpsc.webshopping.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
  @Autowired
  private UserService userService;
  
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TwoFactorAuthentication twoFactorAuth;

  @Autowired
  private ApplicantRepository appRepo;

  WidgetController widgetController;
  MarketListingDomainController marketController;

  // Mapping for the /index URL when initiated through Tomcat
//  @RequestMapping({"/index"})
//  public String showUserList(Model model) {
//    return "BrowseWidgetsButtons";
//  }
//
//  @RequestMapping({"/"})
//  public String showIndex(Model model) {
//    return "BrowseWidgetsButtons";
//  }

  @RequestMapping({"/login"})
  public String showLoginPage() {
    return "login";
  }

  @RequestMapping({"/missionStatement"})
  public String showMission() {
    return "missionStatement";
  }

  @RequestMapping({"/FAQ"})
  public String showFAQ() {
    return "FAQ";
  }

  @RequestMapping({"/application"})
  public String showApplication(Model model) {
    Applicant applicant = new Applicant();

    model.addAttribute("applicant", applicant);
    return "application";
  }
  
//  @RequestMapping({"/contactUs"})
//  public String showContactUs(Model model) {
//    return "contactUs";
//  }

  @PostMapping("/apply")
  public String addApplication(@Valid Applicant applicant, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "application";
    }
    appRepo.save(applicant);
    return "redirect:index";
  }

  @PostMapping({"/verify2FA"})
  public String verify2FA(@RequestParam String code, HttpServletRequest request) {
    String username = (String) request.getSession().getAttribute("tempUsername");
    if (username == null) {
        return "redirect:/login";
    }

    User user = userRepository.findByUsername(username);
    if (twoFactorAuth.verify2FACode(user, code)) {
        request.getSession().removeAttribute("tempUsername"); // Clear the temporary attribute
        // Complete the authentication and redirect
        return "redirect:/homePage";
    }

    return "redirect:/verify2FAPage?error";
  }

  @RequestMapping({"/verify2FAPage"})
  public String showVerify2FAPage() {
    return "verify2FA";
  }
  
}
