package ru.codefest.client.android.test.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.test.RenamingDelegatingContext;

public class TestCategoryDao extends AbstractCodeFestProvider {

    private class MyMockContext extends RenamingDelegatingContext {

        public MyMockContext(Context targetContext) {
            super(targetContext, PREFIX);
            super.makeExistingFilesAndDbsAccessible();
        }

        @Override
        public ContentResolver getContentResolver() {
            return getMockContentResolver();
        }
    }

    private MyMockContext mockedContext;

    private static final String PREFIX = "test."; // Not actually used, but
                                                  // needed by
                                                  // RenamingDelegatingContext
                                                  //
    // public void testBulkInsertCategories() {
    // CodeFestHtmlParser parser = new CodeFestHtmlParser();
    // CategoryDao dao = new CategoryDao(mockedContext, new BinderHelper());
    // List<Category> expectedList = new ArrayList<Category>();
    // try {
    // expectedList = parser
    // .parseCodeFestCategories(CodeFestHtmlParser.CODEFEST_PROGRAM_URL);
    // } catch (IOException e) {
    // fail("No internet!");
    // e.printStackTrace();
    // }
    // dao.bulkInsertCategories(expectedList);
    // List<Category> actualList = dao.getCategoryList();
    // assertEquals(expectedList.size(), actualList.size());
    // }

    @Override
    protected void setUp() throws Exception {
        mockedContext = new MyMockContext(getContext());
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();
    }
}
