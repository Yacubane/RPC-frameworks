package sr.grpc.weather;

import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import javafx.util.Pair;
import sr.grpc.gen.Weather;
import sr.grpc.gen.WeatherNotifierGrpc;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import static sr.grpc.gen.Weather.WeatherDataType.*;
import static sr.grpc.gen.Weather.WeatherValue.newBuilder;

public class WeatherNotifierImpl extends WeatherNotifierGrpc.WeatherNotifierImplBase implements Runnable {

    private final ExecutorService executorService;
    private final BlockingQueue<Pair<WeatherData, WeatherData>> weatherDataQueue;

    private static class Client {
        private final Weather.WeatherNotifierRequest request;
        private final StreamObserver<Weather.WeatherEvent> responseObserver;
        public boolean justConnected = true;

        public Client(Weather.WeatherNotifierRequest request,
                      StreamObserver<Weather.WeatherEvent> responseObserver) {

            this.request = request;
            this.responseObserver = responseObserver;
        }

        @Override
        public boolean equals(Object o) {
            return responseObserver.equals(o);
        }

        @Override
        public int hashCode() {
            return responseObserver.hashCode();
        }
    }

    private static Set<Client> observers = ConcurrentHashMap.newKeySet();

    public WeatherNotifierImpl(ExecutorService executorService,
                               BlockingQueue<Pair<WeatherData, WeatherData>> weatherDataQueue) {

        this.executorService = executorService;
        this.weatherDataQueue = weatherDataQueue;
    }

    @Override
    public void subscribeOn(Weather.WeatherNotifierRequest request,
                            StreamObserver<Weather.WeatherEvent> responseObserver) {
        Client client = new Client(request, responseObserver);
        observers.add(client);
        System.out.println("Adding new client");
        Context.current().addListener(context -> {
            System.out.println("Deleting client");
            observers.remove(client);
        }, executorService);
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Pair<WeatherData, WeatherData> data = weatherDataQueue.take();
                WeatherData prevWeather = data.getKey();
                WeatherData currWeather = data.getValue();

                System.out.println("Weather update: " + currWeather);

                for (Client client : observers) {
                    if (conditionFulfilled(client, currWeather)) {
                        if (!conditionFulfilled(client, prevWeather) || client.justConnected) {
                            client.responseObserver.onNext(prepareMessage(client, currWeather));
                            client.justConnected = false;
                        }
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Weather.WeatherEvent prepareMessage(Client client, WeatherData currWeather) {
        String message = "City " + currWeather.getCityName() + " has reached condition (" +
                client.request.getWeatherDataType().toString() + " " +
                client.request.getRelation().toString() + " " +
                client.request.getValue() + ")";
        return Weather.WeatherEvent.newBuilder()
                .setWeatherDataType(client.request.getWeatherDataType())
                .setDetail(message)
                .addCurrentWeatherValues(
                        newBuilder().setWeatherDataType(TEMPERATURE)
                                .setValue(currWeather.getTemperature()).build())
                .addCurrentWeatherValues(
                        newBuilder().setWeatherDataType(HUMIDITY)
                                .setValue(currWeather.getHumidity()).build())
                .addCurrentWeatherValues(
                        newBuilder().setWeatherDataType(ATMOSPHERIC_PRESSURE)
                                .setValue(currWeather.getPressure()).build())
                .build();
    }

    private boolean conditionFulfilled(Client client, WeatherData weather) {
        Weather.WeatherNotifierRequest request = client.request;
        if (!request.getCityName().equals(weather.getCityName())) {
            return false;
        }
        switch (request.getRelation()) {
            case EQUAL:
                return (weather.getWeatherParameter(request.getWeatherDataType()) == request.getValue());
            case MORE_THAN_EQUAL:
                return (weather.getWeatherParameter(request.getWeatherDataType()) >= request.getValue());
            case MORE_THAN:
                return (weather.getWeatherParameter(request.getWeatherDataType()) > request.getValue());
            case LESS_THAN_EQUAL:
                return (weather.getWeatherParameter(request.getWeatherDataType()) <= request.getValue());
            case LESS_THAN:
                return (weather.getWeatherParameter(request.getWeatherDataType()) < request.getValue());
            case UNRECOGNIZED:
                return false;
        }

        return false;
    }

}
