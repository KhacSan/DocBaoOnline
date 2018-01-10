package vn.edu.hust.soict.khacsan.docbaoonline.Presenter;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments.CustomFragment;

/**
 * Created by San on 12/11/2017.
 */

public interface LoadBao {
    void LoadFragment(ArrayList<CustomFragment> fragments,int loaiBao);
    void LoadArrayTinTuc(int LoaiBao,int position);
}
