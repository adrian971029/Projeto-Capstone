package com.adrian_971029.infobook.utils;

import com.adrian_971029.infobook.io.ApiAdapter;
import com.adrian_971029.infobook.io.ApiService;
import com.adrian_971029.infobook.model.Volume;

import retrofit2.Call;

public class CategoryHelper {

    public static Call<Volume> defineCategoria(String categoria) {
        ApiService service = ApiAdapter.getApiService(Constants.URL_BASE);

        switch (categoria) {
            case Constants.ACTION:
                return service.getActionBooks();
            case Constants.ADVENTURE:
                return service.getAdventureBooks();
            case Constants.DRAMA:
                return service.getDramaBooks();
            case Constants.FANTASY:
                return service.getFantasyBooks();
            case Constants.HISTORY:
                return service.getHistoryBooks();
            case Constants.HORROR:
                return service.getHorrorBooks();
            case Constants.MYSTERY:
                return service.getMysteryBooks();
            case Constants.RELIGION:
                return service.getReligionBooks();
            case Constants.ROMANCE:
                return service.getRomanceBooks();
            case Constants.SCIENCE:
                return service.getScienceBooks();
            case Constants.SCIENCE_FICTION:
                return service.getScienceFictionBooks();
            default:
                return service.getAdventureBooks();
        }
    }

}
