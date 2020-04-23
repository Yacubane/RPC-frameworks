package sr.thrift.homework.server.devices.concrete;

import org.apache.thrift.TBaseProcessor;
import org.apache.thrift.TException;
import sr.rpc.thrift.InvalidArguments;
import sr.rpc.thrift.RGBLight;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RGBLightHandler extends LightHandler implements RGBLight.Iface {
    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
    private final Pattern pattern;
    private String color;

    public RGBLightHandler(String id, String name, double minBrightness, double maxBrightness, double brightness, String color) {
        super(id, name, minBrightness, maxBrightness, brightness);
        this.color = color;
        pattern = Pattern.compile(HEX_PATTERN);
    }

    @Override
    public void setColor(String hex) throws InvalidArguments, TException {
        Matcher matcher = pattern.matcher(hex);
        if(!matcher.matches()) {
            throw new InvalidArguments(1, "This isn't proper HEX code of color");
        }
        this.color = hex;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public TBaseProcessor<?> generateProcessor() {
        return new RGBLight.Processor<>(this);
    }

}
