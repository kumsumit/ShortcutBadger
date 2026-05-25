package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

/**
 * @author Jason Ling
 */
public class HuaweiHomeBadger implements Badger {
    private static final String HUAWEI_BADGE_URI = "content://com.huawei.android.launcher.settings/badge/";
    private static final String HONOR_BADGE_URI = "content://com.hihonor.android.launcher.settings/badge/";
    private static final String CHANGE_BADGE_METHOD = "change_badge";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Bundle localBundle = new Bundle();
        localBundle.putString("package", context.getPackageName());
        localBundle.putString("class", componentName.getClassName());
        localBundle.putInt("badgenumber", badgeCount);
        try {
            context.getContentResolver().call(Uri.parse(HUAWEI_BADGE_URI), CHANGE_BADGE_METHOD, null, localBundle);
        } catch (Exception huaweiException) {
            try {
                context.getContentResolver().call(Uri.parse(HONOR_BADGE_URI), CHANGE_BADGE_METHOD, null, localBundle);
            } catch (Exception honorException) {
                throw new ShortcutBadgeException("Unable to execute Huawei/Honor badge", honorException);
            }
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.huawei.android.launcher",
                "com.hihonor.android.launcher"
        );
    }
}
