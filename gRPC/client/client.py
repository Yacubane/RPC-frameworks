import grpc

from gen import weather_pb2
from gen import weather_pb2_grpc
import backoff

import threading
import time
import sys

def backoff_hdlr(details):
    print ("[{args[1]}]Backing off {wait:.2f} seconds afters {tries} tries".format(**details))

@backoff.on_exception(backoff.expo,
                      grpc.FutureTimeoutError,
                      on_backoff=backoff_hdlr)
def subscribe(request, no):
    print(f'[{no}]Connecting...')
    
    channel = grpc.insecure_channel('localhost:50051', options=[
        ('grpc.keepalive_time_ms', 10000),
        ('grpc.keepalive_timeout_ms', 5000),
        ('grpc.keepalive_permit_without_calls', True)
    ])

    grpc.channel_ready_future(channel).result(timeout=5)
    print(f'[{no}]Connected!')
        
    stub = weather_pb2_grpc.WeatherNotifierStub(channel)
    call_future = stub.subscribeOn(request)

    for item in call_future:
        print(item.detail)
        for weatherInfo in item.currentWeatherValues:
            weather_data_type = weather_pb2.WeatherDataType.keys()[weatherInfo.weatherDataType]
            print(f"\t{weather_data_type} -> {weatherInfo.value}")

def subscribe_thread(request, no):
    while True:
        try:
            subscribe(request, no)
        except Exception:
            print(f"Connection error during streaming")

thread_no = 0
def inform_when(city, mode, relation, value):
    request = weather_pb2.WeatherNotifierRequest(
            cityName=city,
            weatherDataType=mode,
            relation=relation,
            value=value)
    global thread_no
    thread_no = thread_no + 1
    threading.Thread(target = subscribe_thread, args = (request,thread_no), daemon=True).start()


if len(sys.argv) == 5:
    inform_when(sys.argv[1], sys.argv[2], sys.argv[3], float(sys.argv[4]))
    while True:
        pass
elif len(sys.argv) == 1:
    print("Usage: client.py  <city_name> <weather_type> <relation> <value>")
    print("Ex:    client.py \"New York\" HUMIDITY MORE_THAN 50")
    print("Test without arguments:")
    print("    Inform when temperature in New York is more than 13 or less than 13")
    inform_when("New York", "TEMPERATURE", "MORE_THAN", 13)
    inform_when("New York", "TEMPERATURE", "LESS_THAN", 13)
    while True:
        pass
else:
    print("Wrong arguments number")
