import sys
import glob
sys.path.append('gen-py')

from smarthome import *
from smarthome.ttypes import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TMultiplexedProtocol
from thrift.transport.TTransport import TTransportException

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
thermometer2 = SingleSensorDevice.Client(get_protocol(get_device_id_by_name('thermometer-outside')))
thermostat = Thermostat.Client(get_protocol(get_device_id_by_name('thermostat')))
camera = PTZSurveillanceCamera.Client(get_protocol(get_device_id_by_name('camera')))
musicPlayer = CustomDevice.Client(get_protocol(get_device_id_by_name('music-player')))

def print_help():
    print("List of available commands:\n"
          "\thelp, list, light on, light off, light,\n"
          "\tgate open, gate close, gate, gate set percent <arg>, gate percent,\n"
          "\trgb on, rgb off, rgb, rgb set color <arg>, rgb color,\n"
          "\ttemp, tempout, thermostat, thermostat set temp <arg>,\n"
          "\tcamera pan, camera tilt, camera zoom, camera set pan <arg>, camera set tilt <arg>, camera set zoom <arg>,\n"
          "\tcamera start recording, camera stop recording, camera recording,\n"
          "\tmusic keys, music operations, music get <key>, music call <op> <args>")

def execute_user_command(command):
    if command == 'help':
        print_help()
    elif command == 'list':
        for device in devices:
            print(f' * {device}')
    elif command == 'light on':
        light.turnOn()
    elif command == 'light off':
        light.turnOff()  
    elif command == 'light':
        print(SwitchState._VALUES_TO_NAMES[light.getState()])
    elif command == 'gate open':
        gate.open()
    elif command == 'gate close':
        gate.close()  
    elif command == 'gate':
        print(OpenCloseState._VALUES_TO_NAMES[gate.getState()])
    elif command.startswith('gate set percent'):
        gate.setOpenPercent(float(command.split()[3]))
    elif command == 'gate percent':
        print(gate.getOpenPercent())
    elif command == 'rgb on':
        rgblight.turnOn()
    elif command == 'rgb off':
        rgblight.turnOff()  
    elif command == 'rgb':
        print(SwitchState._VALUES_TO_NAMES[rgblight.getState()])
    elif command.startswith('rgb set color'):
        rgblight.setColor(command.split()[3])
    elif command == 'rgb color':
        print(rgblight.getColor())
    elif command == 'temp':
        print(thermometer.getValue())
    elif command == 'tempout':
        print(thermometer2.getValue())
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
        camera.setPan(float(command.split()[3]))
    elif command.startswith("camera set tilt"):
        camera.setTilt(float(command.split()[3]))
    elif command.startswith("camera set zoom"):
        camera.setZoom(float(command.split()[3])) 
    elif command == "camera start recording":
        camera.startRecording()
    elif command == "camera stop recording":
        camera.stopRecording()
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
    except TTransportException:
        print('Network error')
        break
    except:
        print('Unknown error')

