package ru.innopolis.innoweather.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.innoweather.R;
import ru.innopolis.innoweather.presentation.model.CityModel;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {
    private static final String TAG = "CitiesAdapter";

    public interface OnItemClickListener {
        void onCityItemClicked(CityModel cityModel);
    }

    private Collection<CityModel> citiesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    @Inject
    public CitiesAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        citiesCollection = Collections.emptyList();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.card_layout, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        final CityModel cityModel = ((List<CityModel>) citiesCollection).get(position);
        holder.tvName.setText(cityModel.getName());
        if (cityModel.getTemp() != null) {
            holder.tvTemp.setText(cityModel.getTemp().toString());
        }
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onCityItemClicked(cityModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (citiesCollection != null) ? citiesCollection.size() : 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setCitiesCollection(Collection<CityModel> citiesCollection) {
        validateCitiesCollection(citiesCollection);
        this.citiesCollection = citiesCollection;
        notifyDataSetChanged();
    }

    private void validateCitiesCollection(Collection<CityModel> citiesList) {
        if (citiesList == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cities_name)
        TextView tvName;
        @BindView(R.id.tv_cities_temp)
        TextView tvTemp;

        public CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}