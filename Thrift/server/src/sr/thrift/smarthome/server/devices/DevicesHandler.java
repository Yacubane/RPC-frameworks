package sr.thrift.smarthome.server.devices;

import org.apache.thrift.TException;
import sr.thrift.smarthome.DeviceInfo;
import sr.thrift.smarthome.Devices;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DevicesHandler implements Devices.Iface {
    private List<DeviceInfo> devices;

    public DevicesHandler(List<DeviceHandler> devices) {
        this.devices = devices.stream()
                .map(this::createDeviceInfo)
                .collect(toList());
    }

    private DeviceInfo createDeviceInfo(DeviceHandler deviceHandler) {
        try {
            return new DeviceInfo(deviceHandler.getId(), deviceHandler.getName());
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DeviceInfo> getDeviceInfos() throws TException {
        System.out.println("getDeviceInfos");
        return devices;
    }
}
