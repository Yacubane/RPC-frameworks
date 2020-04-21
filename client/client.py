import grpc

import weather_pb2
import weather_pb2_grpc

import threading
import time

def thread(city, mode, relation, value):
    while True:
        try:
            print("Connecting...")
            channel = grpc.insecure_channel('localhost:50051', options=[
                ('grpc.keepalive_time_ms', 10000),
                ('grpc.keepalive_timeout_ms', 5000),
                ('grpc.keepalive_permit_without_calls', True)
            ])
            stub = weather_pb2_grpc.WeatherNotifierStub(channel)

            request = weather_pb2.WeatherNotifierRequest(
                cityName=city,
                weatherDataType=mode,
                relation=relation,
                value=value)
            call_future = stub.subscribeOn(request)

            for item in call_future:
                print(item.detail)
                for weatherInfo in item.currentWeatherValues:
                    weather_data_type = weather_pb2.WeatherDataType.keys()[weatherInfo.weatherDataType]
                    print(f"\t{weather_data_type} -> {weatherInfo.value}")

        except:
            print("Connection lost")

def inform_when(city, mode, relation, value):
    threading.Thread(target = thread,
    args = (city, mode, relation, value), daemon=True).start()

inform_when("New York", "TEMPERATURE", "MORE_THAN", 13)
inform_when("New York", "TEMPERATURE", "LESS_THAN", 13)

while True:
    pass
