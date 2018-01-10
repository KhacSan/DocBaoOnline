package vn.edu.hust.soict.khacsan.docbaoonline.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.FragmentAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.LoaiBaoAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments.CustomFragment;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.TinTuc;
import vn.edu.hust.soict.khacsan.docbaoonline.Presenter.LoadDataBao;
import vn.edu.hust.soict.khacsan.docbaoonline.Presenter.LoadDataLoaiBao;
import vn.edu.hust.soict.khacsan.docbaoonline.R;


public class MainActivity extends AppCompatActivity implements MainView {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private ListView listViewLoaiBao;
    private static TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private LoadDataLoaiBao loadDataLoaiBao;
    private static LoadDataBao loadDataBao;
    private FragmentAdapter adapterBaoVn = null, adapterBaoVnNet = null, adapterBaoDanTri = null;
    private ArrayList<CustomFragment> fragmentsBaoVn = null;
    private ArrayList<CustomFragment> fragmentsBaoVnNet = null;
    private ArrayList<CustomFragment> fragmentsBaoDanTri = null;
    SharedPreferences sharedPreferences;
    private static int loaiBao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("isDayNight", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isNight", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        actionBar();
        loadDataBao.LoadFragment(fragmentsBaoVn, 0);

        if(adapterBaoVn == null){
            adapterBaoVn = new FragmentAdapter(getSupportFragmentManager(), fragmentsBaoVn);
        }

        viewPager.setAdapter(adapterBaoVn);
        loadDataBao.LoadArrayTinTuc(0, 0);
        loadDataLoaiBao.LoadData();

        listViewLoaiBao.setOnItemClickListener(listViewLoaiBaoListener);
        tabLayout.addOnTabSelectedListener(onTabSelected);

    }

    public TabLayout.OnTabSelectedListener onTabSelected = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();

            //loadDataBao.LoadArrayTinTuc(loaiBao,position);
            if (loaiBao == 0 && fragmentsBaoVn != null && fragmentsBaoVn.get(position).getTinTucs().size() == 0) {
                loadDataBao.LoadArrayTinTuc(0, position);
            } else {
                if (loaiBao == 1 && fragmentsBaoVnNet != null && fragmentsBaoVnNet.get(position).getTinTucs().size() == 0) {
                    loadDataBao.LoadArrayTinTuc(1, position);
                } else {
                    if (loaiBao == 2 && fragmentsBaoDanTri != null && fragmentsBaoDanTri.get(position).getTinTucs().size() == 0) {
                        loadDataBao.LoadArrayTinTuc(2, position);
                    }
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public AdapterView.OnItemClickListener listViewLoaiBaoListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawerLayout.closeDrawer(GravityCompat.START);
            loaiBao = position;
            if (position == 0) {
                viewPager.setAdapter(adapterBaoVn);
            } else {
                if (position == 1) {
                    if (adapterBaoVnNet == null) {
                        fragmentsBaoVnNet = new ArrayList<>();
                        loadDataBao.LoadFragment(fragmentsBaoVnNet, position);
                        adapterBaoVnNet = new FragmentAdapter(getSupportFragmentManager(), fragmentsBaoVnNet);
                        loadDataBao.LoadArrayTinTuc(position, 0);
                    }
                    viewPager.setAdapter(adapterBaoVnNet);

                } else {
                    if (adapterBaoDanTri == null) {
                        fragmentsBaoDanTri = new ArrayList<>();
                        loadDataBao.LoadFragment(fragmentsBaoDanTri, position);
                        adapterBaoDanTri = new FragmentAdapter(getSupportFragmentManager(), fragmentsBaoDanTri);
                        loadDataBao.LoadArrayTinTuc(position, 0);
                    }
                    viewPager.setAdapter(adapterBaoDanTri);
                }
            }
        }
    };


    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation);
        listViewLoaiBao = findViewById(R.id.listViewLoaiBao);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        drawerLayout = findViewById(R.id.drawerLayout);
        tabLayout.setupWithViewPager(viewPager);
        loadDataLoaiBao = new LoadDataLoaiBao(MainActivity.this, this);
        loadDataBao = new LoadDataBao(MainActivity.this, this);
        fragmentsBaoVn = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {

            if (item.getTitle().equals("Chế độ ban đêm")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNight", true);
                editor.commit();
                item.setTitle("Chế độ ban ngày");
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNight", false);
                editor.commit();
                item.setTitle("Chế độ ban đêm");
            }

            recreate();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetApp() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    public static void Refresh() {
        int position = tabLayout.getSelectedTabPosition();
        loadDataBao.LoadArrayTinTuc(loaiBao, position);
    }


    @Override
    public void LoadLoaiBao(LoaiBaoAdapter adapter) {
        listViewLoaiBao.setAdapter(adapter);
    }

    @Override
    public void LoadTinTuc(ArrayList<TinTuc> tinTucs, int loaiBao, int position) {
        if (loaiBao == 0) {
            fragmentsBaoVn.get(position).setTinTucs(tinTucs);
        } else if (loaiBao == 1) {
            fragmentsBaoVnNet.get(position).setTinTucs(tinTucs);
        } else fragmentsBaoDanTri.get(position).setTinTucs(tinTucs);
    }

    @Override
    public void LoadFaiLure(int errorCode) {
        if (errorCode == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Thiết bị không có internet!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
