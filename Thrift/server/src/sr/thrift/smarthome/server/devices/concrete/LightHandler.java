package sr.thrift.smarthome.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidArguments;
import sr.thrift.smarthome.Light;
import sr.thrift.smarthome.server.devices.general.SwitchHandler;

import java.util.concurrent.atomic.AtomicReference;

public class LightHandler extends SwitchHandler implements Light.Iface {
    AtomicReference<Double> brightness = new AtomicReference<>(0.0);
    private final double maxBrightness;
    private final double minBrightness;

    public LightHandler(String id, String name, double minBrightness, double maxBrightness, double brightness) {
        super(id, name);
        this.brightness.set(brightness);
        this.minBrightness = minBrightness;
        this.maxBrightness = maxBrightness;
    }

    @Override
    public void setBrightness(double brightness) throws InvalidArguments, TException {
        System.out.println("LightHandler::setBrightness");
        if (brightness > maxBrightness || brightness < minBrightness) {
            throw new InvalidArguments(1, "Brightness param must be bettwen "
                    + minBrightness + " and " + maxBrightness);
        }
        this.brightness.set(brightness);
    }

    @Override
    public double getBrightness() throws TException {
        System.out.println("LightHandler::getBrightness");
        return brightness.get();
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new Light.Processor<>(this);
    }

}
