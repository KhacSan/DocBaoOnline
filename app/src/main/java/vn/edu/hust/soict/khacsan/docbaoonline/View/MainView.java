package vn.edu.hust.soict.khacsan.docbaoonline.View;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.LoaiBaoAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.TinTuc;

/**
 * Created by San on 12/11/2017.
 */

public interface MainView {
    void LoadLoaiBao(LoaiBaoAdapter adapter);
    void LoadFaiLure(int errorCode);
}
