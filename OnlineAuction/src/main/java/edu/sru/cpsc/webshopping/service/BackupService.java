package edu.sru.cpsc.webshopping.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This class handles the database backups of the system. This is achieved
 * through the use of Java Executor Service and TimerTask classes.
 * 
 * @author Wolfgang
 */

@Service
public class BackupService {
	//Create only one instance of the backup service so multiple backups aren't occurring at the same time
	private static BackupService backupService = new BackupService();
//	private Timer backupTimer = new Timer();
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> currentTask;
	private BackupTask backupTask = new BackupTask();

	//Constructor is locked to achieve singleton pattern
	private BackupService() {
	}

	//Get the instance of the backup service
	public static BackupService getInstance() {
		return backupService;
	}

	//Cancels the current scheduled task and schedules task with new parameters
	public void scheduleBackup(Date date, String repeat) {
		TimeUnit repeatUnit;
		long period;
		
		if(!(currentTask == null)) {
			if(!currentTask.isCancelled())
				currentTask.cancel(true);
		}

		switch (repeat) {
		case "Minute":
			period = 1;
			repeatUnit = TimeUnit.MINUTES;
			break;
		case "Daily":
			period = 1;
			repeatUnit = TimeUnit.DAYS;
			break;
		case "Weekly":
			period = 7;
			repeatUnit = TimeUnit.DAYS;
			break;
		case "Monthly":
			period = 30;
			repeatUnit = TimeUnit.DAYS;
			break;
		default:
			period = 1;
			repeatUnit = TimeUnit.DAYS;
		}
		
		currentTask = executor.scheduleAtFixedRate(backupTask, 0, period, repeatUnit);
	}
}

/**
 * This is the class that runs the backup. It extends TimerTask allowing it to run on a separate thread and be scheduled by our executor.
 * @author Wolfgang
 */
class BackupTask extends TimerTask {

    private String username = "springuser";
    private String password = "ThePassword";
    
    private String exeFilePath = "src/main/resources/static/tools/mysqldump.exe";
    
    private String dumpFilePath ="src/main/resources/static/backups";
    
	public void run() {
		// TODO add backup functionality
		Date dateAndTimeOfBackup = new Date();
		System.out.println("[" + dateAndTimeOfBackup.toString() + "]" +  " Running Backup...");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String dumpName = formatter.format(dateAndTimeOfBackup);
		
		System.out.println(exeFilePath);
		System.out.println(dumpFilePath);
		
		Process process = null;
		try {
			Runtime runtime = Runtime.getRuntime();

			System.out.println(username + "\n" + password);
			
			process = runtime.exec(String.format("%s -u%s -p%s sellingwidgets", exeFilePath, username, password));
			
			InputStreamReader inputSteam = new InputStreamReader(process.getInputStream());
			BufferedReader bufferReader = new BufferedReader(inputSteam);
			String line = "";
			FileWriter newDump = new FileWriter(String.format("%s/%s.sql", dumpFilePath, dumpName));
			
			newDump.write("CREATE SCHEMA `sellingwidgets` ;" + System.lineSeparator() + "USE `sellingwidgets` ;" + System.lineSeparator());
			
			while(true) {
				line = bufferReader.readLine();
				if(line == null) {
					break;
				}
				newDump.write(line + System.lineSeparator());
			}
			
			newDump.close();
			
			process.waitFor();
			if(process.exitValue() == 0) {
				dateAndTimeOfBackup = new Date();
				System.out.println("[" + dateAndTimeOfBackup.toString() + "]" +  " Backup Successful!");
			}
			else {
				System.out.println("Error occured with exit code: " + process.exitValue());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}