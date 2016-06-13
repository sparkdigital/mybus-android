package com.mybus;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.mybus.dao.RecentLocationDao;
import com.mybus.model.RecentLocation;
import com.mybus.model.RecentType;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class RecentLocationDaoTest extends InstrumentationTestCase {
    private Context mContext;
    private static final String TYPE_FIELD = "type";
    private static final String STREET_FIELD = "streetName";
    private static final String STREET_VALUE = "Test_Address 1100";
    private static final String STREET_VALUE_2 = "Test_Address_2 2200";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getInstrumentation().getTargetContext();
    }

    public void test1SaveRecent() {
        RecentLocation recLocation = new RecentLocation(RecentType.ORIGIN, STREET_VALUE, -37.3291053, -59.1336692);
        RecentLocation recLocation2 = new RecentLocation(RecentType.ORIGIN, STREET_VALUE_2, -37.3291053, -59.1336692);
        boolean success = RecentLocationDao.getInstance(mContext).saveOrUpdate(recLocation);
        boolean success2 = RecentLocationDao.getInstance(mContext).saveOrUpdate(recLocation2);
        assertTrue(success);
        assertTrue(success2);
        Log.i("TestSaveRecent", "Recents save " + (success ? "success" : "failed"));
    }

    public void test2GetRecentByAddress() {
        RecentLocation recLocation = RecentLocationDao.getInstance(mContext).getByField(STREET_FIELD, STREET_VALUE);
        assertNotNull(recLocation);
        Log.i("TestGetRecentByAddress", "Recent: " + recLocation.toString());
    }

    public void test3UpdateUsageCount() {
        RecentLocation recLocation = RecentLocationDao.getInstance(mContext).getByField(STREET_FIELD, STREET_VALUE);
        assertNotNull(recLocation);
        Long id = recLocation.getId();
        RecentLocationDao.getInstance(mContext).updateItemUsageCount(id);
        RecentLocation recUpdated = RecentLocationDao.getInstance(mContext).getById(id);
        Log.i("TestUpdateUsageCount", "UsageCount: " + recUpdated.getUsageCount());
        assertTrue(recUpdated.getUsageCount() > 0);
    }

    public void test4ListHistory() {
        RealmResults<RecentLocation> results = RecentLocationDao.getInstance(mContext).getAll();
        assertTrue(results.size() > 0);
        Log.i("TestListHistory", "History");
        for (RecentLocation recent : results) {
            Log.i("TestListHistory", "Recent: " + recent.toString());
        }
    }

    public void test5ListOriginHistory() {
        List<RecentLocation> results = RecentLocationDao.getInstance(mContext).getAllByField(TYPE_FIELD, RecentType.ORIGIN);
        assertTrue(results.size() > 0);
        Log.i("TestOriginHistory", "Origin History");
        for (RecentLocation recent : results) {
            Log.i("TestOriginHistory", "Recent: " + recent.toString());
        }
    }

    public void test6RemoveRecentUsedForTests() {
        RecentLocation recent = RecentLocationDao.getInstance(mContext).getByField(STREET_FIELD, STREET_VALUE);
        assertNotNull(recent);
        assertTrue(RecentLocationDao.getInstance(mContext).remove(recent.getId()));

        RecentLocation recent2 = RecentLocationDao.getInstance(mContext).getByField(STREET_FIELD, STREET_VALUE_2);
        assertNotNull(recent2);
        assertTrue(RecentLocationDao.getInstance(mContext).remove(recent2.getId()));
    }
}
