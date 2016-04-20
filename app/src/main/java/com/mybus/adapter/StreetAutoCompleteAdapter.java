package com.mybus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mybus.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adapter for AutoCompleteEditText searching streets names
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class StreetAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<String> resultList = new ArrayList<String>();
    private Call mLastCall;

    public StreetAutoCompleteAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (mLastCall != null) {
                    mLastCall.cancel();
                }
                if (constraint != null && constraint.length() >= 3) {
                    List<String> streetList = findStreets(stripAccents(constraint.toString()));
                    // Assign the data to the FilterResults
                    filterResults.values = streetList;
                    filterResults.count = streetList.size();
                }
                return filterResults;
            }

            @Override
            public void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    /**
     * Get's the list of streets matching a constraint
     * <p/>
     * Example url: //http://gis.mardelplata.gob.ar/opendata/ws.php?method=rest&endpoint=callejero_mgp&token=rwef3253465htrt546dcasadg4343&nombre_calle=ind
     *
     * @param constraint
     * @return list of streets
     */
    private List<String> findStreets(String constraint) {
        List<String> results = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://gis.mardelplata.gob.ar/opendata/ws.php?method=rest&endpoint=callejero_mgp&token=rwef3253465htrt546dcasadg4343&nombre_calle=" + constraint)
                .build();
        try {
            mLastCall = client.newCall(request);
            Response response = mLastCall.execute();

            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < jsonArray.length(); i++) {
                results.add(jsonArray.getJSONObject(i).getString("descripcion"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Removes the accents to any string
     *
     * @param s
     * @return
     */
    private String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}