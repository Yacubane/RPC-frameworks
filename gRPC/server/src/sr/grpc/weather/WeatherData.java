package sr.grpc.weather;

import sr.grpc.gen.Weather;

public class WeatherData {
    private final String cityName;
    private final float temperature;
    private final float humidity;
    private final float pressure;

    public WeatherData(String cityName, float temperature, float humidity, float pressure) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWeatherParameter(Weather.WeatherDataType weatherDataType) {
        switch (weatherDataType) {
            case TEMPERATURE:
                return temperature;
            case HUMIDITY:
                return humidity;
            case ATMOSPHERIC_PRESSURE:
                return pressure;
            case UNRECOGNIZED:
                break;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "cityName='" + cityName + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }
}
