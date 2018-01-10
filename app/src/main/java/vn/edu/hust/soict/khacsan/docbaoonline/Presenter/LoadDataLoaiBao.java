package vn.edu.hust.soict.khacsan.docbaoonline.Presenter;

import android.content.Context;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.LoaiBaoAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.CheckConnection;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.LoaiBao;
import vn.edu.hust.soict.khacsan.docbaoonline.View.MainView;


/**
 * Created by San on 12/11/2017.
 */

public class LoadDataLoaiBao implements LoadLoaiBao{
    private Context context;
    private ArrayList<LoaiBao> loaiBaos;
    private MainView mainView;

    public LoadDataLoaiBao(Context context,MainView mainView) {
        this.context = context;
        loaiBaos = new ArrayList<>();
        this.mainView = mainView;

    }

    @Override
    public void LoadData() {
        if(CheckConnection.internetConnectionCheck(context)) {
            loaiBaos.add(new LoaiBao("VnExpress", "http://49b5af5c747982f45fd7-dec8f175b0901987f30693abc46dc353.r35.cf2.rackcdn.com/icon/13/08/16/9eb2a67a45e18b0d2c45a59881863b76.png"));
            loaiBaos.add(new LoaiBao("VietNamNet", "http://allaboutwindowsphone.com/images/appicons/101858.png"));
            loaiBaos.add(new LoaiBao("Dân Trí", "https://lh4.ggpht.com/2iubcNdVuHLWE90hjlKT7SF5z_kVTcO1iLp_zLE6rEqTMQLx3WOlcxLxF2pbZk3TMpU=w300"));
            LoaiBaoAdapter baoAdapter = new LoaiBaoAdapter(loaiBaos,context);
            mainView.LoadLoaiBao(baoAdapter);
        }else{
            mainView.LoadFaiLure(0);
        }

    }
}
