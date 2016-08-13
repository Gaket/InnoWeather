package ru.innopolis.innoweather.presentation.view;

import android.content.Context;

public interface LoadDataView {

    /**
     * Show some kind of progress bar
     */
    void showProgress();

    /**
     * Hide the progress bar
     */
    void hideProgress();

    /**
     * Show that there is an error
     *
     * @param message to show
     */
    void showError(String message);

    /**
     * Get the context of view
     */
    Context getContext();
}
