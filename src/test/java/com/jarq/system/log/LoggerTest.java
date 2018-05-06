package com.jarq.system.log;

import com.jarq.AbstractTest;
import com.jarq.system.helpers.datetimer.DateTimer;
import com.jarq.system.helpers.datetimer.IDateTimer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

public class LoggerTest extends AbstractTest {

    private ILog logger;
    private IDateTimer dateTimer;
    private ILogWriter<String> writer;

    @Before
    public void setUp()  {

        dateTimer = mock(DateTimer.class);
        writer = mock(LogWriter.class);

        logger = Logger.getInstance(dateTimer, writer);
    }

    @Test
    public void getInstance() {
        assertNotNull(logger);
        assertTrue(logger instanceof Logger);
    }

    @Test
    public void log() throws IOException {

        when(dateTimer.getCurrentDateTime()).thenReturn("2018-05-05 12:10:55");
        when(writer.write("error message")).thenReturn(true);

        assertTrue(logger.log("error message"));
    }

    @Test
    public void log_when_something_goes_wrong_should_return_false() throws IOException {

        when(dateTimer.getCurrentDateTime()).thenReturn("2018-05-05 12:10:55");
        when(writer.write("error message")).thenReturn(false);

        assertFalse(logger.log("error message"));
    }
}