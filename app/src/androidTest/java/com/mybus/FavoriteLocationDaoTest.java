package com.mybus;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.mybus.dao.FavoriteLocationDao;
import com.mybus.model.FavoriteLocation;

import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class FavoriteLocationDaoTest extends InstrumentationTestCase {
    private Context mContext;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getInstrumentation().getTargetContext();
    }

    public void test1SaveFavorite() {
        FavoriteLocation favLocation = new FavoriteLocation("Casa", "9 de Julio", 500, -37.3291053, -59.1336692);
        FavoriteLocationDao.getInstance(mContext).saveOrUpdate(favLocation);
        Log.i("TestSaveFavorite", "Favorite saved: " + favLocation.toString());
    }

    public void test2GetFavoriteByName() {
        FavoriteLocation favLocation = FavoriteLocationDao.getInstance(mContext).getById("Casa");
        assertTrue(favLocation != null);
        Log.i("TestGetFavoriteByName", "Favorite (name == 'Casa'): " + favLocation.toString());
    }

    public void test3UpdateUsageCount() {
        FavoriteLocationDao.getInstance(mContext).updateItemUsageCount("Casa");
        assertTrue(FavoriteLocationDao.getInstance(mContext).getById("Casa").getUsageCount() > 0);
    }

    public void test4ListFavorites() {
        RealmResults<FavoriteLocation> results = FavoriteLocationDao.getInstance(mContext).getAll();
        assertTrue(results.size() > 0);
        Log.i("TestListFavorites", "All Favorites");
        for (FavoriteLocation fav : results) {
            Log.i("TestListFavorites", "Favorite: " + fav.toString());
        }
    }
}
