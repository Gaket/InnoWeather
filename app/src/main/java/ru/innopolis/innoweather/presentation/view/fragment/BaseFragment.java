package ru.innopolis.innoweather.presentation.view.fragment;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.di.HasComponent;

public abstract class BaseFragment extends Fragment {

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_update:
                update();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Updates data in current fragment
     *
     * @return true in case of successful update
     */
    public abstract boolean update();
}
