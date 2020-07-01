package fit.hplus.bluetooth.dial;

import fit.hplus.bluetooth.bean.PushDialLayoutBean;
import fit.hplus.bluetooth.bean.PushDialResBean;
import java.util.List;

public interface IDialController {
    void getDialFile(int i, int i2, DialFileStatusCallback dialFileStatusCallback);

    void pushDial(List<PushDialLayoutBean> list, List<PushDialResBean> list2, DialFileCallback dialFileCallback);

    void pushDialWithIndex(int i, DialFileCallback dialFileCallback);
}
