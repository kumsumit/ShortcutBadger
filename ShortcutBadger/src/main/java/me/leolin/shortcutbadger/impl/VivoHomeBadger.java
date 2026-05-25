package me.leolin.shortcutbadger.impl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.Arrays;
import java.util.List;

import me.leolin.shortcutbadger.Badger;
import me.leolin.shortcutbadger.ShortcutBadgeException;

/**
 * @author leolin
 * see also https://dev.vivo.com.cn/documentCenter/doc/459.
 */
public class VivoHomeBadger implements Badger {
    private static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;

    @Override
    @SuppressLint("WrongConstant")
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra("className", componentName.getClassName());
        intent.putExtra("notificationNum", badgeCount);
        intent.addFlags(FLAG_RECEIVER_INCLUDE_BACKGROUND);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        context.sendBroadcast(intent);
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
            "com.vivo.launcher",
            "com.bbk.launcher2"
        );
    }
}
