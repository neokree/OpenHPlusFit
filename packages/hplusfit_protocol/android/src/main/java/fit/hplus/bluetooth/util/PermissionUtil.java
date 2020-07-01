package fit.hplus.bluetooth.util;

public class PermissionUtil {
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0041 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean checkSelfPermission(android.content.Context r6, java.lang.String r7) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            r3 = 1
            if (r1 < r2) goto L_0x0033
            java.lang.String r1 = "android.content.Context"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = "checkSelfPermission"
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ all -> 0x002e }
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r0] = r5     // Catch:{ all -> 0x002e }
            java.lang.reflect.Method r1 = r1.getMethod(r2, r4)     // Catch:{ all -> 0x002e }
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch:{ all -> 0x002e }
            r2[r0] = r7     // Catch:{ all -> 0x002e }
            java.lang.Object r6 = r1.invoke(r6, r2)     // Catch:{ all -> 0x002e }
            java.lang.Integer r6 = (java.lang.Integer) r6     // Catch:{ all -> 0x002e }
            int r6 = r6.intValue()     // Catch:{ all -> 0x002e }
            if (r6 != 0) goto L_0x0042
            goto L_0x0041
        L_0x002e:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x0042
        L_0x0033:
            android.content.pm.PackageManager r1 = r6.getPackageManager()
            java.lang.String r6 = r6.getPackageName()
            int r6 = r1.checkPermission(r7, r6)
            if (r6 != 0) goto L_0x0042
        L_0x0041:
            r0 = 1
        L_0x0042:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: fit.hplus.bluetooth.util.PermissionUtil.checkSelfPermission(android.content.Context, java.lang.String):boolean");
    }
}
