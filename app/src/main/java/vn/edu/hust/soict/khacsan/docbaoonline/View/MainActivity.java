package vn.edu.hust.soict.khacsan.docbaoonline.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.FragmentAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.Adapter.LoaiBaoAdapter;
import vn.edu.hust.soict.khacsan.docbaoonline.Model.Fragments.CustomFragment;
import vn.edu.hust.soict.khacsan.docbaoonline.Presenter.LoadDataLoaiBao;
import vn.edu.hust.soict.khacsan.docbaoonline.R;


public class MainActivity extends AppCompatActivity implements MainView {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private ListView listViewLoaiBao;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private LoadDataLoaiBao loadDataLoaiBao;
    private FragmentAdapter adapterBaoVn = null, adapterBaoVnNet = null, adapterBaoDanTri = null;
    private ArrayList<CustomFragment> fragmentsBaoVn = null;
    private ArrayList<CustomFragment> fragmentsBaoVnNet = null;
    private ArrayList<CustomFragment> fragmentsBaoDanTri = null;
    private SharedPreferences sharedPreferences;
    private int loaiBao = 0;
    private int pos = 0;
    private boolean isNight = false;
    private LoaiBaoAdapter loaiBaoAdapter;

    private String titlesBaoVnNet[];
    private String titleBaoVnExpress[];
    private String titlesBaoDanTri[];

    private String urlRssBaoVnNet[];
    private String urlRssBaoVnExpress[];
    private String urlRssBaoDanTri[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("isDayNight", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isNight", false)) {
            isNight = true;
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        actionBar();

        titlesBaoVnNet = getApplicationContext().getResources().getStringArray(R.array.title_bao_vnnet);
        titleBaoVnExpress = getApplicationContext().getResources().getStringArray(R.array.title_bao_vnex);
        titlesBaoDanTri = getApplicationContext().getResources().getStringArray(R.array.title_bao_dantri);
        urlRssBaoDanTri = getApplicationContext().getResources().getStringArray(R.array.url_bao_dantri);
        urlRssBaoVnExpress = getApplicationContext().getResources().getStringArray(R.array.url_bao_vnex);
        urlRssBaoVnNet = getApplicationContext().getResources().getStringArray(R.array.url_bao_vnnet);

        loadDataLoaiBao.LoadData();
        loaiBao = 0;


        if(adapterBaoVn == null){
            fragmentsBaoVn = new ArrayList<>();
            for(int i = 0; i < titleBaoVnExpress.length; i++){
                CustomFragment fragment = CustomFragment.newInstance(titleBaoVnExpress[i],urlRssBaoVnExpress[i]);
                fragmentsBaoVn.add(fragment);
            }
            adapterBaoVn = new FragmentAdapter(getSupportFragmentManager(), fragmentsBaoVn);
        }

        listViewLoaiBao.setOnItemClickListener(listViewLoaiBaoListener);
        tabLayout.addOnTabSelectedListener(onTabSelected);


    }


    public TabLayout.OnTabSelectedListener onTabSelected = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();

//            //loadDataBao.LoadArrayTinTuc(loaiBao,position);
//            if (loaiBao == 0 && fragmentsBaoVn != null && fragmentsBaoVn.size() > position &&fragmentsBaoVn.get(position).getTinTucs().size() == 0) {
//                loadDataBao.LoadArrayTinTuc(0, position);
//            } else {
//                if (loaiBao == 1 && fragmentsBaoVnNet != null && fragmentsBaoVnNet.size() > position &&fragmentsBaoVnNet.get(position).getTinTucs().size() == 0) {
//                    loadDataBao.LoadArrayTinTuc(1, position);
//                } else {
//                    if (loaiBao == 2 && fragmentsBaoDanTri != null &&fragmentsBaoDanTri.size() > position &&  fragmentsBaoDanTri.get(position).getTinTucs().size() == 0) {
//                        loadDataBao.LoadArrayTinTuc(2, position);
//                    }
//                }
//            }
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
                loaiBaoAdapter.setSelect(0);
            } else {
                if (position == 1) {
                    loaiBaoAdapter.setSelect(1);
                    if (adapterBaoVnNet == null) {
                        fragmentsBaoVnNet = new ArrayList<>();
                        for(int i = 0; i < titlesBaoVnNet.length; i++){
                            CustomFragment fragment = CustomFragment.newInstance(titlesBaoVnNet[i],urlRssBaoVnNet[i]);
                            fragmentsBaoVnNet.add(fragment);
                        }
                        adapterBaoVnNet = new FragmentAdapter(getSupportFragmentManager(), fragmentsBaoVnNet);
                    }
                    viewPager.setAdapter(adapterBaoVnNet);

                } else {
                    loaiBaoAdapter.setSelect(2);
                    if (adapterBaoDanTri == null) {
                        fragmentsBaoDanTri = new ArrayList<>();
                        for(int i = 0; i < titlesBaoDanTri.length; i++){
                            CustomFragment fragment = CustomFragment.newInstance(titlesBaoDanTri[i],urlRssBaoDanTri[i]);
                            fragmentsBaoDanTri.add(fragment);
                        }
                        adapterBaoDanTri = new FragmentAdapter(getSupportFragmentManager(), fragmentsBaoDanTri);
                    }
                    viewPager.setAdapter(adapterBaoDanTri);
                }
            }
        }
    };


    private void actionBar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(loaiBao == 0) viewPager.setAdapter(adapterBaoVn);
        if(loaiBao == 1) viewPager.setAdapter(adapterBaoVnNet);
        if(loaiBao == 2) viewPager.setAdapter(adapterBaoDanTri);
        if(loaiBaoAdapter != null) loaiBaoAdapter.setSelect(loaiBao);
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("LOAI_BAO",loaiBao);
        outState.putInt("POSITION",tabLayout.getSelectedTabPosition());
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
        fragmentsBaoVn = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if(!isNight){
            menu.getItem(0).setTitle("Chế độ ban đêm");
        }else {
            menu.getItem(0).setTitle("Chế độ ban ngày");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            if (item.getTitle().equals("Chế độ ban đêm")) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNight", true);
                editor.apply();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNight", false);
                editor.apply();
            }

            resetApp();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetApp() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    @Override
    public void LoadLoaiBao(LoaiBaoAdapter adapter) {
        loaiBaoAdapter = adapter;
        loaiBaoAdapter.setSelect(0);
        listViewLoaiBao.setAdapter(loaiBaoAdapter);
    }


    @Override
    public void LoadFaiLure(int errorCode) {
        if (errorCode == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
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
