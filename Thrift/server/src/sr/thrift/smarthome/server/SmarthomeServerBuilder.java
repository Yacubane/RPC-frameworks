package sr.thrift.smarthome.server;

import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import sr.thrift.smarthome.Devices;
import sr.thrift.smarthome.server.devices.DevicesHandler;
import sr.thrift.smarthome.server.devices.base.DeviceHandler;

import java.util.ArrayList;
import java.util.List;

public class SmarthomeServerBuilder {
    List<DeviceHandler> devicesHandlerList = new ArrayList<>();
    TMultiplexedProcessor multiplex = new TMultiplexedProcessor();

    public SmarthomeServerBuilder() {

    }

    public void addDevice(DeviceHandler deviceHandler) throws TException {
        devicesHandlerList.add(deviceHandler);
        multiplex.registerProcessor(
                "device-" + deviceHandler.getId(),
                deviceHandler.generateProcessor());
    }

    public void serve() throws TTransportException {
        Devices.Processor<DevicesHandler> devicesProcessor =
                new Devices.Processor<>(new DevicesHandler(devicesHandlerList));
        multiplex.registerProcessor("devices-list", devicesProcessor);

        TServerTransport serverTransport = new TServerSocket(9090);
        TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
        TServer server = new TThreadPoolServer(
                new TThreadPoolServer.Args(serverTransport)
                        .protocolFactory(protocolFactory)
                        .processor(multiplex));
        server.serve();

    }
}
