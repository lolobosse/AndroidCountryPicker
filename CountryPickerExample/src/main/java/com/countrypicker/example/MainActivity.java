package com.countrypicker.example;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.countrypicker.City;
import com.countrypicker.Country;
import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.countrypicker.CountryProvider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private String TAG = getClass().getSimpleName();
    private List<Country> countryList;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.countrypickerexample.R.layout.activity_main);
        countryList = new ArrayList<>();
        cityList = new ArrayList<>();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        try {
            CountryProvider countryProvider = new CountryProvider(getApplicationContext());
            countryList = countryProvider.getCountries();
            cityList = countryProvider.getCityList();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }


        CountryPicker picker = new CountryPicker();
        picker.setSearchableList(cityList);
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onFilter(String filter) {

            }
        });

        transaction.replace(com.countrypickerexample.R.id.home, picker);

        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.countrypickerexample.R.menu.main, menu);
        MenuItem item = menu.findItem(com.countrypickerexample.R.id.show_dialog);
        item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {

                    Log.d(TAG, "onMenuItemClick: ");
                    CountryPicker picker = CountryPicker.newInstance("Select Country");
                    picker.setSearchableList(cityList);
                    /**
                    picker.getCountryListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d(TAG, "onItemClick: " + countryList.get(position).getSearchableString());
                        }
                    });
                    picker.setListener(new CountryPickerListener() {
                        @Override
                        public void onSelectCountry(String name, String code) {
                            Toast.makeText(
                                    MainActivity.this,
                                    "Country Name: " + name + " - Code: " + code,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                     **/

                    picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
                    return false;
                } catch (Exception e) {
                    Log.e(TAG, "onMenuItemClick: ", e);
                    return false;
                }

            }
        });
        return true;
    }
}
