package com.jarq.system.log;

import com.jarq.AbstractTest;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

public class LoggerTest extends AbstractTest {

    private ILog logger;
    private IDateTimer dateTimer;
    private ILogWriter<String> logWriter;

    @Before
    public void setUp()  {

        dateTimer = mock(DateTimer.class);
        logWriter = mock(LogWriter.class);

        logger = Logger.getInstance(dateTimer, logWriter);
    }

    @Test
    public void getInstance() {
        assertNotNull(logger);
        assertTrue(logger instanceof Logger);
    }

    @Test
    public void log() throws Exception {

        when(dateTimer.getCurrentDateTime()).thenReturn("2018-05-05 12:10:55");
        when(logWriter.write(anyString())).thenReturn(true);

        assertTrue(logger.log("Error message"));
    }

    @Test
    public void log_when_something_goes_wrong_should_return_false() throws IOException {

        when(dateTimer.getCurrentDateTime()).thenReturn("2018-05-05 12:10:55");
        when(logWriter.write(anyString())).thenReturn(false);

        assertFalse(logger.log("Error message"));
    }
}