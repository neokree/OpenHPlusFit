package fit.hplus.bluetooth.dial;

import fit.hplus.bluetooth.bean.PushDialBean;
import java.util.List;

public interface OnDialFileCallback {
    void onDialFile(List<PushDialBean> list);
}
