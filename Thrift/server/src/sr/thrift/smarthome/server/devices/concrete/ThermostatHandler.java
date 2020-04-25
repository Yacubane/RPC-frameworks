package sr.thrift.smarthome.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidArguments;
import sr.thrift.smarthome.Thermostat;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class ThermostatHandler extends DeviceHandler implements Thermostat.Iface {
    AtomicReference<Double> currentTemperature = new AtomicReference<>(0.0);
    AtomicReference<Double> desiredTemperature = new AtomicReference<>(0.0);

    public ThermostatHandler(String id, String name, double temperature) {
        super(id, name);
        currentTemperature.set(temperature);
        desiredTemperature.set(temperature);
    }

    @Override
    public double getTemperature() throws TException {
        return currentTemperature.get();
    }

    @Override
    public void setTemperature(double temperature) throws InvalidArguments, TException {
        this.desiredTemperature.set(temperature);
        this.currentTemperature.set(temperature);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new Thermostat.Processor<>(this);
    }

}
