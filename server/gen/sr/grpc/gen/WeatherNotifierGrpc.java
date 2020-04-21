package sr.grpc.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.0)",
    comments = "Source: weather.proto")
public final class WeatherNotifierGrpc {

  private WeatherNotifierGrpc() {}

  public static final String SERVICE_NAME = "weather.WeatherNotifier";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<sr.grpc.gen.Weather.WeatherNotifierRequest,
      sr.grpc.gen.Weather.WeatherEvent> getSubscribeOnMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribeOn",
      requestType = sr.grpc.gen.Weather.WeatherNotifierRequest.class,
      responseType = sr.grpc.gen.Weather.WeatherEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<sr.grpc.gen.Weather.WeatherNotifierRequest,
      sr.grpc.gen.Weather.WeatherEvent> getSubscribeOnMethod() {
    io.grpc.MethodDescriptor<sr.grpc.gen.Weather.WeatherNotifierRequest, sr.grpc.gen.Weather.WeatherEvent> getSubscribeOnMethod;
    if ((getSubscribeOnMethod = WeatherNotifierGrpc.getSubscribeOnMethod) == null) {
      synchronized (WeatherNotifierGrpc.class) {
        if ((getSubscribeOnMethod = WeatherNotifierGrpc.getSubscribeOnMethod) == null) {
          WeatherNotifierGrpc.getSubscribeOnMethod = getSubscribeOnMethod =
              io.grpc.MethodDescriptor.<sr.grpc.gen.Weather.WeatherNotifierRequest, sr.grpc.gen.Weather.WeatherEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "subscribeOn"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.Weather.WeatherNotifierRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  sr.grpc.gen.Weather.WeatherEvent.getDefaultInstance()))
              .setSchemaDescriptor(new WeatherNotifierMethodDescriptorSupplier("subscribeOn"))
              .build();
        }
      }
    }
    return getSubscribeOnMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WeatherNotifierStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherNotifierStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherNotifierStub>() {
        @java.lang.Override
        public WeatherNotifierStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherNotifierStub(channel, callOptions);
        }
      };
    return WeatherNotifierStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WeatherNotifierBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherNotifierBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherNotifierBlockingStub>() {
        @java.lang.Override
        public WeatherNotifierBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherNotifierBlockingStub(channel, callOptions);
        }
      };
    return WeatherNotifierBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WeatherNotifierFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherNotifierFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherNotifierFutureStub>() {
        @java.lang.Override
        public WeatherNotifierFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherNotifierFutureStub(channel, callOptions);
        }
      };
    return WeatherNotifierFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class WeatherNotifierImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribeOn(sr.grpc.gen.Weather.WeatherNotifierRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.Weather.WeatherEvent> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeOnMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeOnMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                sr.grpc.gen.Weather.WeatherNotifierRequest,
                sr.grpc.gen.Weather.WeatherEvent>(
                  this, METHODID_SUBSCRIBE_ON)))
          .build();
    }
  }

  /**
   */
  public static final class WeatherNotifierStub extends io.grpc.stub.AbstractAsyncStub<WeatherNotifierStub> {
    private WeatherNotifierStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherNotifierStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherNotifierStub(channel, callOptions);
    }

    /**
     */
    public void subscribeOn(sr.grpc.gen.Weather.WeatherNotifierRequest request,
        io.grpc.stub.StreamObserver<sr.grpc.gen.Weather.WeatherEvent> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeOnMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WeatherNotifierBlockingStub extends io.grpc.stub.AbstractBlockingStub<WeatherNotifierBlockingStub> {
    private WeatherNotifierBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherNotifierBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherNotifierBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<sr.grpc.gen.Weather.WeatherEvent> subscribeOn(
        sr.grpc.gen.Weather.WeatherNotifierRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeOnMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WeatherNotifierFutureStub extends io.grpc.stub.AbstractFutureStub<WeatherNotifierFutureStub> {
    private WeatherNotifierFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherNotifierFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherNotifierFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE_ON = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WeatherNotifierImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WeatherNotifierImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE_ON:
          serviceImpl.subscribeOn((sr.grpc.gen.Weather.WeatherNotifierRequest) request,
              (io.grpc.stub.StreamObserver<sr.grpc.gen.Weather.WeatherEvent>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WeatherNotifierBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WeatherNotifierBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return sr.grpc.gen.Weather.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WeatherNotifier");
    }
  }

  private static final class WeatherNotifierFileDescriptorSupplier
      extends WeatherNotifierBaseDescriptorSupplier {
    WeatherNotifierFileDescriptorSupplier() {}
  }

  private static final class WeatherNotifierMethodDescriptorSupplier
      extends WeatherNotifierBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WeatherNotifierMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WeatherNotifierGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WeatherNotifierFileDescriptorSupplier())
              .addMethod(getSubscribeOnMethod())
              .build();
        }
      }
    }
    return result;
  }
}
