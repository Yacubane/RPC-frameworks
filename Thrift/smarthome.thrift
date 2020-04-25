namespace java sr.thrift.smarthome
namespace py smarthome

enum SwitchState {
  ON = 1,
  OFF = 2
}

enum OpenCloseState {
  IN_BETWEEN = 1,
  OPEN = 2,
  CLOSE = 3
}

exception InvalidOperation {
  1: i32 whatOp,
  2: string why
}

exception InvalidArguments {
  1: i32 argNo,
  2: string reason
}

service Device {
    string getId(),
    string getName()
}

service SingleSensorDevice extends Device {
    double getValue(),
}

service MultipleSensorDevice extends Device {
     double getValue(1:string field)  throws (1: InvalidArguments ex),
     set<string> getAvailableKeys(),
}

service CustomDevice extends Device {
      string getValue(1:string field)  throws (1: InvalidArguments ex),
      set<string> getAvailableKeys(),
      void call(1:string operation, 2: list<string> arguments) throws (1: InvalidArguments ex, 2: InvalidOperation ex2),
      map<string,string> getAvailableOperations(),
}

service Switch extends Device {
    SwitchState getState(),
    void turnOn() throws (1: InvalidOperation ex),
    void turnOff() throws (1: InvalidOperation ex),
}

service OpenCloseDevice extends Device {
    OpenCloseState getState(),
    void open() throws (1: InvalidOperation ex),
    void close() throws (1: InvalidOperation ex),
}

service Light extends Switch {
    void setBrightness(1:double brightness) throws (1: InvalidArguments ex),
    double getBrightness(),
}

service RGBLight extends Light {
    void setColor(1:string hex) throws (1: InvalidArguments ex),
    string getColor()
}

service Gate extends OpenCloseDevice {
    double getOpenPercent(),
    void setOpenPercent(1:double openPercent) throws (1: InvalidArguments ex)
}

service SurveillanceCamera extends Device {
    bool isRecording(),
    void startRecording() throws (1: InvalidOperation ex),
    void stopRecording() throws (1: InvalidOperation ex)
}

service PTZSurveillanceCamera extends SurveillanceCamera {
    double getPan(),
    double getTilt(),
    double getZoom(),
    void setPan(1:double pan) throws (1: InvalidArguments ex),
    void setTilt(1:double tilt) throws (1: InvalidArguments ex),
    void setZoom(1:double zoom) throws (1: InvalidArguments ex),
}

service Thermostat extends Device {
    double getTemperature(),
    void setTemperature(1:double temperature) throws (1: InvalidArguments ex),
}

struct DeviceInfo {
    1: string id,
    2: string name,
}

service Devices {
    list<DeviceInfo> getDeviceInfos(),
}