package ru.codefest.client.android.test.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import ru.codefest.client.android.model.Category;
import ru.codefest.client.android.model.Lecture;
import ru.codefest.client.android.parser.CodeFestHtmlParser;

public class TestCodeFestHtmlParser extends TestCase {

    public TestCodeFestHtmlParser(String name) {
        super(name);
    }

    public void testGetLectureHtmlByUrl() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        try {
            String lectureHtml = parser
                    .getLectureHtmlByUrl(CodeFestHtmlParser.CODEFEST_PROGRAM_URL
                            + "project-production/");
            assertTrue(lectureHtml.length() > 0);
            assertTrue(lectureHtml.contains("inner-name"));
            assertTrue(lectureHtml.contains("ava"));
            assertTrue(lectureHtml.contains("block"));
            assertTrue(lectureHtml.contains("prog-list"));
        } catch (IOException e) {
            fail("No internet");
            e.printStackTrace();
        }

    }

    public void testParseCodeFestCategories() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        List<Category> categoryList = new ArrayList<Category>();
        categoryList.add(new Category("Web"));
        categoryList.add(new Category("Enterprise"));
        categoryList.add(new Category("Mobile"));
        categoryList.add(new Category("PM"));
        categoryList.add(new Category("QA"));
        categoryList.add(new Category("Design"));
        categoryList.add(new Category(" вартирники"));
        try {
            List<Category> categories = parser
                    .parseCodeFestCategories(CodeFestHtmlParser.CODEFEST_PROGRAM_URL);
            assertEquals(categories, categoryList);
            assertTrue(categoryList.containsAll(categories));
        } catch (IOException e) {
            fail("No internet");
            e.printStackTrace();
        }
    }

    public void testParseCodeFestProgram() {
        CodeFestHtmlParser parser = new CodeFestHtmlParser();
        try {
            List<Lecture> lectures = parser
                    .parseCodeFestProgram(CodeFestHtmlParser.CODEFEST_PROGRAM_URL);
            assertEquals(50, lectures.size());
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
