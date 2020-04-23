package sr.thrift.homework.client;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import sr.rpc.thrift.*;

import java.util.Arrays;
import java.util.HashSet;


public class SmarthomeClient
{
	public static void main(String [] args) 
	{

		String opt = "multiplex"; //simple | multiplex | non-block | asyn | multi-thread
		String host = "localhost";

		TProtocol protocol = null;
		TTransport transport = null;


		System.out.println("Running client in the " + opt + " mode");
		try {
			transport = new TSocket(host, 9090);
			protocol = new TBinaryProtocol(transport, true, true);
			transport.open();

			Devices.Client devices = new Devices.Client(new TMultiplexedProtocol(protocol, "devices-list"));
			Light.Client light = new Light.Client(new TMultiplexedProtocol(protocol, "device-" + devices.getDeviceInfos().get(0).getId()));


			System.out.println(devices.getDeviceInfos());
			System.out.println(light.getBrightness());
			light.setBrightness(1.0);

			while (true){

			}



		} catch (TException ex) {
			ex.printStackTrace();
		}
	}
}
