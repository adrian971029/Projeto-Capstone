package com.adrian_971029.infobook;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends BaseActivity {

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

    private Volume volume;
    private ArrayList<Item> items;
    private MainAdapter mAdapter;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        resources = this.getResources();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("InfoBook");
        mToolbar.setTitleTextColor(resources.getColor(R.color.textAndIcons));
        exibirProgreso(true);
        crearLayout();

    }

    @OnClick(R.id.img_atualizar)
    void onclickImageAtualizar(){
        if (temConexao(this)) {
            exibirProgreso(true);
            exibirMensagemSemConexao(false);
            crearLayout();
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

    private void crearLayout(){
        if(items == null){
            items = new ArrayList<Item>();
        }
        setupRecycler();

        if(temConexao(this)){
            ReadVolumeJson readVolumeJson = new ReadVolumeJson(volume,items,mAdapter,mProgressBar,mTextMensagem);
            readVolumeJson.chamaJSon(Constants.ADVENTURE);
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

}
