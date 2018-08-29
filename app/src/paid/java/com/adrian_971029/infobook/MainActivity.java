package com.adrian_971029.infobook;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.infobook.adapter.FavoritesAdapter;
import com.adrian_971029.infobook.adapter.MainAdapter;
import com.adrian_971029.infobook.data.ContentProviderAccess;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.adrian_971029.infobook.utils.Constants;
import com.adrian_971029.infobook.utils.ReadVolumeJson;
import com.adrian_971029.infobook.widget.InfoBookWidgetProvider;
import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<List<VolumeInfo>> {

    public static final int OPERATION_SEARCH_LOADER = 25;
    public static final String BOOK_PREFERENCE = "book_preference";
    public static final String FAVORITE_PREFERENCE = "favorite_preference";
    public static final String KEY_RECYCLER_STATE = "recycler_state";
    public static final String STATE_ITEMS = "state_items";
    public static final String STATE_FAVORITES = "state_items_favorites";

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
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private Volume volume;
    private ArrayList<Item> items;
    private ArrayList<VolumeInfo> volumeInfoArrayList;
    private MainAdapter mAdapter;
    private FavoritesAdapter mFavoritesAdapter;
    private Resources resources;
    private ActionBarDrawerToggle mToogle;
    private ContentProviderAccess providerAccess;
    private Context context;
    protected SharedPreferences sharedPrefs;
    private RecyclerView.LayoutManager layoutManager;
    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        resources = this.getResources();
        context = getApplicationContext();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        items = new ArrayList<Item>();
        volumeInfoArrayList = new ArrayList<VolumeInfo>();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("InfoBook");
        mToolbar.setTitleTextColor(resources.getColor(R.color.textAndIcons));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Typeface fontExpanded = Typer.set(this).getFont(Font.ROBOTO_CONDENSED_BOLD);
        Typeface fontCollapsed = Typer.set(this).getFont(Font.ROBOTO_BOLD);
        collapsingToolbarLayout.setCollapsedTitleTypeface(fontCollapsed);
        collapsingToolbarLayout.setExpandedTitleTypeface(fontExpanded);
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
        atualizarWidget();
        if(savedInstanceState != null) {
            if (savedInstanceState.getParcelable(KEY_RECYCLER_STATE) != null) {
                if (sharedPrefs != null) {
                    if(sharedPrefs.getString(BOOK_PREFERENCE,Constants.ADVENTURE).equals(FAVORITE_PREFERENCE)) {
                        mListState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
                        items.clear();
                        volumeInfoArrayList = savedInstanceState.getParcelableArrayList(STATE_FAVORITES);
                        setupRecyclerFavorites();
                    } else {
                        mListState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
                        items = savedInstanceState.getParcelableArrayList(STATE_ITEMS);
                        volumeInfoArrayList.clear();
                        setupRecycler();
                    }
                } else {
                    mListState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);
                    volumeInfoArrayList.clear();
                    items = savedInstanceState.getParcelableArrayList(STATE_ITEMS);
                    setupRecycler();
                }
                layoutManager.onRestoreInstanceState(mListState);
            }
        } else {
            exibirProgreso(true);
            controlBookPreference();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListState != null) {
        } else {
            controlBookPreference();
        }
    }

    @OnClick(R.id.img_atualizar)
    void onclickImageAtualizar(){
        if (temConexao(this)) {
            exibirProgreso(true);
            exibirMensagemSemConexao(false);
            mRecyclerView.getRecycledViewPool().clear();
            items.clear();
            volume = null;
            controlBookPreference();
        } else {
            Toast.makeText(this, R.string.lbl_sem_conex_internet,Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecycler() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new MainAdapter(this,items);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupRecyclerFavorites() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mFavoritesAdapter = new FavoritesAdapter(this,volumeInfoArrayList);
        mRecyclerView.setAdapter(mFavoritesAdapter);
    }

    private void controlBookPreference() {
        if(sharedPrefs != null) {
            if(sharedPrefs.getString(BOOK_PREFERENCE,Constants.ADVENTURE).equals(FAVORITE_PREFERENCE)) {
                crearLayoutFavorites();
            } else {
                crearLayout(sharedPrefs.getString(BOOK_PREFERENCE,Constants.ADVENTURE));
            }
        } else {
            crearLayout(Constants.ADVENTURE);
        }
    }

    private void crearLayout(String category){
        mTextMensagemSemFavoritos.setVisibility(View.GONE);
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

    private void crearLayoutFavorites(){
        mTextMensagemSemFavoritos.setVisibility(View.GONE);
        if(volumeInfoArrayList == null){
            volumeInfoArrayList = new ArrayList<VolumeInfo>();
        }
        setupRecyclerFavorites();
        initLoader();
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
            case R.id.nav_item_favoritos:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(FAVORITE_PREFERENCE);
                crearLayoutFavorites();
                break;
            case R.id.nav_item_map:
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_item_action:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.ACTION);
                crearLayout(Constants.ACTION);
                break;
            case R.id.nav_item_adventure:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.ADVENTURE);
                crearLayout(Constants.ADVENTURE);
                break;
            case R.id.nav_item_drama:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.DRAMA);
                crearLayout(Constants.DRAMA);
                break;
            case R.id.nav_item_fantasy:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.FANTASY);
                crearLayout(Constants.FANTASY);
                break;
            case R.id.nav_item_history:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.HISTORY);
                crearLayout(Constants.HISTORY);
                break;
            case R.id.nav_item_horror:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.HORROR);
                crearLayout(Constants.HORROR);
                break;
            case R.id.nav_item_mystery:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.MYSTERY);
                crearLayout(Constants.MYSTERY);
                break;
            case R.id.nav_item_religion:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.RELIGION);
                crearLayout(Constants.RELIGION);
                break;
            case R.id.nav_item_romance:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.ROMANCE);
                crearLayout(Constants.ROMANCE);
                break;
            case R.id.nav_item_science:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.SCIENCE);
                crearLayout(Constants.SCIENCE);
                break;
            case R.id.nav_item_science_fiction:
                drawerLayout.closeDrawer(GravityCompat.START);
                mRecyclerView.getRecycledViewPool().clear();
                items.clear();
                volume = null;
                exibirProgreso(true);
                guardandoUltimaPreferencia(Constants.SCIENCE_FICTION);
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


    @NonNull
    @Override
    public Loader<List<VolumeInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<VolumeInfo>>(context) {
            @Nullable
            @Override
            public List<VolumeInfo> loadInBackground() {
                return providerAccess.buscarTodos(context);
            }

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<VolumeInfo>> loader, List<VolumeInfo> data) {
        if(data != null){
            if(items.size() == 0) {
                if(data.size() > 0){
                    exibirProgreso(false);
                    mTextMensagemSemFavoritos.setVisibility(View.GONE);
                    volumeInfoArrayList.clear();
                    volumeInfoArrayList.addAll(data);
                    mFavoritesAdapter.notifyDataSetChanged();
                }else {
                    mTextMensagemSemFavoritos.setVisibility(View.VISIBLE);
                    volumeInfoArrayList.clear();
                    mFavoritesAdapter.notifyDataSetChanged();
                    exibirProgreso(false);
                }
            }
        }
        else {
            if(items.size() == 0) {
                mTextMensagemSemFavoritos.setVisibility(View.VISIBLE);
            }
            exibirProgreso(false);
            volumeInfoArrayList.clear();
            mFavoritesAdapter.notifyDataSetChanged();
        }
        stopLoader(OPERATION_SEARCH_LOADER);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<VolumeInfo>> loader) {

    }

    private void initLoader() {
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);
        if(loader==null){
            loaderManager.initLoader(OPERATION_SEARCH_LOADER, null, this);
        }else{
            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, null, this);
        }
    }

    private void guardandoUltimaPreferencia(String bookPreference){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(BOOK_PREFERENCE,bookPreference);
        editor.apply();
    }

    private void atualizarWidget() {
        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, InfoBookWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.layout_item_book);
    }

    void stopLoader(int id) {
        getLoaderManager().destroyLoader(id);
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(KEY_RECYCLER_STATE, layoutManager.onSaveInstanceState());
        state.putParcelableArrayList(STATE_ITEMS,items);
        state.putParcelableArrayList(STATE_FAVORITES,volumeInfoArrayList);
    }

}
