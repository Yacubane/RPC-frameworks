package sr.thrift.smarthome.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidArguments;
import sr.thrift.smarthome.PTZSurveillanceCamera;

import java.util.concurrent.atomic.AtomicReference;

public class PTZSurveillanceCameraHandler extends SurveillanceCameraHandler implements PTZSurveillanceCamera.Iface {
    private final PTZSurveillanceCameraRestrictions restrictions;
    AtomicReference<Double> pan = new AtomicReference<>(0.0);
    AtomicReference<Double> tilt = new AtomicReference<>(0.0);
    AtomicReference<Double> zoom = new AtomicReference<>(0.0);

    public PTZSurveillanceCameraHandler(String id, String name, PTZSurveillanceCameraRestrictions restrictions) {
        super(id, name);
        this.restrictions = restrictions;
        pan.set(restrictions.minPan);
        tilt.set(restrictions.minTilt);
        zoom.set(restrictions.minZoom);
    }


    @Override
    public double getPan() throws TException {
        return pan.get();
    }

    @Override
    public double getTilt() throws TException {
        return tilt.get();
    }

    @Override
    public double getZoom() throws TException {
        return zoom.get();
    }

    @Override
    public void setPan(double pan) throws InvalidArguments, TException {
        if (pan < restrictions.minPan || pan > restrictions.maxPan) {
            throw new InvalidArguments(1, "Pan of camera must be between " +
                    restrictions.minPan + " and " + restrictions.maxPan);
        }
        this.pan.set(pan);
    }

    @Override
    public void setTilt(double tilt) throws InvalidArguments, TException {
        if (tilt < restrictions.minTilt || tilt > restrictions.maxTilt) {
            throw new InvalidArguments(1, "Tilt of camera must be between " +
                    restrictions.minTilt + " and " + restrictions.maxTilt);
        }
        this.tilt.set(tilt);
    }

    @Override
    public void setZoom(double zoom) throws InvalidArguments, TException {
        if (zoom < restrictions.minZoom || zoom > restrictions.maxZoom) {
            throw new InvalidArguments(1, "Zoom of camera must be between " +
                    restrictions.minZoom + " and " + restrictions.maxZoom);
        }
        this.zoom.set(zoom);
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new PTZSurveillanceCamera.Processor<>(this);
    }

    public static class PTZSurveillanceCameraRestrictions {
        private final double minPan;
        private final double maxPan;
        private final double minTilt;
        private final double maxTilt;
        private final double minZoom;
        private final double maxZoom;

        public PTZSurveillanceCameraRestrictions(double minPan, double maxPan,
                                                 double minTilt, double maxTilt,
                                                 double minZoom, double maxZoom) {
            this.minPan = minPan;
            this.maxPan = maxPan;
            this.minTilt = minTilt;
            this.maxTilt = maxTilt;
            this.minZoom = minZoom;
            this.maxZoom = maxZoom;
        }

    }

}
