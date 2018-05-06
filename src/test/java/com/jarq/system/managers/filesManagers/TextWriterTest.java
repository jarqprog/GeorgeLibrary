package com.jarq.system.managers.filesManagers;

import com.jarq.system.FileRelatedTest;
import com.jarq.system.enums.RepositoriesPath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class TextWriterTest extends FileRelatedTest {

    private IContentWriter<String> writer;

    @Before
    public void setUp() {
        writer = TextWriter.getInstance(StandardCharsets.UTF_8);
    }

    @Test
    public void getInstance() {

        assertNotNull(writer);
        assertTrue(writer instanceof TextWriter);
    }

    @Test
    public void writeContent() throws IOException {

        String testFilepath = RepositoriesPath.WRITER_TEST_FILE.getPath();

        writer.writeContent(testFilepath, getTestText());

        String output = readTestFile(testFilepath);

        assertEquals(getTestText(), output);

    }

    @Test
    public void writeContent_as_bytes() throws IOException {

        String testFilepath = RepositoriesPath.WRITER_TEST_FILE.getPath();

        writer.writeContent(testFilepath, getTestText().getBytes(StandardCharsets.UTF_8));

        String output = readTestFile(testFilepath);

        assertEquals(getTestText(), output);
    }

    private String getTestText() {

        return "I. \n" +
                "\n" +
                "\n" +
                "Autobiography of a Child \n" +
                "\n" +
                "mamma/’ It was very exciting. First one chair \n" +
                "had to be reached, then another fallen over, till \n" +
                "a third tumbled me at my mother’s feet. I burst \n" +
                "into a passion of tears, not because of the fall, \n" +
                "but from terror at finding myself so near my \n" +
                "mother. Nurse gathered me into her arms and \n" +
                "began to coo over me, and here the picture \n" +
                "fades from my mind. \n" +
                "\n" +
                "My nurse loved me devotedly, and of course \n" +
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