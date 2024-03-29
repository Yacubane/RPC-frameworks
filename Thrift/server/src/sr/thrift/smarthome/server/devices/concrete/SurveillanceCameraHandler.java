package sr.thrift.smarthome.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidOperation;
import sr.thrift.smarthome.SurveillanceCamera;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class SurveillanceCameraHandler extends DeviceHandler implements SurveillanceCamera.Iface {
    AtomicBoolean recording = new AtomicBoolean();

    public SurveillanceCameraHandler(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean isRecording() throws TException {
        System.out.println("SurveillanceCameraHandler::isRecording");
        return recording.get();
    }

    @Override
    public void startRecording() throws InvalidOperation, TException {
        System.out.println("SurveillanceCameraHandler::startRecording");
        if (recording.get()) {
            throw new InvalidOperation(0, "Device is already recording");
        }
        recording.set(true);
    }

    @Override
    public void stopRecording() throws InvalidOperation, TException {
        System.out.println("SurveillanceCameraHandler::stopRecording");
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
