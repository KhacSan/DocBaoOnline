package vn.edu.hust.soict.khacsan.docbaoonline.Presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.CheckConnection;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments.CustomFragment;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.ReadRSS;
import vn.edu.hust.soict.khacsan.docbaoonline.R;
import vn.edu.hust.soict.khacsan.docbaoonline.View.MainView;

/**
 * Created by San on 12/11/2017.
 */

public class LoadDataBao implements LoadBao {
    MainView mainView;
    Context context;
    private String titlesBaoVnNet[];
    private String titleBaoVnExpress[];
    private String titlesBaoDanTri[];

    private String urlRssBaoVnNet[];
    private String urlRssBaoVnExpress[];
    private String urlRssBaoDanTri[];

    public LoadDataBao(Context context, MainView mainView) {
        this.mainView = mainView;
        this.context = context;
        titlesBaoVnNet = context.getResources().getStringArray(R.array.title_bao_vnnet);
        titleBaoVnExpress = context.getResources().getStringArray(R.array.title_bao_vnex);
        titlesBaoDanTri = context.getResources().getStringArray(R.array.title_bao_dantri);
        urlRssBaoDanTri = context.getResources().getStringArray(R.array.url_bao_dantri);
        urlRssBaoVnExpress = context.getResources().getStringArray(R.array.url_bao_vnex);
        urlRssBaoVnNet = context.getResources().getStringArray(R.array.url_bao_vnnet);
    }

    @Override
    public void LoadFragment(ArrayList<CustomFragment> fragments, int loaiBao) {
        if (loaiBao == 0) {
            for (int i = 0; i < titleBaoVnExpress.length; i++) {
                fragments.add(new CustomFragment(titleBaoVnExpress[i]));
            }
        } else {
            if (loaiBao == 1) {
                for (int i = 0; i < titlesBaoVnNet.length; i++) {
                    fragments.add(new CustomFragment(titlesBaoVnNet[i]));
                }
            } else {
                for (int i = 0; i < titlesBaoDanTri.length; i++) {
                    fragments.add(new CustomFragment(titlesBaoDanTri[i]));
                }
            }
        }
    }


    @Override
    public void LoadArrayTinTuc(int loaiBao, int position) {
        if (!CheckConnection.internetConnectionCheck(context)) {
            mainView.LoadFaiLure(0);
        } else {
            if (loaiBao == 0) {
                new ReadRSS(context,loaiBao,position,mainView).execute(urlRssBaoVnExpress[position]);
            } else {
                if (loaiBao == 1) {
                    new ReadRSS(context,loaiBao,position,mainView).execute(urlRssBaoVnNet[position]);
                } else {
                    new ReadRSS(context,loaiBao,position,mainView).execute(urlRssBaoDanTri[position]);
                }
            }
        }
    }
}
