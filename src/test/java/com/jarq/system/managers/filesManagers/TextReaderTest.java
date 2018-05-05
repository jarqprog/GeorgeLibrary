package com.jarq.system.managers.filesManagers;

import com.jarq.AbstractTest;
import com.jarq.system.enums.RepositoriesPath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TextReaderTest extends AbstractTest {

    private IContentReader<String> contentReader;
    private String testFile = RepositoriesPath.READER_TEST_FILE.getPath();

    @Before
    public void setUp() {
        contentReader = TextReader.getInstance(StandardCharsets.UTF_8);
    }

    @Test
    public void getInstance() {
        assertNotNull(contentReader);
        assertTrue(contentReader instanceof TextReader);
    }

    @Test
    public void readContent() throws IOException {

        String expected = getTestText();
        String output = contentReader.readContent(testFile);

        assertEquals(expected, output);
    }

    private String getTestText() {

        return "My nurse loved me devotedly, and of course \n" +
                "spoiled me. Most of the villagers helped her \n" +
                "in this good work, so that the first seven years \n" +
                "of my childhood, in spite of baby-face unblest by \n" +
                "mother’s kiss, were its happiest period. Women \n" +
                "who do not love their children do well to put \n" +
                "them out to nurse. The contrast of my life at \n" +
                "home and the years spent with these rustic \n" +
                "strangers is very shocking. The one petted, \n" +
                "cherished, and untroubled; the other full of dark \n" +
                "terrors and hate, and a loneliness such as grown \n" +
                "humanity cannot understand without experience \n" +
                "of that bitterest of all tragedies — unloved and \n" +
                "ill-treated childhood. But I was only reminded \n" +
                "of my sorrow at nurse’s on the rare occasion \n" +
                "of my mother’s visits, or when nurse once a \n" +
                "month put me into my best clothes, after wash- \n" +
                "ing my face with blue mottled soap — >a thing I \n" +
                "detested — and carried me off on the mail-car to";
    }
}
