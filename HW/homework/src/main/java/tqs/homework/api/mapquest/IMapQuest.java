package tqs.homework.api.mapquest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;

public interface IMapQuest {
    @GET("geocoding/v1/reverse")
    Call<MapQuestData> getLocation(
            @Query("key") String apiKey,
            @Query("location") String location
    );
}
