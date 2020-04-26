package sr.thrift.smarthome.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidOperation;
import sr.thrift.smarthome.Switch;
import sr.thrift.smarthome.SwitchState;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class SwitchHandler extends DeviceHandler implements Switch.Iface {
    AtomicReference<SwitchState> currentState = new AtomicReference<>(SwitchState.OFF);

    public SwitchHandler(String id, String name) {
        super(id, name);
    }

    @Override
    public SwitchState getState() throws TException {
        System.out.println("SwitchHandler::getState");
        return currentState.get();
    }

    @Override
    public void turnOn() throws InvalidOperation, TException {
        System.out.println("SwitchHandler::turnOn");
        if (currentState.get() == SwitchState.ON)
            throw new InvalidOperation(0, "Device is already turned on");
        currentState.set(SwitchState.ON);
    }

    @Override
    public void turnOff() throws InvalidOperation, TException {
        System.out.println("SwitchHandler::turnOff");
        if (currentState.get() == SwitchState.OFF)
            throw new InvalidOperation(0, "Device is already turned off");
        currentState.set(SwitchState.OFF);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new Switch.Processor<>(this);
    }

}
