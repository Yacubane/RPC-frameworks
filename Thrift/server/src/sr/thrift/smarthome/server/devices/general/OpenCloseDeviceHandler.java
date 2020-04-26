package sr.thrift.smarthome.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidOperation;
import sr.thrift.smarthome.OpenCloseDevice;
import sr.thrift.smarthome.OpenCloseState;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class OpenCloseDeviceHandler extends DeviceHandler implements OpenCloseDevice.Iface {
    AtomicReference<OpenCloseState> state = new AtomicReference<>(OpenCloseState.IN_BETWEEN);

    public OpenCloseDeviceHandler(String id, String name, OpenCloseState state) {
        super(id, name);
        this.state.set(state);
    }

    @Override
    public OpenCloseState getState() throws TException {
        System.out.println("OpenCloseState::getState");
        return state.get();
    }

    @Override
    public void open() throws InvalidOperation, TException {
        System.out.println("OpenCloseState::open");
        if (state.get() == OpenCloseState.OPEN) {
            throw new InvalidOperation(1, "Device is already open");
        }
        state.set(OpenCloseState.OPEN);
    }

    @Override
    public void close() throws InvalidOperation, TException {
        System.out.println("OpenCloseState::close");
        if (state.get() == OpenCloseState.CLOSE) {
            throw new InvalidOperation(1, "Device is already closed");
        }
        state.set(OpenCloseState.CLOSE);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new OpenCloseDevice.Processor<>(this);
    }

}
