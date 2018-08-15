package com.adrian_971029.infobook;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.infobook.adapter.MainAdapter;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.utils.Constants;
import com.adrian_971029.infobook.utils.ReadVolumeJson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_SemConexao)
    ImageView imgSemConexao;
    @BindView(R.id.tv_semConexao)
    TextView mTextMensagemSemConexao;
    @BindView(R.id.tv_semFavoritos)
    TextView mTextMensagemSemFavoritos;
    @BindView(R.id.img_atualizar)
    ImageView imgAtualizar;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_aguarde)
    TextView mTextMensagem;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private Volume volume;
    private ArrayList<Item> items;
    private MainAdapter mAdapter;
    private Resources resources;
    private ActionBarDrawerToggle mToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        resources = this.getResources();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("InfoBook");
        mToolbar.setTitleTextColor(resources.getColor(R.color.textAndIcons));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToogle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view)
            {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                supportInvalidateOptionsMenu();
            }
        };
        mToogle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        exibirProgreso(true);
        crearLayout(Constants.ADVENTURE);

    }

    @OnClick(R.id.img_atualizar)
    void onclickImageAtualizar(){
        if (temConexao(this)) {
            exibirProgreso(true);
            exibirMensagemSemConexao(false);
            mRecyclerView.getRecycledViewPool().clear();
            items.clear();
            volume = null;
            crearLayout(Constants.ADVENTURE);
        } else {
            Toast.makeText(this, R.string.lbl_sem_conex_internet,Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new MainAdapter(this,items);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void crearLayout(String category){
        if(items == null){
            items = new ArrayList<Item>();
        }
        setupRecycler();

        if(temConexao(this)){
            ReadVolumeJson readVolumeJson = new ReadVolumeJson(volume,items,mAdapter,mProgressBar,mTextMensagem);
            readVolumeJson.chamaJSon(category);
        }
        else{
            exibirProgreso(false);
            exibirMensagemSemConexao(true);
        }
    }

    private void exibirProgreso(boolean exibir){
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);

        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void exibirMensagemSemConexao(boolean exibir) {
        imgSemConexao.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mTextMensagemSemConexao.setVisibility(exibir ? View.VISIBLE : View.GONE);
        imgAtualizar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mToogle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_item_action:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.ACTION);
                break;
            case R.id.nav_item_adventure:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.ADVENTURE);
                break;
            case R.id.nav_item_drama:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.DRAMA);
                break;
            case R.id.nav_item_fantasy:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.FANTASY);
                break;
            case R.id.nav_item_history:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.HISTORY);
                break;
            case R.id.nav_item_horror:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.HORROR);
                break;
            case R.id.nav_item_mystery:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.MYSTERY);
                break;
            case R.id.nav_item_religion:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.RELIGION);
                break;
            case R.id.nav_item_romance:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.ROMANCE);
                break;
            case R.id.nav_item_science:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.SCIENCE);
                break;
            case R.id.nav_item_science_fiction:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                crearLayout(Constants.SCIENCE_FICTION);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
