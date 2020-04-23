package sr.thrift.homework.server.devices.general;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.rpc.thrift.CustomDevice;
import sr.rpc.thrift.InvalidArguments;
import sr.rpc.thrift.InvalidOperation;
import sr.thrift.homework.server.devices.base.DeviceHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class CustomDeviceHandler extends DeviceHandler implements CustomDevice.Iface {
    private final List<String> operationNames;
    private ConcurrentHashMap<String, String> values = new ConcurrentHashMap<>();

    public CustomDeviceHandler(String id, String name, List<String> operationNames) {
        super(id, name);
        this.operationNames = operationNames;
        init();
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new CustomDevice.Processor<>(this);
    }

    @Override
    public String getValue(String field) throws InvalidArguments, TException {
        if (!values.containsKey(field)) {
            throw new InvalidArguments(1, "This device doesn't support value with key " + field);
        }
        return values.get(field);
    }

    @Override
    public Set<String> getAvailableKeys() throws TException {
        return values.keySet();
    }

    public void setValue(String key, String value) {
        this.values.put(key, value);
    }

    public abstract void init();

    @Override
    public void call(String operation, List<String> arguments) throws InvalidArguments, InvalidOperation, TException {
        if (!operationNames.contains(operation)) {
            throw new InvalidOperation(1, "This device doesn't support this operation");
        }
        handleCall(operation, arguments);
    }

    public abstract void handleCall(String operation, List<String> arguments) throws InvalidArguments, InvalidOperation;

    @Override
    public abstract Map<String, String> getAvailableOperations() throws TException;
}
