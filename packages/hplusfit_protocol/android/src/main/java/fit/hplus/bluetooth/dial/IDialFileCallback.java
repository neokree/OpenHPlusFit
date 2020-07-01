package fit.hplus.bluetooth.dial;

import fit.hplus.bluetooth.bean.PushDialLayoutBean;
import fit.hplus.bluetooth.bean.PushDialResBean;
import java.util.List;

public interface IDialFileCallback {
    void notFileWritePermission();

    void notInternetPermission();

    void onFailDialFile(String str);

    void onLayoutData(List<PushDialLayoutBean> list);

    void onResData(List<PushDialResBean> list);
}
