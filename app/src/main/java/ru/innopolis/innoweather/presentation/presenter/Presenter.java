package ru.innopolis.innoweather.presentation.presenter;


/**
 * Presenter in an MVP pattern
 */
public interface Presenter {

  void resume();

  void pause();

  void destroy();
}
