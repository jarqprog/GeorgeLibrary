package com.jarq.system.managers.filesManagers;

import com.jarq.AbstractTest;
import com.jarq.system.enums.RepositoriesPath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class RepositoryFileReaderTest extends AbstractTest {

    private IContentReader<String> contentReader;
    private String testFile = RepositoriesPath
            .TEST_FILES_REPOSITORY.getPath() +
            RepositoriesPath.READER_TEST_FILE.getPath();

    @Before
    public void setUp() {
        contentReader = RepositoryFileReader.getInstance(StandardCharsets.UTF_8);
    }

    @Test
    public void getInstance() {
        assertNotNull(contentReader);
        assertTrue(contentReader instanceof RepositoryFileReader);
    }

    @Test
    public void readContent() throws IOException {

        String expected = getTestText();
        String output = contentReader.readContent(testFile);

        assertEquals(expected, output);
    }

    private String getTestText() {

        return "She had a troop of pretty engaging children, mostly girls, only one of\n" +
                "whom she was ever known to kiss or caress, and to the others she was\n" +
                "worse than the traditional stepmother of fairy tale. It was only\n" +
                "afterwards I learned that those proud creatures I, in my abject\n" +
                "solitude, hated and envied, lived in the same deadly fear of her with\n" +
                "which her cold blue eyes and thin cruel lips inspired me with.\n" +
                "\n" +
                "But there were, thank God! many bright hours for me, untroubled by her\n" +
                "shadow. I was a little sovereign lady in my nurse's kindly village,\n" +
                "admired and never thwarted. I toddled imperiously among a small world in\n" +
                "corduroy breeches and linsey skirts, roaming unwatched the fields and\n" +
                "lanes from daylight until dark. We sat upon green banks and made daisy\n" +
                "chains, and dabbled delightedly with the sand of the pond edges, while\n" +
                "we gurgled and chattered and screamed at the swans.";
    }
}
