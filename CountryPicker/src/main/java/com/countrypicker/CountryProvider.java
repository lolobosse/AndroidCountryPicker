package com.countrypicker;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Country provider loading and containing all country instances available.
 *
 * @author Michael Ruf
 * @since 2015-09-04
 */
public class CountryProvider {

    private Context context;
    private String countryJson;
    private List<Country> countryList;

    private CountryProvider() {
        countryList = new ArrayList<>();
    }

    public CountryProvider(Context context) throws IOException, JSONException {
        this();
        this.context = context;
        readCountryJsonString();
        parseCountryJson();
    }

    private void readCountryJsonString()
            throws java.io.IOException {
        String base64 = context.getResources().getString(R.string.countries);
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        countryJson = new String(data, "UTF-8");
        Log.d(CountryPicker.TAG, countryJson);
    }

    private void parseCountryJson() throws JSONException {
        JSONObject jsonObject = new JSONObject(countryJson);
        Iterator<?> keys = jsonObject.keys();

        // Add the data to all countries list
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Country country = new Country();
            country.setCode(key);
            country.setName(jsonObject.getString(key));
            countryList.add(country);
        }

        // Sort the all countries list based on country name
        Collections.sort(countryList, new Comparator<Country>() {
            @Override
            public int compare(Country lhs, Country rhs) {
                if (lhs.getCode().toLowerCase().equals("de")){
                    return -1;
                }
                else if (rhs.getCode().toLowerCase().equals("de")){
                    return 1;
                }
                return stripAccents(lhs.getName()).compareTo(stripAccents(rhs.getName()));
            }
        });
    }

    public List<Country> getCountries() {
        return countryList;
    }

    public Country getCountryByCode(String code) {
        for (Country country : countryList) {
            if (country.getCode().equalsIgnoreCase(code)) {
                return country;
            }
        }
        return null;
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
