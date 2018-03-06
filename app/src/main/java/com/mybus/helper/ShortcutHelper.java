package com.mybus.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import com.mybus.R;
import com.mybus.activity.MainActivity;
import com.mybus.dao.RecentLocationDao;
import com.mybus.model.RecentLocation;
import com.mybus.model.SearchType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldimitroff on 05/03/18.
 */
public final class ShortcutHelper {

    private ShortcutHelper() {
    }

    public static void updateRecentLocationShortcuts(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return;
        }

        List<RecentLocation> mRecentLocations = RecentLocationDao.getInstance(context).findAllByType(SearchType.DESTINATION);
        if (mRecentLocations == null) {
            return;
        }

        List<ShortcutInfo> shortcutInfoList = new ArrayList<>();
        int count = 1;
        for (RecentLocation recentLocation : mRecentLocations) {
            if (count <= 3) {
                shortcutInfoList.add(getShortCutByRecent(context, recentLocation));
            }
            count++;
        }

        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager != null && !shortcutInfoList.isEmpty()) {
            shortcutManager.setDynamicShortcuts(shortcutInfoList);
        }
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private static ShortcutInfo getShortCutByRecent(Context context, RecentLocation recentLocation) {
        return new ShortcutInfo.Builder(context, String.valueOf(recentLocation.getId()))
                .setShortLabel("Ir a: " + recentLocation.getAddress())
                .setIcon(Icon.createWithResource(context, R.drawable.from_arrow))
                .setIntent(
                        new Intent(context, MainActivity.class)
                                .setAction(MainActivity.SHORTCUT_INTENT_ACTION)
                                .putExtra(MainActivity.SHORTCUT_GEOLOCATION_EXTRA, recentLocation.getId()))
                .build();
    }
}
