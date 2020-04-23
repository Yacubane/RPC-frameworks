package sr.thrift.homework.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.rpc.thrift.*;
import sr.thrift.homework.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicReference;

public class OpenCloseDeviceHandler extends DeviceHandler implements OpenCloseDevice.Iface {
    AtomicReference<OpenCloseState> state = new AtomicReference<>(OpenCloseState.IN_BETWEEN);

    public OpenCloseDeviceHandler(String id, String name, OpenCloseState state) {
        super(id, name);
        this.state.set(state);
    }

    @Override
    public OpenCloseState getState() throws TException {
        return state.get();
    }

    @Override
    public void open() throws InvalidOperation, TException {
        if(state.get() == OpenCloseState.OPEN) {
            throw new InvalidOperation(1, "Device is already open");
        }
        state.set(OpenCloseState.OPEN);
    }

    @Override
    public void close() throws InvalidOperation, TException {
        if(state.get() == OpenCloseState.CLOSE) {
            throw new InvalidOperation(1, "Device is already closed");
        }
        state.set(OpenCloseState.CLOSE);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new OpenCloseDevice.Processor<>(this);
    }

}
