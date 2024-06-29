package edu.sru.cpsc.webshopping.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BackupServiceTest {

    @Mock
    private ScheduledExecutorService executor;

    @Mock
    private ScheduledFuture<?> currentTask;

    @InjectMocks
    private BackupService backupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testScheduleBackup() {
        Date date = new Date();
        String repeat = "Daily";
        backupService.scheduleBackup(date, repeat);

        verify(currentTask).cancel(true);
        verify(executor).scheduleAtFixedRate(any(Runnable.class), eq(0L), anyLong(), any(TimeUnit.class));
    }
    
    @Test
    void testGetInstance() {
    	BackupService backupService = BackupService.getInstance();
    	assertSame(backupService, BackupService.getInstance());
    }
}