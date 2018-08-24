package com.adrian_971029.infobook.io;

import com.adrian_971029.infobook.model.Volume;
import com.adrian_971029.infobook.model_map.MyPlaces;
import com.adrian_971029.infobook.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.ADVENTURE  + Constants.MAX_RESULTS)
    Call<Volume> getAdventureBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.ACTION  + Constants.MAX_RESULTS)
    Call<Volume> getActionBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.DRAMA  + Constants.MAX_RESULTS)
    Call<Volume> getDramaBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.FANTASY  + Constants.MAX_RESULTS)
    Call<Volume> getFantasyBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.HISTORY  + Constants.MAX_RESULTS)
    Call<Volume> getHistoryBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.HORROR  + Constants.MAX_RESULTS)
    Call<Volume> getHorrorBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.MYSTERY  + Constants.MAX_RESULTS)
    Call<Volume> getMysteryBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.RELIGION  + Constants.MAX_RESULTS)
    Call<Volume> getReligionBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.ROMANCE  + Constants.MAX_RESULTS)
    Call<Volume> getRomanceBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.SCIENCE  + Constants.MAX_RESULTS)
    Call<Volume> getScienceBooks();

    @GET(Constants.VOLUMES + Constants.SUBJECT + Constants.SCIENCE_FICTION  + Constants.MAX_RESULTS)
    Call<Volume> getScienceFictionBooks();

    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);

}
