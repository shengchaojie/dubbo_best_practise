# dubbo_best_practise
采用dubbo 2.7版本  
测试案例见  
@see  DubboTest  
@see  SPITest

# core class
## SPI FrameWork  
ExtensionLoader

## config load and init  
DubboNamespaceHandler  
ServiceBean  
ReferenceBean  

## service export 
ProxyFactory  
ServiceBean  
RegistryProtocol   
DubboProtocol  
HeaderExchangeServer  
NettyTransporter  
NettyServer  

## service refer 
ProxyFactory  
ReferenceBean  
RegistryProtocol  
RegistryDirectory  
DubboProtocol  
DubboInvoker  
NettyTransporter  
NettyClient  


## service invoke
ProxyFactory  
DubboInvoker  
HeaderExchangeClient  
DefaultFilter  


### Dubbo Netty Handler
#### 所有handler
codecHandler netty线程 用于codec 将二进制转换为对象  
IdleStateHandler netty线程  netty框架心跳  
NettyClientHandler/NettyServerHandler会调用到下面的handler 但是根据netty事件 使用链路不同   

MultiMessageHandler netty线程 处理粘包  
HeartbeatHandler netty线程 dubbo框架心跳  
Dispatcher    Dubbo IO模型 将下面的Handler放在Dubbo 业务线程执行  
DecodeHandler codec那边没有完全反序列化 反序列化操作可以延迟到这里 dubbo业务线程  
HeaderExchangeHandler dubbo业务线程 处理Exchanger层逻辑  
DubboProtocol.requestHandler dubbo业务线程 

#### client  

##### in  Response/HeartBeat
channelRead事件调用链  

NettyCodecAdapter.getDecoder  
IdleStateHandler  

NettyClientHandler 会调用内部handler的received方法  
MultiMessageHandler  可能会走MultiMessage处理逻辑  
HeartbeatHandler  走心跳逻辑 判断是心跳请求包还是回应包 如果是请求包那么返回 如果是回应包那么调用链结束  
Dispatcher  将handler放到Dubbo线程池运行    
DecodeHandler  decode  
HeaderExchangeHandler->handleResponse 如果是Response调用DefaultFuture.received唤醒对应阻塞的Request  
DubboProtocol.requestHandler->invoker  这边理论上也有可能 但是很少用到 服务端回调消费者接口    

##### out Request/HeartBeat
write事件调用链

NettyClientHandler  会调用NettyClientHandler中handler的sent方法  
NettyCodecAdapter.getEncoder       
IdleStateHandler  
MultiMessageHandler  无逻辑   
HeartbeatHandler  设置WRITE_TIMESTAMP  
Dispatcher  无逻辑  
DecodeHandler  无逻辑  
HeaderExchangeHandler 调用  DefaultFuture的sent方法  
DubboProtocol.requestHandler 无逻辑       

  

#### server 

##### in Request/HeartBeat
channelRead事件调用链 会调用内部handler的received方法 

下面同client的in

NettyCodecAdapter.getDecoder  
IdleStateHandler  

NettyServerHandler 
MultiMessageHandler  
HeartbeatHandler  
Dispatcher  
DecodeHandler  
HeaderExchangeHandler->handleRequest/telnet   
DubboProtocol.requestHandler->invoker    


##### out Response/HeartBeat
write事件调用链  

NettyServerHandler 会调用NettyClientHandler中handler的sent方法
IdleStateHandler
NettyCodecAdapter.getEncoder 
  
MultiMessageHandler  无逻辑
HeartbeatHandler  设置WRITE_TIMESTAMP
Dispatcher  无逻辑
DecodeHandler  无逻辑
HeaderExchangeHandler 无逻辑
DubboProtocol.requestHandler 无逻辑
       
  

### invoke filters
consumer side  

org.apache.dubbo.rpc.filter.ConsumerContextFilter  
org.apache.dubbo.rpc.protocol.dubbo.filter.FutureFilter  
org.apache.dubbo.monitor.support.MonitorFilter  


provider side

org.apache.dubbo.rpc.filter.EchoFilter  
org.apache.dubbo.rpc.filter.ClassLoaderFilter  
org.apache.dubbo.rpc.filter.GenericFilter  
org.apache.dubbo.rpc.filter.ContextFilter  
org.apache.dubbo.rpc.protocol.dubbo.filter.TraceFilter  
org.apache.dubbo.rpc.filter.TimeoutFilter  
org.apache.dubbo.monitor.support.MonitorFilter  
com.scj.demo.dubbo.api.ExceptionFilter # 自定义filter  
org.apache.dubbo.rpc.filter.ExceptionFilter  