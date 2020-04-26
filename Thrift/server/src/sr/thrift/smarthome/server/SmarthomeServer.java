package sr.thrift.smarthome.server;

import org.apache.thrift.TException;
import sr.thrift.smarthome.InvalidArguments;
import sr.thrift.smarthome.InvalidOperation;
import sr.thrift.smarthome.server.devices.concrete.*;
import sr.thrift.smarthome.server.devices.concrete.PTZSurveillanceCameraHandler.PTZSurveillanceCameraRestrictions;
import sr.thrift.smarthome.server.devices.general.CustomDeviceHandler;
import sr.thrift.smarthome.server.devices.general.SingleSensorDeviceHandler;

import java.util.List;
import java.util.Map;

public class SmarthomeServer {

    public static void main(String[] args) throws TException {
        SmarthomeServerBuilder builder = new SmarthomeServerBuilder();
        builder.addDevice(new LightHandler(
                "1", "light", 0, 1, 0.5));

        builder.addDevice(new GateHandler(
                "2", "gate", 0.5));

        builder.addDevice(new RGBLightHandler(
                "3", "rgblight", 0, 1, 0.5, "#FF0000"));

        builder.addDevice(new SingleSensorDeviceHandler(
                "4", "thermometer", 22));

        builder.addDevice(new SingleSensorDeviceHandler(
                "5", "thermometer-outside", 9));

        builder.addDevice(new ThermostatHandler(
                "6", "thermostat", 8));

        builder.addDevice(new PTZSurveillanceCameraHandler(
                "7", "camera",
                new PTZSurveillanceCameraRestrictions(0, 180, 0, 180, 1, 3)));

        builder.addDevice(new CustomDeviceHandler("8", "music-player", List.of("play")) {
            @Override
            public void init() {
                setValue("currentMusic", "Taco Hemingway - Następna Stacja");
            }

            @Override
            public void handleCall(String operation, List<String> arguments) throws InvalidArguments, InvalidOperation {
                switch (operation) {
                    case "play":
                        if (arguments.size() != 1) {
                            throw new InvalidArguments(1, "Play operation needs exactly one argument");
                        }
                        setValue("currentMusic", arguments.get(0));
                        break;
                }
            }

            @Override
            public Map<String, String> getAvailableOperations() throws TException {
                return Map.of("play(musicName:string)", "plays music with selected name");
            }
        });
        System.out.println("Server started!");
        builder.serve();
    }

}