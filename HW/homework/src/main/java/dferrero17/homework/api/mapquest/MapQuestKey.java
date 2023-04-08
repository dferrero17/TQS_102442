package dferrero17.homework.api.mapquest;

import dferrero17.homework.api.openweather.OpenWeatherKey;

import java.util.Objects;

public class MapQuestKey {
    private Double lat;
    private Double lon;

    public MapQuestKey(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapQuestKey that = (MapQuestKey) o;

        if (!Objects.equals(lat, that.lat)) return false;
        return Objects.equals(lon, that.lon);
    }

    @Override
    public int hashCode() {
        int result = lat != null ? lat.hashCode() : 0;
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        return result;
    }
}
