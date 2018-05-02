package com.jarq.system.managers;

import com.jarq.AbstractTest;
import com.jarq.system.enums.RepositoriesPath;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class RepositoryFileWriterTest extends AbstractTest {

    private IContentWriter<String> writer;

    @Before
    public void setUp() {
        writer = RepositoryFileWriter.getInstance(StandardCharsets.UTF_8);

    }

    @Test
    public void getInstance() {

        assertNotNull(writer);
        assertTrue(writer instanceof RepositoryFileWriter);
    }

    @Test
    public void writeContent() throws IOException {

        String testFilepath = RepositoriesPath
                .TEST_FILES_REPOSITORY.getPath() + "writerTextFile.md";

        writer.writeContent(testFilepath, getTestText());

        String output = readTestFile(testFilepath);

        assertEquals(getTestText(), output);

    }

    private String readTestFile(String testFilepath) throws IOException {
        Path path = Paths.get(testFilepath);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            int len = stringBuilder.length();
            stringBuilder.delete(len-1, len);  // removes unnecessary new line from the end of text
        }
        return stringBuilder.toString();
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
                "we gurgled and chattered and screamed at the swans.\n" +
                "\n" +
                "The setting of that nursery biography is vague. It seemed to me that the\n" +
                "earth was made up of field beyond field, and lanes that ran from this\n" +
                "world to the next, with daisies that never could be gathered, they were\n" +
                "so many; and an ocean since has impressed me less with the notion of\n" +
                "immensity of liquid surface than the modest sheet of water we called the\n" +
                "Pond. Years afterwards I walked out from town to that village, and how\n" +
                "small the pond was, how short the lanes, what little patches for fields\n" +
                "so sparsely sprinkled with daisies! A more miserable disillusionment I\n" +
                "have not known.";
    }
}