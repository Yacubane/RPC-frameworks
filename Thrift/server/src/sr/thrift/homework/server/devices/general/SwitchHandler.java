package sr.thrift.homework.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.rpc.thrift.InvalidOperation;
import sr.rpc.thrift.Switch;
import sr.rpc.thrift.SwitchState;
import sr.thrift.homework.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class SwitchHandler extends DeviceHandler implements Switch.Iface {
    AtomicReference<SwitchState> currentState = new AtomicReference<>(SwitchState.OFF);

    public SwitchHandler(String id, String name) {
        super(id, name);
    }

    @Override
    public SwitchState getState() throws TException {
        return currentState.get();
    }

    @Override
    public void turnOn() throws InvalidOperation, TException {
        if(currentState.get() == SwitchState.ON)
            throw new InvalidOperation(0, "Device is already turned on");
        currentState.set(SwitchState.ON);
    }

    @Override
    public void turnOff() throws InvalidOperation, TException {
        if(currentState.get() == SwitchState.OFF)
            throw new InvalidOperation(0, "Device is already turned off");
        currentState.set(SwitchState.OFF);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new Switch.Processor<>(this);
    }

}
