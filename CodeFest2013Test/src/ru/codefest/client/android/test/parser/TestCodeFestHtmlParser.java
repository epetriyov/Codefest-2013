package ru.codefest.client.android.test.parser;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.model.LecturePeriod;
import ru.codefest.client.android.parser.CodeFestHtmlParser;

public class TestCodeFestHtmlParser extends TestCase {

    public TestCodeFestHtmlParser(String name) {
        super(name);
    }

    public void testParseCodeFestCategories() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        List<Category> actualCategories;
        try {
            actualCategories = parser
                    .parseCodeFestCategories(CodeFestHtmlParser.CODEFEST_URL);
            int expectedCatergoryCount = 6;
            assertEquals(actualCategories.size(), expectedCatergoryCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testParseCodeFestProgram() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        try {
            List<Category> actualCategories = parser
                    .parseCodeFestCategories(CodeFestHtmlParser.CODEFEST_URL);
            List<LecturePeriod> actualPeriods = parser
                    .parseLecturePeriods(CodeFestHtmlParser.CODEFEST_URL);
            List<Lecture> lectures = parser.parseCodeFestProgram(
                    CodeFestHtmlParser.CODEFEST_URL, actualCategories,
                    actualPeriods);
            assertEquals(47, lectures.size());
        } catch (IOException e) {
            fail("No internet");
            e.printStackTrace();
        }
    }

    public void testParseLecturePeriods() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        List<LecturePeriod> actualPeriods;
        try {
            actualPeriods = parser
                    .parseLecturePeriods(CodeFestHtmlParser.CODEFEST_URL);
            int expectedPeriodsCount = 16;
            assertEquals(actualPeriods.size(), expectedPeriodsCount);
        } catch (IOException e) {
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
