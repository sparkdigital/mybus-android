package com.mybus.helper;


import android.widget.Filter;

import com.mybus.listener.OnFindResultsListener;
import com.mybus.model.StreetSuggestion;
import com.mybus.service.ServiceFacade;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Filter for searching streets given a constraint
 * <p/>
 * Created by ldimitroff on 01/06/16.
 */
public class StreetSuggestionFilter extends Filter {

    private OnFindResultsListener mListener;

    public StreetSuggestionFilter(OnFindResultsListener listener) {
        this.mListener = listener;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        List<StreetSuggestion> suggestionList = new ArrayList<>();
        if (constraint != null && constraint.length() >= 3) {
            List<String> streetList = findStreets(stripAccents(constraint.toString()));
            for (String street : streetList) {
                suggestionList.add(new StreetSuggestion(street));
            }

        }
        // Assign the data to the FilterResults
        FilterResults filterResults = new FilterResults();
        filterResults.values = suggestionList;
        filterResults.count = suggestionList.size();
        return filterResults;
    }

    @Override
    public void publishResults(CharSequence constraint, FilterResults results) {
        if (mListener != null) {
            mListener.onResults((List<StreetSuggestion>) results.values);
        }
    }


    /**
     * Get's the list of streets matching a constraint
     * <p/>
     * Example url: //http://gis.mardelplata.gob.ar/opendata/ws.php?
     * +      method=rest&endpoint=callejero_mgp&token=rwef3253465htrt546dcasadg4343&nombre_calle=ind
     *
     * @param constraint
     * @return list of streets
     */
    private static List<String> findStreets(String constraint) {
        return ServiceFacade.getInstance().findStreets(constraint);
    }

    /**
     * Removes the accents to any string
     *
     * @param s
     * @return
     */
    private static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
