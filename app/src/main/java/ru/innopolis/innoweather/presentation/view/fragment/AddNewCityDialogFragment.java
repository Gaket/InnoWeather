package ru.innopolis.innoweather.presentation.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;
import ru.innopolis.innoweather.presentation.di.components.UserComponent;
import ru.innopolis.innoweather.presentation.presenter.AddNewCityPresenter;
import ru.innopolis.innoweather.presentation.view.AddNewCityView;

public class AddNewCityDialogFragment extends DialogFragment implements AddNewCityView {
    private static final String TAG = "CityPickerDialog";

    @Inject
    AddNewCityPresenter addNewCityPresenter;

    @BindView(R.id.actv_cities_picker)
    AutoCompleteTextView actvCitiesPicker;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(UserComponent.class).inject(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_city_picker, null);
        ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle(getString(R.string.dialog_title_pick_city));
        builder.setNegativeButton(getString(R.string.dialog_cancel), (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });
        builder.setPositiveButton(getString(R.string.dialog_add_city), (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }



    @Override
    public int chooseNewCity() {
        return 0;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        // adapter should be filterable, therefore here is a concrete class
        // instead of higher level abstraction
        CursorAdapter adapter = addNewCityPresenter.createAdapter(getContext());
        actvCitiesPicker.setAdapter(adapter);
        return rootView;
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
