package edu.sru.cpsc.webshopping.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import edu.sru.cpsc.webshopping.domain.billing.RefundTicket;
import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.market.Transaction;
import edu.sru.cpsc.webshopping.domain.misc.Notification;
import edu.sru.cpsc.webshopping.domain.misc.Revenue;
import edu.sru.cpsc.webshopping.domain.user.Applicant;
import edu.sru.cpsc.webshopping.domain.user.Message;
import edu.sru.cpsc.webshopping.domain.user.Statistics;
import edu.sru.cpsc.webshopping.domain.user.Statistics.StatsCategory;
import edu.sru.cpsc.webshopping.domain.user.Ticket;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Category;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;
import edu.sru.cpsc.webshopping.domain.Comment;
import edu.sru.cpsc.webshopping.domain.Post;
import edu.sru.cpsc.webshopping.repository.applicant.ApplicantRepository;
import edu.sru.cpsc.webshopping.repository.misc.NotificationRepository;
import edu.sru.cpsc.webshopping.repository.user.UserRepository;
import edu.sru.cpsc.webshopping.repository.widgets.WidgetRepository;
import edu.sru.cpsc.webshopping.service.CategoryService;
import edu.sru.cpsc.webshopping.service.NotificationService;
import edu.sru.cpsc.webshopping.service.RefundService;
import edu.sru.cpsc.webshopping.service.TicketService;
import edu.sru.cpsc.webshopping.service.TransactionService;
import edu.sru.cpsc.webshopping.service.UserService;
import edu.sru.cpsc.webshopping.service.CommentService;
import edu.sru.cpsc.webshopping.service.PostService;
import edu.sru.cpsc.webshopping.util.constants.TimeConstants;
import edu.sru.cpsc.webshopping.util.enums.MessageType;
import edu.sru.cpsc.webshopping.util.enums.Role;
import edu.sru.cpsc.webshopping.util.enums.TicketState;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {
  @Autowired
  UserService userService;
  
  @Autowired
  private NotificationRepository notificationRepository;
  
  @Autowired
  private NotificationService notificationService;
  
  @Autowired
  private RefundService refundService;
  
  @Autowired
  private TransactionService transactionService;
  
  @Autowired
  private RevenueController revenueController;
  
  @Autowired
  private CommentService commentService; 
  
  @Autowired
  private PostService postService;
  
  private final UserController userController;
  private final ApplicantDomainController appControl;
  private final MarketListingDomainController market;
  private final WidgetController widgetController;
  private final ApplicantRepository appRepo;
  private final EmailController email;
  private final StatisticsDomainController statControl;
  private final TicketService ticketService;
  private final UserRepository userRepository;
  private final EmailController emailController;
  private final WidgetRepository widgetRepository;
  private final CategoryController categories;
  @Autowired
  private CategoryService categoryService;
  private String page;
  private String mailboxPage;
  private String page2;
  private String page3;
  private String masterPage;
  private String pageSuccess;
  private User[] myUserSearch;
  private List<MarketListing> searchedUserListings = new ArrayList<>();
  private List<Widget> searchedUserWidgets = new ArrayList<>();
  private List<User> searchedUserSellers = new ArrayList<>();
  private List<User> allUsers = new ArrayList<>();
  private List<Widget> allWidgets = new ArrayList<>();
  private List<User> allSellers = new ArrayList<>();
  private List<MarketListing> allMarketListings = new ArrayList<>();
  private List<MarketListing> watchlistItems = new ArrayList<>();
  private List<Applicant> allApplicants = new ArrayList<>();
  private List<Applicant> reviewedApplicants = new ArrayList<>();
  private List<Applicant> newApplicants = new ArrayList<>();
  private List<Message> allMessages = new ArrayList<>();
  private int applicantPage;
  private User searchedUser;
  private Widget searchedWidget;
  private MarketListing searchedMarket;
  private User searchedSeller;
  private boolean singleWidgetCheck = false;
  public String search;
  List<String> roleList;
  

  @ModelAttribute
  public void preLoad(Model model) {
    roleList = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toList());
  }

  List<StatsCategory> CategoryList;

  @ModelAttribute
  public void preLoad2(Model model) {
    CategoryList = new ArrayList<>();

    CategoryList.add(StatsCategory.ACCOUNTCREATION);
    CategoryList.add(StatsCategory.ACCOUNTDELETED);
    CategoryList.add(StatsCategory.SALE);
    CategoryList.add(StatsCategory.SALEVALUE);
    CategoryList.add(StatsCategory.USERLOGIN);
    CategoryList.add(StatsCategory.MESSAGES);
    CategoryList.add(StatsCategory.WIDGETCREATED);
  }

  List<String> dateList;

  @ModelAttribute
  public void preLoad3(Model model) {
    dateList = new ArrayList<>();

    dateList.add("Today");
    dateList.add("Week to date");
    dateList.add("Month to date");
    dateList.add("Year to date");
    dateList.add("Yesterday");
    dateList.add("Previous Week");
    dateList.add("Previous Month");
    dateList.add("Previous Year");
  }

  @RequestMapping({"/statButton"})
  public String statButton(Model model, Principal principal) {

    String username = principal.getName();
    User user = userService.getUserByUsername(username);
    if (user.getRole().equals("ROLE_SALES")) {
      setPage("statistics");
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    User useradd = new User();
    model.addAttribute("dateList", dateList);
    model.addAttribute("catList", CategoryList);
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("page2", getPage2());
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @RequestMapping({"/lookupStatistics"})
  public String StatisticsButton(@RequestParam String date, @RequestParam StatsCategory category, Model model, Principal principal) {
    String username = principal.getName();
    User user = userService.getUserByUsername(username);
    if (user.getRole().equals("ROLE_SALES")) {
      setPage2("stats");
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    /*SALEVALUE,
    SALE,
    ACCOUNTCREATION,
    USERLOGIN,
    ACCOUNTDELETED,
    MESSAGESENT,
    WIDGETCREATED;*/
    LocalDateTime date1 = LocalDateTime.now();
    // System.out.println(date);
    LocalDateTime date2 = LocalDateTime.now();
    // System.out.println(category);
    if (date.equals("Today")) {
      date2 = date1;
    }
    if (date.equals("Week to date")) {
      date2 = date1;
      DayOfWeek today = date1.getDayOfWeek();
      while (today != DayOfWeek.SUNDAY) {
        date1 = date1.minusDays(1);
        today = date1.getDayOfWeek();
      }
    }
    if (date.equals("Month to date")) {
      date2 = date1;
      while (date1.getDayOfMonth() != 1) {
        date1 = date1.minusDays(1);
      }
    }
    if (date.equals("Year to date")) {
      date2 = date1;
      while (date1.getDayOfYear() != 1) {
        date1 = date1.minusDays(1);
      }
    }
    if (date.equals("Yesterday")) {

      date1 = date1.minusDays(1);
      date2 = date1;
    }
    if (date.equals("Previous Week")) {

      DayOfWeek today = date1.getDayOfWeek();
      while (today != DayOfWeek.SUNDAY) {
        date1 = date1.minusDays(1);
        today = date1.getDayOfWeek();
      }
      date2 = date1.minusDays(1);
      date1 = date2.minusDays(6);
    }
    if (date.equals("Previous Month")) {
      date1 = date1.minusMonths(1);
      while (date1.getDayOfMonth() != 1) {
        date1 = date1.minusDays(1);
      }
      date2 = date1;
      while (true) {
        date2 = date2.plusDays(1);
        if (date2.getMonthValue() != date1.getMonthValue()) {
          date2 = date2.minusDays(1);
          break;
        }
      }
    }
    if (date.equals("Previous Year")) {
      date1 = date1.minusYears(1);
      while (date1.getDayOfYear() != 1) {
        date1 = date1.minusDays(1);
      }
      date2 = date1;
      while (true) {
        date2 = date2.plusDays(1);
        if (date2.getYear() != date1.getYear()) {
          date2 = date2.minusDays(1);
          break;
        }
      }
    }

    // Statistics[] stats = statControl.getStatisticsByHour(date1, date2, Category.ACCOUNTCREATION);
    // Statistics[] stats = statControl.getStatsByCategory(Category.ACCOUNTCREATION);
    Statistics[] stats = statControl.getStatsByCategory(category);

    List<Statistics> resultStats = new ArrayList<>();

    for (LocalDateTime day = date1;
        day.getDayOfYear() <= date2.getDayOfYear();
        day = day.plusDays(1)) {
      /*
      System.out.println(day.getDayOfYear());
      System.out.println(date2.getDayOfYear());
      System.out.println(day.getYear());
      System.out.println(date2.getYear());*/
      for (int i = 0; i < stats.length; i++) {
        if (stats[i].getDate().getYear() == day.getYear()
            && stats[i].getDate().getDayOfYear() == day.getDayOfYear()) {
          resultStats.add(stats[i]);
        }
      }
      if (date.equals("Previous Year") && day.getDayOfYear() == date2.getDayOfYear()) {
        break;
      }
    }

    Statistics[] statsValues = resultStats.toArray(new Statistics[resultStats.size()]);
    float total = 0;
    for (int i = 0; i < statsValues.length; i++) {
      total += statsValues[i].getValue();
    }
    model.addAttribute("total", total);
    model.addAttribute("date", date);
    model.addAttribute("cat", category);
    model.addAttribute("dateList", dateList);
    model.addAttribute("catList", CategoryList);
    model.addAttribute("allStats", resultStats);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("page2", getPage2());
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @RequestMapping({"/employee"})
  public String showEmployeePage(Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    String[] allusernames = new String[getAllUsers().size()];
    setMasterPage("base");
    if (getMasterPage().equals("base")) {
      setPage("begin");

      model.addAttribute("page", page);
    }
    model.addAttribute("masterPage", getMasterPage());
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);

    User useradd = new User();
    if (user.getRole().contains("ROLE_USER")) {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    model.addAttribute("user", user);
    model.addAttribute("useradd", useradd);
    model.addAttribute("roleList", roleList);
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @RequestMapping({"/backButton"})
  public String backButton(Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    String[] allusernames = new String[getAllUsers().size()];

    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());

    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    if (user.getRole().contains("ROLE_USER")) {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    model.addAttribute("widgetNames", allWidgetNames);
    model.addAttribute("dateList", dateList);
    model.addAttribute("catList", CategoryList);
    model.addAttribute("allMarketListings", getSearchedUserListings());
    model.addAttribute("allWidgets", getSearchedUserWidgets());
    model.addAttribute("allSellers", getSearchedUserSellers());
    model.addAttribute("myusers", getMyUserSearch());
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page2", getPage2());
    model.addAttribute("page", getPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("masterPage", getMasterPage());
    return "employee";
  }

  @PostMapping({"/createEmployee"})
  public String createEmp(
      @Valid User useradd, BindingResult result, @RequestParam String role, Model model, Principal principal) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user2 = userService.getUserByUsername(principal.getName());

    if (user2.getRole().equals("ROLE_ADMIN") || user2.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user2);
      model.addAttribute("page", page);
      return "employee";
    }
    if (userController.getUserByUsername(useradd.getUsername()) != null) {
      result.addError(new FieldError("username", "username", "Username is already taken."));
    }
    if (userController.getUserByEmail(useradd.getEmail()) != null) {
      result.addError(
          new FieldError("email", "email", "An account is already associated with this email."));
    }

    if (result.hasErrors()) {
      setPage2("createFail");
      List<String> errorString = new ArrayList<>();
      result.getAllErrors().forEach(u -> errorString.add(u.getDefaultMessage() + " "));
      model.addAttribute("createStringError", errorString);
      model.addAttribute("user", user2);
      model.addAttribute("page", getPage());
      model.addAttribute("page2", getPage2());
      model.addAttribute("useradd", useradd);
      model.addAttribute("roleList", roleList);
      
      return "employee";
    }
    useradd.setEnabled(true);
    userController.addUser(useradd, result);

    setPage2("create");
    
    // log event
    StatsCategory cat = StatsCategory.ACCOUNTCREATION;
    Statistics stat = new Statistics(cat, 1);
    stat.setDescription(user2.getUsername() + " created an account, " + useradd.getUsername() + ", with role: " + useradd.getRole());
    statControl.addStatistics(stat);
    
    model.addAttribute("user", user2);
    model.addAttribute("page2", getPage2());
    model.addAttribute("page", getPage());
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", new User());
    return "employee";
  }
  

  @RequestMapping("/postLayout")
  public String showPostLayout(Model model, Principal principal) {
	  model.addAttribute("masterPage", "query");
	  model.addAttribute("page", "postLayout");
	  
      if (principal != null) {
          User user = userService.getUserByUsername(principal.getName());
          model.addAttribute("user", user);
      }
      // For the form to add a new post
      model.addAttribute("post", new Post()); 
      // Load all posts and their comments
      var posts = postService.findAllWithComments();

      model.addAttribute("posts", posts);
      model.addAttribute("comment", new Comment()); // For the comment form

      return "employee";
  }

  
   @RequestMapping("/admin/delete/{postId}") 
   public String admindeletePost(@PathVariable Long postId, Model model, Principal principal){
   	postService.deletePostById(postId);
   	
   	return "redirect:/postLayout";
   }
   
   @RequestMapping("/admin/archive/{postId}") 
   public String adminarchivePost(@PathVariable Long postId, Model model, Principal principal){
   	postService.toggleArchivePostById(postId);
   	
   	return "redirect:/postLayout";
   }
   
   @RequestMapping("/admin/deleteOldPosts")
   public String deleteOldPosts(@RequestParam("days") int days, Model model, Principal principal) {
       // Calculate the cut-off date
       LocalDate cutoffDate = LocalDate.now().minusDays(days);
       Date cutoff = Date.from(cutoffDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

       // Delegate to the service to delete old posts
       postService.deletePostsNotCommentedSince(cutoff);

       return "redirect:/postLayout";
   }

  @RequestMapping({"/createButton"})
  public String createButton(Model model, Principal principal) {
    setPage("create");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    
    if(user.getRole().equals("ROLE_ADMIN") || user.getRole().equals("ROLE_SUPERADMIN")) {
    	User useradd = new User();
        model.addAttribute("roleList", roleList);
        model.addAttribute("useradd", useradd);
        model.addAttribute("user", user);
        model.addAttribute("page", page);
        return "employee";
    }
    
    setPage("notAuthorized");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
    
  }

  @RequestMapping({"/cancelButton"})
  public String cancelButton(Model model, Principal principal) {
    setPage("null");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());

    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @RequestMapping({"/searchButton"})
  public String searchButton(Model model, Principal principal) {

    setPage("search");
    setMasterPage("query");

    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllUsers().clear();
    Iterable<User> allUsersIterator = userController.getAllUsers();

    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }
    User useradd = new User();
    setMyUserSearch(getAllUsers().toArray(new User[getAllUsers().size()]));
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @RequestMapping({"/searchWidgetButton"})
  public String searchWidgetButton(Model model, Principal principal) {

    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllMarketListings().clear();
    getAllWidgets().clear();
    getAllSellers().clear();
    Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
    allMarketListingsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));

    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }
    setSearchedUserListings(getAllMarketListings());
    setSearchedUserWidgets(getAllWidgets());
    setSearchedUserSellers(getAllSellers());
    model.addAttribute("widgetNames", allWidgetNames);
    setPage("searchWidgetSellerMarketListing");
    model.addAttribute("allMarketListings", getAllMarketListings());
    model.addAttribute("allWidgets", getAllWidgets());
    model.addAttribute("allSellers", getAllSellers());
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "employee";
  }

  @GetMapping({"/widgetsInfo"})
  public String widgetsInfo(Model model, Principal principal) {

    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      model.addAttribute("page", getPage());
      model.addAttribute("user", user);
      return "employee";
    }
    setPage("widgetsInfo");
    model.addAttribute("page", getPage());
    model.addAttribute("user", user);
    model.addAttribute("categories", categories.getAllCategories());
    return "employee";
  }

  
  // https://attacomsian.com/blog/export-download-data-csv-file-spring-boot
  @GetMapping({"/downloadDataFile"})
  public void downloadDataFile(HttpServletResponse response, @RequestParam Category Category)
      throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

    List<Widget> widgets = widgetRepository.findByCategory(Category);

    /*switch (subCategory) {
      case "car_parts":
        widgets =
            widgets.stream().filter(Vehicle_Car.class::isInstance).collect(Collectors.toList());
        break;
    }*/

    String filename = String.format("%s.csv", Category.getName());

    response.setContentType("text/csv");
    response.setHeader(
        HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

    StatefulBeanToCsv<Widget> writer =
        new StatefulBeanToCsvBuilder<Widget>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

    writer.write(widgets);
  }
  
//  @RequestMapping({"", "/", "/index", "/BrowseWidgetsButton"})
//  public String browseWidgetsButton(Model model, Principal principal) {
//    User user;
//    if(principal == null) {
//      user = null;
//    } else {
//      user = userService.getUserByUsername(principal.getName());
//    }
//
//    setPage("searchWidgetSellerMarketListing");
//    setMasterPage("query");
//    model.addAttribute("masterPage", getMasterPage());
//    getAllMarketListings().clear();
//    getAllWidgets().clear();
//    getAllSellers().clear();
//    getAllUsers().clear();
//
//    Iterable<User> allUsersIterator = userController.getAllUsers();
//    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));
//
//    Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();
//
//    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
//    allMarketListingsIterator
//        .iterator()
//        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
//    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));
//
//    model.addAttribute("users", getAllUsers());
//    model.addAttribute("allMarketListings", getAllMarketListings());
//    model.addAttribute("allWidgets", getAllWidgets());
//    model.addAttribute("allSellers", getAllSellers());
//    model.addAttribute("page", getPage());
//    model.addAttribute("user", user);
//    
//    return "browseWidgets";
//  }

// Controller for the watchlist
  @RequestMapping({"/Watchlist"})
  public String watchlist(Model model, Principal principal) {

    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    setPage("watchlistPage");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    
    getAllMarketListings().clear();
    getAllWidgets().clear();
    getAllSellers().clear();
    getAllUsers().clear();

    Iterable<User> allUsersIterator = userController.getAllUsers();
    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));

    Iterable<MarketListing> allWatchlistItemsIterator = getWatchlistItems();
    
    allWatchlistItemsIterator.iterator().forEachRemaining(u -> getWatchlistItems().add(u));
    allWatchlistItemsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
    allWatchlistItemsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));

    allWatchlistItemsIterator.iterator().forEachRemaining(u -> getWatchlistItems());
    allWatchlistItemsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets());
    allWatchlistItemsIterator.iterator().forEachRemaining(u -> getAllSellers());
    
    // log event
    StatsCategory cat = StatsCategory.WATCHLIST;
    Statistics stat = new Statistics(cat, 1);
    stat.setDescription(user.getUsername() + " viewed their watchlist");
    statControl.addStatistics(stat);

    model.addAttribute("users", getAllUsers());
    model.addAttribute("allWatchlistItems", user.getWishlistedWidgets());
    model.addAttribute("allWidgets", getAllWidgets());
    model.addAttribute("allSellers", getAllSellers());
    model.addAttribute("page", getPage());
    model.addAttribute("user", user);
    
    return "watchlist"; // return watchlist.html
  }

  @RequestMapping({"/searchMessageButton"})
  public String searchMessageButton(Model model, Principal principal) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    setPage("searchMessage");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllUsers().clear();
    Iterable<User> allUsersIterator = userController.getAllUsers();

    allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));

    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("roleList", roleList);
    model.addAttribute("user", user);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("page", page);
    return "employee";
  }

  @RequestMapping({"/searchApplicationsButton"})
  public String searchApplicationsButton(@RequestParam("id") int id, Model model, Principal principal) {
    setPage("applications");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_HIRINGMANAGER")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    getAllApplicants().clear();
    getReviewedApplicants().clear();
    getNewApplicants().clear();
    Iterable<Applicant> applicantIterator = appRepo.findAll();

    applicantIterator.iterator().forEachRemaining(u -> getAllApplicants().add(u));

    Applicant[] allApplications =
        getAllApplicants().toArray(new Applicant[getAllApplicants().size()]);
    for (int i = 0; i < allApplications.length; i++) {
      if (allApplications[i].getReviewed() != null) {
        if (allApplications[i].getReviewed() == true) {
          getReviewedApplicants().add(allApplications[i]);
        } else {
          getNewApplicants().add(allApplications[i]);
        }
      } else {
        getNewApplicants().add(allApplications[i]);
      }
    }
    if (id == 1) {
      model.addAttribute("applicants", getNewApplicants());
      setApplicantPage(id);
    } else {
      model.addAttribute("applicants", getReviewedApplicants());
      setApplicantPage(id);
    }
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    return "employee";
  }

  @GetMapping({"/searchTickets"})
  public String searchTickets(Model model, Principal principal) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    Iterable<Ticket> tickets = ticketService.getAllTickets();
    Iterable<RefundTicket> refunds = refundService.getAllRefunds();
    model.addAttribute("refundTickets", refunds);
    setPage("ticket");
    model.addAttribute("tickets", tickets);

    List<User> users =
        StreamSupport.stream(userController.getAllUsers().spliterator(), true)
            .filter(user1 -> !user1.getRole().equals(Role.ROLE_USER.name()))
            .collect(Collectors.toList());
    model.addAttribute("users", users);

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "employee";
  }

  @GetMapping({"/searchTickets/{id}"})
  public String searchTickets(@PathVariable("id") Long id, Model model, Principal principal) {
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    Ticket ticket = ticketService.getTicketById(id);
    setPage("ticketdetail");
    model.addAttribute("ticket", ticket);
    model.addAttribute("message", new Message());

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "employee";
  }

  @PostMapping("/assignTicket/{id}")
  public String getTicketsPage(
      @PathVariable Long id, @RequestParam(value = "username") String userName, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    User user1 = userRepository.findByUsername(userName);
    Ticket ticket = ticketService.getTicketById(id);
    ticket.setAssignedTo(user1);
    ticket.setState(TicketState.UNANSWERED);
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticket.setAssignedAt(ticket.getUpdatedAt());
    ticketService.save(ticket);

    emailController.updateTicketStatus(user, ticket, "assign");

    Iterable<Ticket> tickets = ticketService.getAllTickets();
    setPage("ticket");
    model.addAttribute("tickets", tickets);
    model.addAttribute("userName", "");

    List<User> users =
        StreamSupport.stream(userController.getAllUsers().spliterator(), true)
            .filter(user2 -> !user2.getRole().equals(Role.ROLE_USER.name()))
            .collect(Collectors.toList());
    model.addAttribute("users", users);

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "redirect:/searchTickets";
  }
  
  @PostMapping("/assignRefundTicket/{id}")
  public String assignRefundTicket(
          @PathVariable Long id, 
          @RequestParam(value = "assignedTo") String userName, 
          Model model, 
          Principal principal) {
      User user = userService.getUserByUsername(principal.getName());
      model.addAttribute("user", user);

      // Retrieve the user to be assigned to the refund ticket
      User assignee = userRepository.findByUsername(userName);

      // Retrieve and update the refund ticket
      RefundTicket refundTicket = refundService.getRefundTicketById(id);
      refundTicket.setAssignedTo(assignee);
      refundTicket.setState(TicketState.UNANSWERED); // Assuming similar state exists for refund tickets
      refundTicket.setUpdatedAt(LocalDateTime.now());
      refundTicket.setAssignedAt(refundTicket.getUpdatedAt());
      refundService.save(refundTicket);

      // Fetch and add updated list of refund tickets to the model
      Iterable<RefundTicket> refundTickets = refundService.getAllRefunds();
      model.addAttribute("refundTickets", refundTickets);

      // Fetch and add list of users to the model, filtering out ROLE_USER
      List<User> users = StreamSupport.stream(userController.getAllUsers().spliterator(), true)
              .filter(user2 -> !user2.getRole().equals(Role.ROLE_USER.name()))
              .collect(Collectors.toList());
      model.addAttribute("users", users);

      return "redirect:/searchTickets";
  }

  @GetMapping("/refundTickets/{id}")
  public String refundTicketDetails(@PathVariable("id") Long id, Model model, Principal principal) {
      User user = userService.getUserByUsername(principal.getName());
      model.addAttribute("user", user);

      // Role check similar to searchTickets method
      if (user.getRole().equals("ROLE_ADMIN")
          || user.getRole().equals("ROLE_CUSTOMERSERVICE")
          || user.getRole().equals("ROLE_TECHNICALSERVICE")
          || user.getRole().equals("ROLE_SECURITY")
          || user.getRole().equals("ROLE_ADMIN_SHADOW")
          || user.getRole().equals("ROLE_HELPDESK_ADMIN")
          || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
          // User is authorized, proceed with fetching refund ticket details
          RefundTicket refundTicket = refundService.getRefundTicketById(id);
          model.addAttribute("refundTicket", refundTicket);
          setPage("refundTicketDetail"); // Set the page for refund ticket detail
      } else {
          // User is not authorized
          setPage("notAuthorized");
      }
      
      RefundTicket refundTicket = refundService.getRefundTicketById(id);
      model.addAttribute("refundTicket", refundTicket);
      System.out.println("Refund ticket state: " + refundTicket.getState());

      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("page", "refundTicketDetail");

      return "employee"; // Assuming the same view name
  }

  @PostMapping("/reply/{id}")
  public String getTicketsPage(
      @PathVariable Long id, @ModelAttribute Message message, Model model, Principal principal, RedirectAttributes redirectAttributes) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = ticketService.findById(id).get();
    if (ticket.getAssignedTo() != null) {
      ticket.setState(TicketState.ANSWERED);
    } else {
      ticket.setState(TicketState.UNASSIGNED);
    }
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    message.setMessageType(MessageType.TICKET);
    message.setSender(user.getUsername());
    message.setMsgDate();
    ticket.addMessage(message);
    ticketService.save(ticket);

    emailController.updateTicketStatus(user, ticket, "reply");
    	
    model.addAttribute("ticket", ticket);
    model.addAttribute("message", new Message());

    setPage("ticketdetail");

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());  
    User ticketUser = ticket.getCreatedBy();
    if (ticketUser != null) {
        Long userId = ticketUser.getId();
        notificationService.createNotification(userId, "Tech Support has replied to Ticket ID: " + ticket.getId());
    } else {
        System.out.println("No luck with notifications");  
    }
    
    return "redirect:/searchTickets/" + id;
  }
  
  @PostMapping("/acceptRefundTicket")
  public String acceptRefundTicket(@RequestParam("refundTicketId") Long refundTicketId, @RequestParam("fullRefund") boolean fullRefund, RedirectAttributes redirectAttributes) {
      RefundTicket refundTicket = refundService.getRefundTicketById(refundTicketId);
      if (refundTicket != null) {
          refundTicket.setState(TicketState.APPROVED);
          refundService.save(refundTicket);

          Transaction transaction = refundTicket.getTransaction();
          if (transaction != null) {
              transaction.setRefundStatus(Transaction.RefundStatus.APPROVED);
              transactionService.save(transaction);

              // Create a notification for the user
              User ticketUser = transaction.getBuyer();
              if (ticketUser != null) {
                  String notificationMessage = String.format("Refund has been accepted for %s. Refund Amount: $%.2f",
                                                             transaction.getMarketListing().getWidgetSold().getName(),
                                                             refundTicket.getTotalPriceAfterTaxes());
                  notificationService.createNotification(ticketUser.getId(), notificationMessage);
                  
                  /* remove revenue from system */
                  Revenue revenue = Revenue.getInstance();
                  float value = revenueController.calculateRevenueEarned(transaction);
          		  revenue.removeRevenue(value);
              }
          }
      }
      return "redirect:/searchTickets";
  }

  @PostMapping("/rejectRefundTicket")
  public String rejectRefundTicket(@RequestParam("refundTicketId") Long refundTicketId, RedirectAttributes redirectAttributes) {
      RefundTicket refundTicket = refundService.getRefundTicketById(refundTicketId);
      if (refundTicket != null) {
          refundTicket.setState(TicketState.REJECTED);
          refundService.save(refundTicket);

          Transaction transaction = refundTicket.getTransaction();
          if (transaction != null) {
              transaction.setRefundStatus(Transaction.RefundStatus.REJECTED);
              transactionService.save(transaction);

              // Create a notification for the user
              User ticketUser = transaction.getBuyer();
              if (ticketUser != null) {
                  String notificationMessage = String.format("Refund has been rejected for %s.",
                                                             transaction.getMarketListing().getWidgetSold().getName());
                  notificationService.createNotification(ticketUser.getId(), notificationMessage);
              }
          }
      }
      return "redirect:/searchTickets";
  }

  @PostMapping("/resolveTicket/{id}")
  public String resolveTicket(@PathVariable Long id, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    model.addAttribute("user", user);

    Ticket ticket = ticketService.getTicketById(id);
    ticket.setState(TicketState.RESOLVED);
    ticket.setUpdatedAt(LocalDateTime.now().format(TimeConstants.DATE_TIME_FORMATTER));
    ticket.setResolvedAt(ticket.getUpdatedAt());
    ticketService.save(ticket);

    emailController.updateTicketStatus(user, ticket, "resolve");

    Iterable<Ticket> tickets = ticketService.getAllTickets();
    setPage("ticket");
    model.addAttribute("tickets", tickets);
    model.addAttribute("userName", "");

    List<User> users =
        StreamSupport.stream(userController.getAllUsers().spliterator(), true)
            .filter(anotherUser -> !anotherUser.getRole().equals(Role.ROLE_USER.name()))
            .collect(Collectors.toList());
    model.addAttribute("users", users);

    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    return "redirect:/searchTickets";
  }
  

  @GetMapping({"/editUserButton/{id}"})
  public String editUserButton(@PathVariable("id") int id, Model model, Principal principal) {
    User user = userService.getUserByUsername(principal.getName());
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    setPage2("user");
    User user2 = userController.getUser(id, model);
    setSearchedUser(user2);
    User useradd = new User();

    model.addAttribute("users", getAllUsers());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("roleList", roleList);
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("page2", page2);
    model.addAttribute("myusers", getMyUserSearch());
    model.addAttribute("page", "changeInfo");
    return "employee";
  }

  /*
   * method for admins to modify user account
   * params  account information
   */
  @RequestMapping({"/editUser"})
  public String editUser(@RequestParam("id") Long id, @RequestParam("userName") String userName, @RequestParam("password") String password,
	  @RequestParam("passwordConf") String passwordConf, @RequestParam("email") String email, @RequestParam("fName") String fName,
      @RequestParam("lName") String lName, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("cc") String cc, @RequestParam("role") String role,
      @RequestParam("emailVerification") String emailVerification, @RequestParam("dispName") String dispName, @RequestParam("userDesc") String userDesc,
      @RequestParam("creationDate") String creationDate, @RequestParam("status") String status, Model model, Principal principal) {
	setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
        
    // check for admin account
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      model.addAttribute("roleList", roleList);
      
      return "employee";
    }

    User editUser = userController.getUser(id, model); // user account to be modified
    
    boolean acctStatus = status.equals("enabled"); // sets status to true or false to correlate to enabled or disabled
    String oldPass = editUser.getPassword(); // old password used for comparison in user controller's editUser

    // make changes to the account based on parameters
    editUser.setUsername(userName);
    
    // only change the password if the admin entered a new password NOT WORKING
    if (password != null && !password.isEmpty()) {
    	editUser.setPassword(password);
    	System.out.println("password cond hit");
    }
    
    editUser.setPasswordconf(passwordConf);    
    editUser.setEmail(email);
    editUser.setFirstName(fName);
    editUser.setLastName(lName);
    editUser.setPhoneNumber(phoneNumber);
    editUser.setCountryCode(cc);
    System.out.println("ROLE " + role);
    editUser.setRole(role);
    editUser.setEmailVerification(emailVerification);
    editUser.setDisplayName(dispName);
    editUser.setUserDescription(userDesc);
    editUser.setCreationDate(creationDate);
    editUser.setEnabled(acctStatus); // lock or unlock account
    
    // error handling
    List<String> errorString = new ArrayList<>();
    if (userName.length() < 6 || userName.length() > 30) {
      errorString.add(" Enter username between 6-30 characters ");
    }
    if (fName.length() < 1 || fName.length() > 200) {
      errorString.add(" Enter first name between 1-200 characters ");
    }
    if (lName.length() < 6 || lName.length() > 30) {
      errorString.add(" Enter last name between 1-200 characters ");
    }
    if (email.length() < 6 || email.length() > 10) {
      errorString.add(" Enter valid email address between 6-100 characters ");
    }
    if (phoneNumber.length() < 1 || phoneNumber.length() > 20) {
      errorString.add(" Enter valid phone-number ");
    }
    if (passwordConf.length() < 6 || passwordConf.length() > 200) {
      errorString.add(" Enter a password confirmation between 6-200 characters ");
    }
    
    try {
      userController.editUser(editUser, oldPass);
      setPage3("editSuccess");
    } catch (Exception e) {

      model.addAttribute("error", errorString);
      setPage3("editFail");
    } finally {
      getAllUsers().clear();
      Iterable<User> allUsersIterator = userController.getAllUsers();

      allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));
      User useradd = new User();
      setSearchedUser(editUser);
      
      // log event
      StatsCategory cat = StatsCategory.EDITEDUSER;
      Statistics stat = new Statistics(cat, 1);
      stat.setDescription(user.getUsername() + " edited user information for: " + editUser.getUsername());
      statControl.addStatistics(stat);
            
      // model attributes
      model.addAttribute("users", getAllUsers());
      model.addAttribute("searchedUser", getSearchedUser());
      model.addAttribute("roleList", roleList);
      model.addAttribute("useradd", useradd);
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      model.addAttribute("page2", page2);
      model.addAttribute("page3", getPage3());
      model.addAttribute("myusers", getMyUserSearch());
    }
    
    return "employee";
  }

  @RequestMapping({"/editWidgetHere"})
  public String editWidget(
      @RequestParam("widgetId") String widgetId,
      @RequestParam("widgetName") String widgetName,
      @RequestParam("widgetDesc") String widgetDesc,
      @RequestParam("widgetCat") String widgetCat,
      @RequestParam("sellerName") String sellerName,
      @RequestParam("marketId") String marketId,
      @RequestParam("marketQty") String marketQty,
      @RequestParam("marketPrice") String marketPrice,
      Model model, Principal principal) {
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    User user2 = userService.getUserByUsername(principal.getName());
    if (user2.getRole().equals("ROLE_ADMIN")
        || user2.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user2.getRole().equals("ROLE_TECHNICALSERVICE")
        || user2.getRole().equals("ROLE_SUPERADMIN")) {
      System.out.println("here2");
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user2);
      model.addAttribute("page", page);
      return "employee";
    }

    try {

      setPage3("editWidgetSuccess");
      
      // TODO
      // - Make sure that category gets set up correctly  

      String[] widgetData = widgetId.split(",");
      Widget[] editWidgets = new Widget[widgetData.length];
      String[] widgetNameData = new String[widgetData.length];
      String[] widgetDescriptionData = new String[widgetData.length];
      String[] widgetCategoryData = new String[widgetData.length];
      widgetNameData = widgetName.split(",");
      widgetDescriptionData = widgetDesc.split(",");
      widgetCategoryData = widgetCat.split(",");

      String[] marketData = marketId.split(",");
      System.out.println(marketData.length);
      MarketListing[] editMarkets = new MarketListing[marketData.length];
      String[] sellerNameData = new String[marketData.length];
      String[] marketQtyData = new String[marketData.length];
      String[] marketPriceData = new String[marketData.length];
      sellerNameData = sellerName.split(",");
      marketQtyData = marketQty.split(",");
      marketPriceData = marketPrice.split(",");
      System.out.println(marketPriceData.length);
      getSearchedUserSellers().clear();
      getSearchedUserWidgets().clear();
      getSearchedUserListings().clear();
      for (int i = 0; i < widgetData.length; i++) {
        editWidgets[i] = widgetController.getWidget(Long.parseLong(widgetData[i], 10));
        editWidgets[i].setName(widgetNameData[i]);
        editWidgets[i].setDescription(widgetDescriptionData[i]);
        editWidgets[i].getCategory().setName((widgetCategoryData[i]));
        widgetController.addWidgetnobinding(editWidgets[i]);
        getSearchedUserWidgets().add(editWidgets[i]);
      }
      for (int i = 0; i < marketData.length; i++) {
        editMarkets[i] = market.getMarketListing(Long.parseLong(marketData[i], 10));
        User user = userController.getUserByUsername(sellerNameData[i]);
        editMarkets[i].setSeller(user);
        editMarkets[i].setQtyAvailable(Long.parseLong(marketQtyData[i], 10));
        if (!marketPriceData[i].isEmpty()) {
          editMarkets[i].setPricePerItem(NumberUtils.parseNumber(marketPriceData[i], BigDecimal.class));
          System.out.println(NumberUtils.parseNumber(marketPriceData[i], BigDecimal.class));
        }
        market.addMarketListingSimple(editMarkets[i]);
        getSearchedUserSellers().add(user);
        getSearchedUserListings().add(editMarkets[i]);
      }

    } catch (Exception e) {

      model.addAttribute("error", e.getMessage());
      setPage3("editWidgetFail");
    } finally {

      getAllUsers().clear();
      Iterable<User> allUsersIterator = userController.getAllUsers();

      allUsersIterator.iterator().forEachRemaining(u -> getAllUsers().add(u));

      getAllMarketListings().clear();
      getAllWidgets().clear();
      getAllSellers().clear();
      Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

      allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
      allMarketListingsIterator
          .iterator()
          .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
      allMarketListingsIterator
          .iterator()
          .forEachRemaining(u -> getAllSellers().add(u.getSeller()));

      if (!(isSingleWidgetCheck())) {
        model.addAttribute("sellers", getSearchedUserSellers());
        model.addAttribute("searchedWidgets", getSearchedUserWidgets());
        model.addAttribute("searchedMarkets", getSearchedUserListings());
      } else {

        model.addAttribute("sellers", getSearchedSeller());
        model.addAttribute("searchedWidgets", getSearchedWidget());
        model.addAttribute("searchedMarkets", getSearchedMarket());
      }

      setMailboxPage("searchMarketListing");
      model.addAttribute("mailpage", getMailboxPage());
      model.addAttribute("allMarketListings", getAllMarketListings());
      model.addAttribute("allWidgets", getAllWidgets());
      model.addAttribute("allSellers", getAllSellers());
      model.addAttribute("roleList", roleList);
      model.addAttribute("users", getAllUsers());
      model.addAttribute("user", user2);
      model.addAttribute("page", getPage());
      model.addAttribute("page3", getPage3());
      model.addAttribute("searchedUser", getSearchedUser());
      model.addAttribute("roleList", roleList);

      model.addAttribute("myusers", getMyUserSearch());
    }
    return "employee";
  }

  @RequestMapping({"/escalateApplication"})
  public String escalateApplication(
      @RequestParam("id") int id, @RequestParam("id2") int id2, Model model, Principal principal) {
    setPage("applications");
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    User user = userService.getUserByUsername(principal.getName());
    if (user.getRole().equals("ROLE_HIRINGMANAGER")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    Boolean accept = false;
    if (id2 == 1) {
      accept = true;
    }
    Applicant application = appControl.getApplicant(id);
    if (accept) {
      // email.applicationAccepted("tmd1021@sru.edu", application.getFirstName(),
      // application.getLastName()
      // , application.getPhoneNumber(), application.getRoleAppliedfor());
      email.applicationAccepted(
          application.getEmail(),
          application.getFirstName(),
          application.getLastName(),
          application.getPhoneNumber(),
          application.getRoleAppliedfor());

    } else {
      // email.applicationRejected("tmd1021@sru.edu", application.getFirstName(),
      // application.getLastName());
      email.applicationRejected(
          application.getEmail(), application.getFirstName(), application.getLastName());
    }
    setPageSuccess("reviewedApplication");
    application.setReviewed(true);
    appControl.addApplicantSimple(application);
    getAllApplicants().clear();
    getReviewedApplicants().clear();
    getNewApplicants().clear();
    Iterable<Applicant> applicantIterator = appRepo.findAll();

    applicantIterator.iterator().forEachRemaining(u -> getAllApplicants().add(u));

    Applicant[] allApplications =
        getAllApplicants().toArray(new Applicant[getAllApplicants().size()]);
    for (int i = 0; i < allApplications.length; i++) {
      if (allApplications[i].getReviewed() != null) {
        if (allApplications[i].getReviewed() == true) {
          getReviewedApplicants().add(allApplications[i]);
        } else {
          getNewApplicants().add(allApplications[i]);
        }
      } else {
        getNewApplicants().add(allApplications[i]);
      }
    }
    if (getApplicantPage() == 1) {
      model.addAttribute("applicants", getNewApplicants());
    } else {
      model.addAttribute("applicants", getReviewedApplicants());
    }
    model.addAttribute("user", user);
    model.addAttribute("page", getPage());
    model.addAttribute("pageSuccess", getPageSuccess());
    return "employee";
  }

  @RequestMapping({"/searchUser"})
  public String searchuser(@RequestParam("userName") String userName, @RequestParam("filterUser") String filterUser, Model model, Principal principal) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userService.getUserByUsername(principal.getName());
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }

    int count = 0;
    if(filterUser.equals("name")) {
	    for (int i = 0; i < getAllUsers().size(); i++) {
	      if (getAllUsers().get(i).getUsername().contains(userName)) {
	        count++;
	      }
	    }
    }else if(filterUser.equals("email")) {
    	for (int i = 0; i < getAllUsers().size(); i++) {
  	      if (getAllUsers().get(i).getEmail().contains(userName)) {
  	        count++;
  	      }
    	}
    }else if (count == 0) {
      setPage("userSearchfailed");
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      model.addAttribute("roleList", roleList);
      model.addAttribute("searchedUser", getSearchedUser());

      return "employee";
    }
    User[] searchedUsers = new User[count];
    setMyUserSearch(searchedUsers);
    count = 0;
    if(filterUser.equals("name")) {
	    for (int i = 0; i < getAllUsers().size(); i++) {
	      if (getAllUsers().get(i).getUsername().contains(userName)) {
	        searchedUsers[count] = getAllUsers().get(i);
	        count++;
	      }
	    }
    }else if(filterUser.equals("email")) {
    	for (int i = 0; i < getAllUsers().size(); i++) {
  	      if (getAllUsers().get(i).getEmail().contains(userName)) {
  	        searchedUsers[count] = getAllUsers().get(i);
  	        count++;
  	      }
  	    }
    }
    count = 0;
    setMyUserSearch(searchedUsers);
    model.addAttribute("user", user);
    setPage("userResult");
    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("myusers", getMyUserSearch());

    return "employee";
  }

  @RequestMapping({"/searchUser/{id}"})
  public String searchUserById(@PathVariable("id") int id, Model model, Principal principal) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userService.getUserByUsername(principal.getName());
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    setSearchedUser(userController.getUser(id, model));
    model.addAttribute("user", user);
    setPage("singleUserResult");
    User useradd = new User();
    model.addAttribute("users", getAllUsers());
    model.addAttribute("useradd", useradd);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    model.addAttribute("searchedUser", getSearchedUser());

    return "employee";
  }

  @GetMapping({"/viewListing/{id}"})
  public String searchMarketListingButton(@PathVariable("id") int id, Model model, Principal principal) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userService.getUserByUsername(principal.getName());
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    setMailboxPage("searchMarketListing");
    User user2 = userController.getUser(id, model);
    MarketListing[] listings = market.getListingbyUser(user2);
    Widget[] widgets = new Widget[listings.length];
    User[] sellers = new User[listings.length];
    getSearchedUserListings().clear();
    getSearchedUserWidgets().clear();
    getSearchedUserSellers().clear();
    for (int i = 0; i < listings.length; i++) {
      widgets[i] = listings[i].getWidgetSold();
      sellers[i] = listings[i].getSeller();
      getSearchedUserListings().add(listings[i]);
      getSearchedUserWidgets().add(widgets[i]);
      getSearchedUserSellers().add(sellers[i]);
    }
    User useradd = new User();
    setSingleWidgetCheck(false);
    model.addAttribute("listings", listings);
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("useradd", useradd);
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("sellers", getSearchedUserSellers());
    model.addAttribute("searchedWidgets", getSearchedUserWidgets());
    model.addAttribute("searchedMarkets", getSearchedUserListings());
    model.addAttribute("myusers", getMyUserSearch());
    return "employee";
  }

  @GetMapping({"/viewOneListing/{id}"})
  public String searchListing(@PathVariable("id") int id, Model model, Principal principal) {
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    User user = userService.getUserByUsername(principal.getName());
    setMasterPage("result");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")
        || user.getRole().equals("ROLE_SUPERADMIN")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    model.addAttribute("user", user);
    User useradd = new User();
    model.addAttribute("useradd", useradd);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    if (widgetController.getWidget(id) == null) {
      setPage("listingSearchError");
      return "employee";
    }
    setSingleWidgetCheck(true);
    System.out.println(isSingleWidgetCheck());
    setSearchedWidget(widgetController.getWidget(id));
    setSearchedMarket(market.getListingByWidget(getSearchedWidget()));
    setSearchedSeller(searchedMarket.getSeller());
    setSearchedUser(getSearchedSeller());
    
    model.addAttribute("user", user);
    // setPage("listingResult");
    setMailboxPage("searchMarketListing");
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("allMarketListings", getAllMarketListings());
    model.addAttribute("allWidgets", getAllWidgets());
    model.addAttribute("allSellers", getAllSellers());
    model.addAttribute("searchedUser", getSearchedUser());
    model.addAttribute("sellers", getSearchedSeller());
    model.addAttribute("searchedWidgets", getSearchedWidget());
    
    String category = categoryService.generateCategoryStack(getSearchedWidget().getCategory()).toString();
    category = category.replaceAll("\\[","");
    category = category.replaceAll("\\]","");
    model.addAttribute("widgetCategory", category);
    
    model.addAttribute("searchedMarkets", getSearchedMarket());
    model.addAttribute("mailpage", getMailboxPage());
    model.addAttribute("myusers", getMyUserSearch());
    model.addAttribute("page", page);

    return "employee";
  }
  

  @RequestMapping({"/viewWidgetListing"})
  public String searchOneListing(@RequestParam("name") String name, @RequestParam("filterSearch") String filter, Model model, Principal principal) {
    getAllMarketListings().clear();
    getAllWidgets().clear();
    getAllSellers().clear();
    Iterable<MarketListing> allMarketListingsIterator = market.getAllListings();

    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllMarketListings().add(u));
    allMarketListingsIterator
        .iterator()
        .forEachRemaining(u -> getAllWidgets().add(u.getWidgetSold()));
    allMarketListingsIterator.iterator().forEachRemaining(u -> getAllSellers().add(u.getSeller()));
    String[] allusernames = new String[getAllUsers().size()];
    for (int i = 0; i < allusernames.length; i++) {
      allusernames[i] = getAllUsers().get(i).getUsername();
    }

    model.addAttribute("names", allusernames);
    String[] allWidgetNames = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetNames.length; i++) {
      allWidgetNames[i] = getAllWidgets().get(i).getName();
    }
    
    String[] allWidgetDesc = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetDesc.length; i++) {
      allWidgetDesc[i] = getAllWidgets().get(i).getDescription();
    }
    
    String[] allWidgetCate = new String[getAllWidgets().size()];
    for (int i = 0; i < allWidgetCate.length; i++) {
      allWidgetCate[i] = categoryService.generateCategoryStack(getAllWidgets().get(i).getCategory()).toString();
    }

    model.addAttribute("widgetNames", allWidgetNames);
    model.addAttribute("widgetDesc", allWidgetDesc);
    model.addAttribute("widgetCate", allWidgetCate);
    User user = userService.getUserByUsername(principal.getName());
    setMasterPage("query");
    model.addAttribute("masterPage", getMasterPage());
    if (user.getRole().equals("ROLE_ADMIN")
        || user.getRole().equals("ROLE_CUSTOMERSERVICE")
        || user.getRole().equals("ROLE_TECHNICALSERVICE")
        || user.getRole().equals("ROLE_SECURITY")
        || user.getRole().equals("ROLE_SALES")
        || user.getRole().equals("ROLE_ADMIN_SHADOW")
        || user.getRole().equals("ROLE_HELPDESK_ADMIN")
        || user.getRole().equals("ROLE_HELPDESK_REGULAR")) {
    } else {
      setPage("notAuthorized");
      setMasterPage("query");
      model.addAttribute("masterPage", getMasterPage());
      model.addAttribute("user", user);
      model.addAttribute("page", page);
      return "employee";
    }
    model.addAttribute("user", user);
    model.addAttribute("page", page);
    model.addAttribute("roleList", roleList);
    
    
    int count = 0;
    if(filter.equals("name")) {
	    for (int i = 0; i < getAllWidgets().size(); i++) {
	      if (getAllWidgets().get(i).getName().contains(name)) {
	        count++;
	        System.out.println("count first" + count);
	      }
	    }
    }else if(filter.equals("description")) {
    	for (int i = 0; i < getAllWidgets().size(); i++) {
  	      if (getAllWidgets().get(i).getDescription().contains(name)) {
  	        count++;
  	        System.out.println("count first" + count);
  	      }
  	    }
    }else if(filter.equals("category")) {
    	for (int i = 0; i < getAllWidgets().size(); i++) {
  	      if (getAllWidgets().get(i).getCategory().getName().contains(name)) {
  	        count++;
  	        System.out.println("count first" + count);
  	      }
  	    }
    }else if(count == 0){
      System.out.println("count 2nd" + count + filter + name);
      setPage2("widgetSearchFailed");
      model.addAttribute("user", user);
      model.addAttribute("page2", page2);
      model.addAttribute("roleList", roleList);
      model.addAttribute("searchedUser", getSearchedUser());

      return "employee";
    }
    Widget[] searchedWidgets = getAllWidgets().toArray(new Widget[getAllWidgets().size()]);
    User[] seller = new User[count];
    MarketListing[] listings = new MarketListing[count];
    count = 0;
    getSearchedUserListings().clear();
    getSearchedUserWidgets().clear();
    getSearchedUserSellers().clear();
    
    if(filter.equals("name")) {
	    int j = 0;
	    for (int i = 0; i < searchedWidgets.length; i++) {
	      if (searchedWidgets[i].getName().contains(name)) {
	        System.out.println("count" + count);
	
	        getSearchedUserListings().add(market.getListingByWidget(searchedWidgets[i]));
	        getSearchedUserWidgets().add(searchedWidgets[i]);
	        getSearchedUserSellers().add(getSearchedUserListings().get(j).getSeller());
	        j++;
	        count++;
	      }
	    }
    }else if(filter.equals("description")) {
    	int j = 0;
	    for (int i = 0; i < searchedWidgets.length; i++) {
	      if (searchedWidgets[i].getDescription().contains(name)) {
	        System.out.println("count" + count);
	
	        getSearchedUserListings().add(market.getListingByWidget(searchedWidgets[i]));
	        getSearchedUserWidgets().add(searchedWidgets[i]);
	        getSearchedUserSellers().add(getSearchedUserListings().get(j).getSeller());
	        j++;
	        count++;
	      }
	    }
    }else if(filter.equals("category")) {
    	int j = 0;
	    for (int i = 0; i < searchedWidgets.length; i++) {
	      if (searchedWidgets[i].getCategory().getName().contains(name)) {
	        System.out.println("count" + count);
	
	        getSearchedUserListings().add(market.getListingByWidget(searchedWidgets[i]));
	        getSearchedUserWidgets().add(searchedWidgets[i]);
	        getSearchedUserSellers().add(getSearchedUserListings().get(j).getSeller());
	        j++;
	        count++;
	      }
	    }
    }
    count = 0;
    setPage2("widget");
    setSingleWidgetCheck(false);
    setPage("searchWidgetSellerMarketListing");
    model.addAttribute("allMarketListings", getSearchedUserListings());
    model.addAttribute("allWidgets", getSearchedUserWidgets());
    model.addAttribute("allSellers", getSearchedUserSellers());
    model.addAttribute("roleList", roleList);
    model.addAttribute("users", getAllUsers());
    model.addAttribute("user", user);
    model.addAttribute("page2", getPage2());
    model.addAttribute("page", getPage());

    return "employee";
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getPage2() {
    return page2;
  }

  public void setPage2(String page2) {
    this.page2 = page2;
  }

  public User getSearchedUser() {
    return searchedUser;
  }

  public void setSearchedUser(User searchedUser) {
    this.searchedUser = searchedUser;
  }

  public String getMailboxPage() {
    return mailboxPage;
  }

  public void setMailboxPage(String mailboxPage) {
    this.mailboxPage = mailboxPage;
  }

  public List<User> getAllUsers() {
    return allUsers;
  }

  public void setAllUsers(List<User> allUsers) {
    this.allUsers = allUsers;
  }

  public List<Applicant> getAllApplicants() {
    return allApplicants;
  }

  public void setAllApplicants(List<Applicant> allApplicants) {
    this.allApplicants = allApplicants;
  }

  public User[] getMyUserSearch() {
    return myUserSearch;
  }

  public void setMyUserSearch(User[] myUserSearch) {
    this.myUserSearch = myUserSearch;
  }

  public List<Widget> getAllWidgets() {
    return allWidgets;
  }

  public void setAllWidgets(List<Widget> allWidgets) {
    this.allWidgets = allWidgets;
  }

  public List<User> getAllSellers() {
    return allSellers;
  }

  public void setAllSellers(List<User> allSellers) {
    this.allSellers = allSellers;
  }

  public List<MarketListing> getAllMarketListings() {
    return allMarketListings;
  }

  public void setAllMarketListings(List<MarketListing> allMarketListings) {
    this.allMarketListings = allMarketListings;
  }

  public List<Message> getAllMessages() {
    return allMessages;
  }

  public void setAllMessages(List<Message> allMessages) {
    this.allMessages = allMessages;
  }

  public List<MarketListing> getSearchedUserListings() {
    return searchedUserListings;
  }

  public List<MarketListing> getWatchlistItems() {
	  return  watchlistItems;
  }
  
  public List<Widget> getSearchedUserWidgets() {
    return searchedUserWidgets;
  }

  public List<User> getSearchedUserSellers() {
    return searchedUserSellers;
  }

  public void setSearchedUserListings(List<MarketListing> searchedUserListings) {
    this.searchedUserListings = searchedUserListings;
  }

  public void setSearchedUserWidgets(List<Widget> searchedUserWidgets) {
    this.searchedUserWidgets = searchedUserWidgets;
  }

  public void setSearchedUserSellers(List<User> searchedUserSellers) {
    this.searchedUserSellers = searchedUserSellers;
  }

  public List<Applicant> getReviewedApplicants() {
    return reviewedApplicants;
  }

  public List<Applicant> getNewApplicants() {
    return newApplicants;
  }

  public void setReviewedApplicants(List<Applicant> reviewedApplicants) {
    this.reviewedApplicants = reviewedApplicants;
  }

  public void setNewApplicants(List<Applicant> newApplicants) {
    this.newApplicants = newApplicants;
  }

  public int getApplicantPage() {
    return applicantPage;
  }

  public void setApplicantPage(int applicantPage) {
    this.applicantPage = applicantPage;
  }

  public String getPageSuccess() {
    return pageSuccess;
  }

  public void setPageSuccess(String pageSuccess) {
    this.pageSuccess = pageSuccess;
  }

  public String getMasterPage() {
    return masterPage;
  }

  public void setMasterPage(String masterPage) {
    this.masterPage = masterPage;
  }

  public Widget getSearchedWidget() {
    return searchedWidget;
  }

  public MarketListing getSearchedMarket() {
    return searchedMarket;
  }

  public User getSearchedSeller() {
    return searchedSeller;
  }

  public void setSearchedWidget(Widget searchedWidget) {
    this.searchedWidget = searchedWidget;
  }

  public void setSearchedMarket(MarketListing searchedMarket) {
    this.searchedMarket = searchedMarket;
  }

  public void setSearchedSeller(User searchedSeller) {
    this.searchedSeller = searchedSeller;
  }

  public boolean isSingleWidgetCheck() {
    return singleWidgetCheck;
  }

  public void setSingleWidgetCheck(boolean singleWidgetCheck) {
    this.singleWidgetCheck = singleWidgetCheck;
  }

  public String getPage3() {
    return page3;
  }

  public void setPage3(String page3) {
    this.page3 = page3;
  }
}
