package dferrero17.homework.api.openweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherApi {
    @GET("history")
    Call<OpenWeatherData> getHistory(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("start") long start,
            @Query("end") long end,
            @Query("appid") String apiKey
    );
}
