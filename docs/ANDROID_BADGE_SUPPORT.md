# Android Badge Support Notes

Android does not provide a public API that lets an app directly set a numeric
launcher icon badge on every device. Android 8.0 and newer provide notification
badges/dots through active notifications; numeric display is still controlled by
the launcher or OEM skin.

## Supported Through Launcher-Specific APIs

These launchers have implementation paths in ShortcutBadger:

| Family | Packages / routing | Notes |
| --- | --- | --- |
| Samsung | `com.sec.android.app.launcher`, `com.sec.android.app.twlauncher` | Older Samsung provider path. Newer Samsung versions generally badge from active notifications. |
| Xiaomi / Redmi / POCO | `com.miui.*`, Xiaomi/Redmi/POCO build fallback | MIUI/HyperOS relies on proprietary notification hooks and user notification settings. |
| OPPO / Realme / OnePlus / OPlus | `com.oppo.launcher`, `com.oplus.launcher`, `com.heytap.launcher`, `net.oneplus.launcher`, OPPO/Realme/OnePlus/OPlus build fallback | Uses the OPPO/ColorOS badge provider path when present. Some modern devices expose only system-managed notification badges. |
| Vivo / iQOO | `com.vivo.launcher`, `com.bbk.launcher2`, Vivo/iQOO build fallback | Uses Vivo's launcher broadcast path. |
| Huawei / Honor | `com.huawei.android.launcher`, `com.hihonor.android.launcher`, Huawei/Honor build fallback | Uses Huawei launcher badge provider path. |
| Transsion brands | `com.transsion.hilauncher`, `com.transsion.itel.launcher`, TECNO/Infinix/itel/Transsion build fallback | Covers Tecno, Infinix and itel launcher families when their broadcast API is available. |
| LG, Sony, HTC, ASUS, ZTE, ZUK | Existing launcher packages and manufacturer fallbacks where available | Legacy OEM-specific providers/broadcasts. |
| Nova, ADW, Apex, KISS, LaunchTime, Yandex, EverythingMe | Existing third-party launcher packages | Depends on those launchers still exposing the historical provider/broadcast. |

## Use Notification Badges Instead

For Pixel Launcher, Nothing Launcher, Motorola's mostly stock launcher, and many
recent OEM launcher versions, apps should use normal notifications. Android shows
notification dots by default on launchers that support them. Numeric counts, when
available, are a launcher or OEM setting and are not guaranteed to be controlled
by this library.

For app teams:

1. Keep notification channels badge-enabled unless the notification should not
   affect the app icon badge.
2. Clear or update notifications when content is read, because modern launchers
   often derive badges from active notifications.
3. Use `ShortcutBadger.applyCount(...)` only as a best-effort compatibility path
   for launchers with known private APIs.
