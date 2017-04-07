package com.countrypicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CountryListAdapter extends BaseAdapter {

    private String TAG = getClass().getSimpleName();
    private Context context;
    // Implies the objects in the list are CountryPickerModels
    // i.e. they have to implement CountryPickerModel
    private List<? extends CountryPickerModel> countryPickerModels;


    protected CountryListAdapter(Context context, List<? extends CountryPickerModel> countryPickerModels) {
        this.context = context;
        this.countryPickerModels =  countryPickerModels;
    }

    @Override
    public int getCount() {
        return countryPickerModels.size();
    }

    @Override
    public CountryPickerModel getItem(int position) {
        return countryPickerModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO There is no id?
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cell cell;

        if (convertView != null && convertView.getTag() instanceof Cell) {
            cell = (Cell) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.country_picker_row, parent, false);
            cell = new Cell(convertView);
            convertView.setTag(cell);
        }

        cell.textView.setText(getItem(position).getSearchableString());
        if (getItem(position).hasID()) {
            cell.imageView.setImageResource(getResId(getItem(position).getID()));
            cell.imageView.setVisibility(View.VISIBLE);
        } else {
            cell.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

    // Only needed when showing country flag in the list (nationality only)
    private int getResId(String id) {
        String drawableName = "flag_" + id.toLowerCase(Locale.ENGLISH);
        return context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
    }

    /**
     * Holder for the cell
     */
    protected static class Cell {

        public TextView textView;
        public ImageView imageView;

        public Cell(View view) {
            textView = (TextView) view.findViewById(R.id.country_picker_row_title);
            imageView = (ImageView) view.findViewById(R.id.country_picker_row_icon);
        }
    }
}
