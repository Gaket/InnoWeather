package ru.innopolis.innoweather.presentation.view;

import android.content.Context;

public interface LoadDataView {

  void showBusy();

  void hideBusy();

  void showError(String message);

  Context getContext();
}
