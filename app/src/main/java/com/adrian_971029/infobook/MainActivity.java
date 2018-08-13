package com.adrian_971029.infobook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.adrian_971029.infobook.adapter.MainAdapter;
import com.adrian_971029.infobook.io.ApiAdapter;
import com.adrian_971029.infobook.io.ApiService;
import com.adrian_971029.infobook.model.Item;
import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.model.VolumeInfo;
import com.adrian_971029.infobook.utils.CategoryHelper;
import com.adrian_971029.infobook.utils.Constants;
import com.adrian_971029.infobook.utils.ReadVolumeJson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Volume volume;
    private ArrayList<Item> items;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("InfoBook");
        crearLayout();

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

      //  if(temConexao(this)){
        ReadVolumeJson readVolumeJson = new ReadVolumeJson(volume,items,mAdapter);
        readVolumeJson.chamaJSon(Constants.ADVENTURE);
       // }
       /* else{
            exibirProgreso(false);
            exibirMensagemSemConexao(true);
        }*/

    }

}
