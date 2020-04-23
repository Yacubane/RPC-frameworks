package sr.thrift.homework.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.rpc.thrift.InvalidOperation;
import sr.rpc.thrift.SurveillanceCamera;
import sr.thrift.homework.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class SurveillanceCameraHandler extends DeviceHandler implements SurveillanceCamera.Iface {
    AtomicBoolean recording = new AtomicBoolean();

    public SurveillanceCameraHandler(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean isRecording() throws TException {
        return recording.get();
    }

    @Override
    public void startRecording() throws InvalidOperation, TException {
        if (recording.get()) {
            throw new InvalidOperation(0, "Device is already recording");
        }
        recording.set(true);
    }

    @Override
    public void stopRecording() throws InvalidOperation, TException {
        if (!recording.get()) {
            throw new InvalidOperation(0, "Device isn't recording");
        }
        recording.set(false);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new SurveillanceCamera.Processor<>(this);
    }

}
