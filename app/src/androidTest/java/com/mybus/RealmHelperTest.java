package com.mybus;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.mybus.helper.RealmHelper;
import com.mybus.model.FavoriteLocation;

import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class RealmHelperTest extends InstrumentationTestCase {
    private Context mContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getInstrumentation().getTargetContext();
    }

    public void testSaveFavorite () {
        FavoriteLocation favLocation = new FavoriteLocation("Casa", "9 de Julio", 340, -37.3291053,-59.1336692);
        RealmHelper.getInstance(mContext).saveFavorite(favLocation);
    }

    public void testListFavorites (){
        FavoriteLocation favLocation = new FavoriteLocation("Casa", "9 de Julio", 340, -37.3291053, -59.1336692);
        RealmHelper.getInstance(mContext).saveFavorite(favLocation);
        RealmResults<FavoriteLocation> results = RealmHelper.getInstance(mContext).getAllFavorites();
        Log.i("Favorite: ", results.get(0).toString());
        assertTrue(results.size() > 0);
    }
}
