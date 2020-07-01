package fit.hplus.bluetooth.util;

import android.text.TextUtils;
import fit.hplus.bluetooth.bean.PushDialBean;
import fit.hplus.bluetooth.bean.PushDialLayoutBean;
import fit.hplus.bluetooth.bean.PushDialResBean;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DialFileUtil {
    public static List<PushDialBean> analysisDialJson(String str) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray optJSONArray = new JSONObject(str).optJSONArray("ui_files");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        PushDialBean pushDialBean = new PushDialBean();
                        JSONObject jSONObject = optJSONArray.getJSONObject(i);
                        pushDialBean.setNum(i);
                        pushDialBean.setDrawableFile(jSONObject.optString("effect_file"));
                        pushDialBean.setResFile(jSONObject.optString("res_file"));
                        pushDialBean.setLayoutFile(jSONObject.optString("layout_file"));
                        arrayList.add(pushDialBean);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static List<PushDialLayoutBean> analysisLayoutJson(String str) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                if (jSONArray.length() > 0) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        jSONArray.getJSONObject(i);
                        arrayList.add(new PushDialLayoutBean());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static List<PushDialResBean> analysisResJson(String str) {
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                if (jSONArray.length() > 0) {
                    for (int i = 0; i < jSONArray.length(); i++) {
                        jSONArray.getJSONObject(i);
                        arrayList.add(new PushDialResBean());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}
