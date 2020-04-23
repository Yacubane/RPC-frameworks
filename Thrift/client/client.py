import sys
import glob
sys.path.append('gen-py')

from smarthome import *
from smarthome.ttypes import InvalidArguments
from smarthome.ttypes import InvalidOperation

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TMultiplexedProtocol

transport = TSocket.TSocket('localhost', 9090)

transport = TTransport.TBufferedTransport(transport)
transport.open()

protocol = TBinaryProtocol.TBinaryProtocol(transport)
devices = Devices.Client(TMultiplexedProtocol.TMultiplexedProtocol(protocol, 'devices-list')).getDeviceInfos()

def get_device_id_by_name(device_name):
    return [device for device in devices if device.name==device_name][0].id

def get_protocol(device_id):
    return TMultiplexedProtocol.TMultiplexedProtocol(protocol, f'device-{device_id}')

light = Light.Client(get_protocol(get_device_id_by_name('light')))
gate = Gate.Client(get_protocol(get_device_id_by_name('gate')))
rgblight = RGBLight.Client(get_protocol(get_device_id_by_name('rgblight')))
thermometer = SingleSensorDevice.Client(get_protocol(get_device_id_by_name('thermometer')))
thermostat = Thermostat.Client(get_protocol(get_device_id_by_name('thermostat')))
camera = PTZSurveillanceCamera.Client(get_protocol(get_device_id_by_name('camera')))
musicPlayer = CustomDevice.Client(get_protocol(get_device_id_by_name('music-player')))

def execute_user_command(command):
    if command == 'list':
        for device in devices:
            print(f' * {device}')
    elif command == 'light on':
        light.turnOn()
    elif command == 'light off':
        light.turnOff()  
    elif command == 'light':
        print(light.getState())
    elif command == 'gate open':
        gate.open()
    elif command == 'gate close':
        gate.close()  
    elif command == 'gate':
        print(gate.getState())
    elif command.startswith('gate set percent'):
        gate.setOpenPercent(float(command.split()[3]))
    elif command == 'gate percent':
        print(gate.getOpenPercent())
    elif command == 'rgb on':
        rgblight.turnOn()
    elif command == 'rgb off':
        rgblight.turnOff()  
    elif command == 'rgb':
        print(rgblight.getState())
    elif command.startswith('rgb set color'):
        rgblight.setColor(command.split()[3])
    elif command == 'rgb color':
        print(rgblight.getColor())
    elif command == 'temp':
        print(thermometer.getValue())
    elif command == 'thermostat':
        print(thermostat.getTemperature())
    elif command.startswith('thermostat set temp'):
        thermostat.setTemperature(float(command.split()[3]))
    elif command == "camera pan":
        print(camera.getPan())
    elif command == "camera tilt":
        print(camera.getTilt())
    elif command == "camera zoom":
        print(camera.getZoom()) 
    elif command == "camera recording":
        print(camera.isRecording()) 
    elif command.startswith("camera set pan"):
        print(camera.setPan(float(command.split()[3])))
    elif command.startswith("camera set tilt"):
        print(camera.setTilt(float(command.split()[3])))
    elif command.startswith("camera set zoom"):
        print(camera.setZoom(float(command.split()[3]))) 
    elif command == "camera start recording":
        print(camera.startRecording()) 
    elif command == "camera stop recording":
        print(camera.stopRecording()) 
    elif command.startswith('music keys'):
        print(musicPlayer.getAvailableKeys())
    elif command.startswith('music operations'):
        print(musicPlayer.getAvailableOperations())
    elif command.startswith('music get'):
        print(musicPlayer.getValue(command.split()[2]))
    elif command.startswith('music call'):
        musicPlayer.call(command.split()[2], command.split()[3:])
    else:
        return False
    return True

while True:
    print('->', end='')
    command = input() 
    try:
        if execute_user_command(command):
            print("Command succeeded")
        else:
            print("Unknown command")
    except InvalidArguments as e:
        print(f'Invalid argument: {e.reason}')
    except InvalidOperation as e:
        print(f'Invalid operation: {e.why}')
    except:
        print("Unknown error")

