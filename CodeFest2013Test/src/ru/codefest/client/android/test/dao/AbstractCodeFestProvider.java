package ru.codefest.client.android.test.dao;

import ru.codefest.client.android.provider.CodeFestProvider;
import android.test.ProviderTestCase2;

public abstract class AbstractCodeFestProvider extends
        ProviderTestCase2<CodeFestProvider> {
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

        // clear out all the old data.
        CodeFestProvider dataProvider = (CodeFestProvider) getMockContentResolver()
                .acquireContentProviderClient(CodeFestProvider.CONTENT_URI)
                .getLocalContentProvider();
        // dataProvider.deleteAll();
    }
}