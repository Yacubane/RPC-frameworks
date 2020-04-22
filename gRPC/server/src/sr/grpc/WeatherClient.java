/*
 * Copyright 2015, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package sr.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import sr.grpc.gen.Weather;
import sr.grpc.gen.WeatherNotifierGrpc;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static sr.grpc.gen.Weather.Relation.MORE_THAN;
import static sr.grpc.gen.Weather.WeatherDataType.TEMPERATURE;

public class WeatherClient {
    private static final Logger logger = Logger.getLogger(WeatherClient.class.getName());

    private final ManagedChannel channel;
    private final WeatherNotifierGrpc.WeatherNotifierStub weatherNotifier;


    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public WeatherClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid needing certificates.
                .usePlaintext(/*true*/)
                .build();

        weatherNotifier = WeatherNotifierGrpc.newStub(channel);

        Weather.WeatherNotifierRequest request = Weather.WeatherNotifierRequest.newBuilder()
                .setCityName("New York")
                .setWeatherDataType(TEMPERATURE)
                .setRelation(MORE_THAN)
                .setValue(20)
                .build();

        StreamObserver<Weather.WeatherEvent> responseObserver = new StreamObserver<Weather.WeatherEvent>() {
            @Override
            public void onNext(Weather.WeatherEvent weatherEvent) {
                System.out.println("Next");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gRPC ERROR");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
            }

        };

        weatherNotifier.subscribeOn(request, responseObserver);

        while (true) ;
    }

    public static void main(String[] args) throws Exception {
        WeatherClient client = new WeatherClient("localhost", 50051);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

}
