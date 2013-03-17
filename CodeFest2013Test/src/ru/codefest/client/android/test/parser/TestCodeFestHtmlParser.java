package ru.codefest.client.android.test.parser;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.parser.CodeFestHtmlParser;

public class TestCodeFestHtmlParser extends TestCase {

    public TestCodeFestHtmlParser(String name) {
        super(name);
    }

    public void testParseCodeFestProgram() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        try {
            List<Lecture> lectures = parser
                    .parseCodeFestProgram(CodeFestHtmlParser.CODEFEST_URL);
            assertEquals(47, lectures.size());
        } catch (IOException e) {
            fail("No internet");
            e.printStackTrace();
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
