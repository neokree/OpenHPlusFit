package fit.hplus.bluetooth.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseBean<T> implements JsonLizable {
    private T data;
    private boolean response;
    private int type;

    public boolean isResponse() {
        return this.response;
    }

    public void setResponse(boolean z) {
        this.response = z;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }

    public BaseBean(boolean z, int i, T t) {
        this.response = z;
        this.type = i;
        this.data = t;
    }

    public BaseBean() {
    }

    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("response", this.response);
            jSONObject.put("type", this.type);
            jSONObject.put("data", this.data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
