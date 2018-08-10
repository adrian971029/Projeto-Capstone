package com.adrian_971029.infobook.io;

import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET(Constants.VOLUMES + Constants.BEST_SELLER + Constants.MAX_RESULTS)
    Call<Volume> getBestSeller();

}
