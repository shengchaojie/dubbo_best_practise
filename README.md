# dubbo_best_practise
采用dubbo 2.7版本  
测试案例见
@see  DubboTest  
@see  SPITest

# core class
## SPI FrameWork core 
ExtensionLoader

## config load and trigger init  
DubboNamespaceHandler  
ServiceBean  
ReferenceBean  

## service export core class
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
client  

codecHandler netty线程 用于codec 将二进制转换为对象  
IdleStateHandler netty线程  netty框架心跳  
NettyClientHandler由下面Handler调用链组成  

MultiMessageHandler netty线程 处理粘包  
HeartbeatHandler netty线程 dubbo框架心跳  
Dispatcher    Dubbo IO模型 将下面的Handler放在Dubbo 业务线程执行  
DecodeHandler codec那边没有完全反序列化 反序列化操作可以延迟到这里 dubbo业务线程  
HeaderExchangeHandler dubbo业务线程 处理Exchanger层逻辑  
DubboProtocol.requestHandler dubbo业务线程  执行目标对象方法

server端类似  

codecHandler  
IdleStateHandler  
NettyServerHandler  

MultiMessageHandler  
HeartbeatHandler  
Dispatcher  
DecodeHandler  
HeaderExchangeHandler  
DubboProtocol.requestHandler  

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