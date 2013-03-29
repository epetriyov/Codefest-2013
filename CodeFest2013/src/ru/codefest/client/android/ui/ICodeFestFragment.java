package ru.codefest.client.android.ui;

public interface ICodeFestFragment {

    CodeFestActivity getCodeFestActivity();

    void hideNoResults();

    void hideProgress();

    void showNoResults();

    void showProgress();

    void updateList();

}
