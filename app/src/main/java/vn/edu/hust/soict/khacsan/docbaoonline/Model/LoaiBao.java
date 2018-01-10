package vn.edu.hust.soict.khacsan.docbaoonline.Model;

/**
 * Created by San on 12/11/2017.
 */

public class LoaiBao {
    private String tenBao, icon;

    public LoaiBao(String tenBao, String icon) {
        this.tenBao = tenBao;
        this.icon = icon;
    }

    public String getTenBao() {
        return tenBao;
    }

    public void setTenBao(String tenBao) {
        this.tenBao = tenBao;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
