package ru.codefest.client.android.test.dao;

import ru.codefest.client.android.provider.CodeFestProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.test.ProviderTestCase2;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;

public abstract class AbstractCodeFestProvider extends
        ProviderTestCase2<CodeFestProvider> {

    private class MyMockContext extends RenamingDelegatingContext {

        public MyMockContext(Context targetContext) {
            super(targetContext, PREFIX);
            super.makeExistingFilesAndDbsAccessible();
        }

        @Override
        public ContentResolver getContentResolver() {
            return mockedContentResolver;
        }
    }

    protected MockContentResolver mockedContentResolver;

    protected MyMockContext mockedContext;

    private static final String PREFIX = "test.";

    public AbstractCodeFestProvider() {
        this(CodeFestProvider.class, CodeFestProvider.CONTENT_URI);
    }

    public AbstractCodeFestProvider(Class<CodeFestProvider> providerClass,
            String providerAuthority) {
        super(providerClass, providerAuthority);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockedContext = new MyMockContext(getContext());
        // Gets the resolver for this test.
        mockedContentResolver = getMockContentResolver();
        // clear out all the old data.
        CodeFestProvider dataProvider = (CodeFestProvider) getMockContentResolver()
                .acquireContentProviderClient(CodeFestProvider.CONTENT_URI)
                .getLocalContentProvider();
        // dataProvider.deleteAll();
    }
}