package sr.grpc.weather;

import javafx.util.Pair;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WeatherDataGenerator {
    Random random = new Random();

    public WeatherDataGenerator() {

    }

    public BlockingQueue<Pair<WeatherData, WeatherData>> start() {
        BlockingQueue<Pair<WeatherData, WeatherData>> queue = new LinkedBlockingQueue<>();
        addWeatherDataGenerator(queue, "New York",
                new GeneratorValue(10, 30, 1, 2),
                new GeneratorValue(10, 100, 5, 10),
                new GeneratorValue(950, 1050, 5, 10),
                1000, 2000);
        return queue;
    }

    private void addWeatherDataGenerator(BlockingQueue<Pair<WeatherData, WeatherData>> queue,
                                         String cityName,
                                         GeneratorValue temperatureArg,
                                         GeneratorValue humidityArg,
                                         GeneratorValue pressureArg,
                                         int minInterval, int maxInterval) {

        new Thread(() -> {
            try {
                WeatherData prevWeatherData = null;
                while (true) {
                    temperatureArg.update();
                    humidityArg.update();
                    pressureArg.update();

                    WeatherData weatherData = new WeatherData(
                            cityName,
                            temperatureArg.currentValue,
                            humidityArg.currentValue,
                            pressureArg.currentValue);

                    if (prevWeatherData != null) {
                        queue.add(new Pair<>(prevWeatherData, weatherData));
                    }

                    prevWeatherData = weatherData;

                    Thread.sleep(random.nextInt(maxInterval - minInterval) + minInterval);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static class GeneratorValue {
        private final float minValue;
        private final float maxValue;
        private final float minStep;
        private final float maxStep;
        private int multiplier;
        private float currentValue;

        public GeneratorValue(float minValue, float maxValue,
                              float minStep, float maxStep) {

            this.minValue = minValue;
            this.maxValue = maxValue;
            this.minStep = minStep;
            this.maxStep = maxStep;
            currentValue = minValue;
            multiplier = 1;
        }

        public void update() {
            currentValue += (Math.random() * (maxStep - minStep) + minStep) * multiplier;
            if (currentValue > maxValue) {
                currentValue = maxValue;
                multiplier = -multiplier;
            } else if (currentValue < minValue) {
                currentValue = minValue;
                multiplier = -multiplier;
            }
        }
    }
}
