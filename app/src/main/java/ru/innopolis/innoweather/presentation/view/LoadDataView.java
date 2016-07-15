package ru.innopolis.innoweather.presentation.view;

import android.content.Context;

public interface LoadDataView {

    void showProgress();

    void hideProgress();

    void showError(String message);

    Context getContext();
}
