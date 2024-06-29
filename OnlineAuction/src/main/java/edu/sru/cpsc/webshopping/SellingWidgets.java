package edu.sru.cpsc.webshopping;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.scheduling.annotation.EnableScheduling;

import edu.sru.cpsc.webshopping.service.SuggestionService;
import edu.sru.cpsc.webshopping.service.TaxExcelToDatabaseService;
import edu.sru.cpsc.webshopping.util.PreLoad;
import jakarta.servlet.ServletContext;

@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SellingWidgets extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
	private PreLoad preLoad;

	@Autowired
    private Environment env;

    @Autowired
    private TaxExcelToDatabaseService taxExcelToDatabaseService;

    @Value("${server.port}")
    private String port;
    
    @Value("${spring.datasource.username}")
    private String userName;
    
    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private SuggestionService suggestionService;

    public SellingWidgets() {
    }

    public static void main(String[] args) {
        SpringApplication.run(SellingWidgets.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws SQLException, IOException{
//        Resource excelResource = new ClassPathResource("StateTaxes.xlsx");
//        try {
//            taxExcelToDatabaseService.loadFromExcelFile(excelResource);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        //Run import.sql script in class path resources and run query to insert BLOB information
    	Resource sqlResource = new ClassPathResource("import.sql");
    	Resource[] defaultImages = {new ClassPathResource("static/listingImages/71corvetesbautokit.jpg"), 
    			new ClassPathResource("static/listingImages/hummer h3.jpg"),
    			new ClassPathResource("static/listingImages/2020-chevrolet-corvette-lt2-engine-1563399415.jpg"), 
    			new ClassPathResource("static/listingImages/MT82-Gen-3-1__25921.jpg"), 
    			new ClassPathResource("static/listingImages/s-l400.jpg")};
    	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/SellingWidgets", userName, password);
    	String query = "INSERT INTO widget_image VALUES (1, ?, '71corvetesbautokit.jpg',1),"
    			+ "(2, ?, 'hummer h3',2),"
    			+ "(3, ?, '2020-chevrolet-corvette-lt2-engine-1563399415.jpg',3),"
    			+ "(4, ?, 'MT82-Gen-3-1__25921.jpg',4),"
    			+ "(5, ?, 's-l400.jpg',5);";
    	
        try {
        	ScriptUtils.executeSqlScript(connection, new EncodedResource(sqlResource), true, true, "--", ";", "/*", "*/");
			
		} catch (Exception e) {
			System.out.println("Issue with import.sql");
        	e.printStackTrace();
		}
    	
    	try {
           
            PreparedStatement statement = connection.prepareStatement(query);
            Blob img1 = BlobProxy.generateProxy(defaultImages[0].getContentAsByteArray());
            Blob img2 = BlobProxy.generateProxy(defaultImages[1].getContentAsByteArray());
            Blob img3 = BlobProxy.generateProxy(defaultImages[2].getContentAsByteArray());
            Blob img4 = BlobProxy.generateProxy(defaultImages[3].getContentAsByteArray());
            Blob img5 = BlobProxy.generateProxy(defaultImages[4].getContentAsByteArray());
            statement.setBlob(1, img1);
            statement.setBlob(2, img2);
            statement.setBlob(3, img3);
            statement.setBlob(4, img4);
            statement.setBlob(5, img5);
            statement.executeUpdate();
            
        } catch (Exception e) {
//        	System.out.println("Issue inserting BLOBs. Already exist or action did not work.");
//        	e.printStackTrace();
        }
        
        //Load attribute suggestions from excel file
        Resource suggestions = new ClassPathResource("Website_Car_Parts_Categories.xlsx");
        try {
			suggestionService.loadSuggestions(suggestions);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        connection.close();
        
//        BackupService backupService = BackupService.getInstance();
//        backupService.scheduleBackup(new Date(System.currentTimeMillis()), "Daily");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SellingWidgets.class);
    }

    @Override
    public void run(String... args) throws Exception {
        String ddlAuto = env.getProperty("spring.jpa.hibernate.ddl-auto");
        if (!"update".equals(ddlAuto)) {
            //preLoad.importCategoriesFromCSV();
        }
        //get ip address
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println("\nRunning on https://" + ip + ":" + port + servletContext.getContextPath() + "\n");
    }
}