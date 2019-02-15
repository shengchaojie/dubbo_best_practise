# dubbo_best_practise
采用dubbo 2.7版本  
测试案例见
@see  DubboTest  
@see  SPITest

## Dubbo Netty Handler
client  

codecHandler netty线程 用于codec 将二进制转换为对象  
IdleStateHandler netty线程  netty框架心跳  
NettyClientHandler由下面Handler调用链组成  

MultiMessageHandler netty线程 处理粘包  
HeartbeatHandler netty线程 dubbo框架心跳  
Dispatcher    Dubbo IO模型 将下面的Handler放在Dubbo 业务线程执行  
DecodeHandler codec那边没有完全反序列化 反序列化操作可以延迟到这里 dubbo业务线程  
HeaderExchangeHandler dubbo业务线程 处理Exchanger层逻辑  
dubboProtocol.requestHandler dubbo业务线程   

server段类似

codecHandler
IdleStateHandler
NettyServerHandler

MultiMessageHandler
HeartbeatHandler
Dispatcher
DecodeHandler
HeaderExchangeHandler
dubboProtocol.requestHandler