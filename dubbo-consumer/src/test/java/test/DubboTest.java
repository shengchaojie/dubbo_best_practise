package test;

import com.google.common.collect.Lists;
import com.scj.demo.dubbo.api.HelloService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.RegistryService;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:consumer.xml"})
public class DubboTest {

    @Autowired
    private HelloService helloService;

    @Autowired
    private RegistryService registryService;

    /**
     * 开始前打开本地的zookeeper
     * 使用ide开启第一个provider 端口为23333
     */


    /**
     * 测试异常filter
     * 二方接口 请用Result封装
     */
    @Test
    public void testException(){
        System.out.println(helloService.exception("1234").getCode());
    }

    /**
     * 虽然超时了 但是服务端还是执行了
     * 幂等性的重要性
     */
    @Test
    public void testTimeout(){
        helloService.timeout("234");
    }

    /**
     * 打开dubbo-api中org.apache.dubbo.rpc.Filter中的配置 去掉##
     * 重新开启Provider
     */

    /**
     * 动态配置 强制mock
     * 线上接口不可用 或者让资源 用mock
     */
    @Test
    public void testExceptionMock() throws InterruptedException {
        /**
         * url中dynamic=true代表创建的是临时节点 这个方法运行结束这个节点会消失
         */
        String overrideUrl ="override://0.0.0.0/com.scj.demo.dubbo.api.HelloService?category=configurators&methods=exception&dynamic=true&mock=force:return {'data':'scj123'}";
        registryService.register(URL.valueOf(overrideUrl));
        Assert.assertEquals("scj123",helloService.exception("1234").getData());

    }


    /**
     * 分页同步调用
     * @throws IOException
     */
    @Test
    public void testMantorySynInvoke() throws IOException {
        long now = System.currentTimeMillis();
        List<String> data = new ArrayList<>();
        Lists.newArrayList(1,2,3,4,5,6,7).stream().forEach(i->{
            data.addAll(helloService.testPage1(20,i).getData());
        });
        System.out.println(JSON.json(data));
        System.out.println(System.currentTimeMillis()-now);
    }

    /**
     * 分页本地异步调用优化
     * 多个线程会阻塞
     * @throws IOException
     */
    @Test
    public void testMantoryAsynInvoke() throws IOException {
        long now = System.currentTimeMillis();
        List<String> data = new ArrayList<>();
        CompletableFuture[] cfs1 = Lists.newArrayList(1,2,3,4,5,6,7)
                .stream().map(i -> CompletableFuture.supplyAsync(()->helloService.testPage1(20,i)).whenComplete((r,e)->{
                    data.addAll(r.getData());
                })).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs1).join();
        System.out.println(JSON.json(data));
        System.out.println(System.currentTimeMillis()-now);
    }

    /**
     * dubbo2.7异步调用方式
     * 这种方式能节省线程 只会阻塞一个线程
     * @throws IOException
     */
    @Test
    public void testNewAsynInvoke() throws IOException {
        long now = System.currentTimeMillis();
        List<String> data = new ArrayList<>();
        CompletableFuture[] cfs2 = Lists.newArrayList(1,2,3,4,5,6,7)
                .stream().map( i -> helloService.testPage2(20,i).whenComplete((r,e)->{
                    data.addAll(r.getData());
                })).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs2).join();
        System.out.println(JSON.json(data));
        System.out.println(System.currentTimeMillis()-now);
    }


    /**
     * 测试RegistryService的notify特性
     * 我们通过category=configurators,providers,routers订阅者三个目录
     * 一旦这三个目录有一个变化 会分别通知三次
     * 如果目录下为空 会通知一个empty的url
     */
    @Test
    public void testRegistryService(){
        registryService.subscribe(URL.valueOf("dubbo://172.17.8.49:21111/com.scj.demo.dubbo.api.HelloService?category=configurators,providers,routers"), new NotifyListener() {
            @Override
            public void notify(List<URL> urls) {
                System.out.println("notify....");
                urls.stream().forEach(System.out::println);
            }
        });

        String overrideUrl ="override://0.0.0.0/com.scj.demo.dubbo.api.HelloService?category=configurators&methods=exception&dynamic=true&mock=force:return {'data':'scj123'}";
        registryService.register(URL.valueOf(overrideUrl));
    }

    /**
     *  下面测试服务治理功能
     *  先通过 mvn clean package -DskipTests 构造出dubbodemo.jar
     *  cd dubbo-provider/target
     *  通过 nohup java -jar -Ddubbo.protocol.port=23334 dubbodemo.jar 2>&1 > log & 开启第二个提供者
     *  关闭可以通过 ps -ef |grep dubbodemo.jar |grep -v grep | awk '{print $2}' |xargs kill -9
     */

    /**
     * 测试修改权重
     */
    @Test
    public void testOverrideWeight(){

        String overrideUrl ="override://0.0.0.0:23333/com.scj.demo.dubbo.api.HelloService?category=configurators&dynamic=true&weight=300";
        registryService.register(URL.valueOf(overrideUrl));

        String overrideUrl2 ="override://0.0.0.0:23334/com.scj.demo.dubbo.api.HelloService?category=configurators&dynamic=true&weight=200";
        registryService.register(URL.valueOf(overrideUrl2));

        HashMap<Integer,Integer> statistics = new HashMap<>();
        for(int i =0;i<10;i++){
            helloService.hello("123");
            int port = RpcContext.getContext().getUrl().getPort();
            String weight = RpcContext.getContext().getUrl().getParameter("weight");
            Integer count = statistics.get(port);
            if(count==null){
                statistics.put(port,1);
            }else{
                statistics.put(port,count+1);
            }

            System.out.println(port + " "+ weight);
        }

        Assert.assertEquals(statistics.get(23333),Integer.valueOf(6));
        Assert.assertEquals(statistics.get(23334),Integer.valueOf(4));
    }

    /**
     * 测试禁用23333端口的提供者
     * @throws InterruptedException
     */
    @Test
    public void testOverrideDisable() throws InterruptedException {
        String overrideUrl ="override://0.0.0.0:23333/com.scj.demo.dubbo.api.HelloService?category=configurators&dynamic=true&disabled=true";
        registryService.register(URL.valueOf(overrideUrl));

        HashMap<Integer,Integer> statistics = new HashMap<>();
        for(int i =0;i<10;i++){
            helloService.hello("123");
            int port = RpcContext.getContext().getUrl().getPort();
            String weight = RpcContext.getContext().getUrl().getParameter("weight");
            Integer count = statistics.get(port);
            if(count==null){
                statistics.put(port,1);
            }else{
                statistics.put(port,count+1);
            }
            System.out.println(port + " "+ weight);
        }
        Assert.assertEquals(statistics.get(23334),Integer.valueOf(10));

    }

    /**
     * 测试通过路由 是消费者只能访问到23334的服务
     */
    @Test
    public void testRouter(){
        String routerUrl = "condition://0.0.0.0/com.scj.demo.dubbo.api.HelloService?category=routers&dynamic=true&rule="+URL.encode("=> port = 23334");
        registryService.register(URL.valueOf(routerUrl));

        HashMap<Integer,Integer> statistics = new HashMap<>();
        for(int i =0;i<10;i++){
            helloService.hello("123");
            int port = RpcContext.getContext().getUrl().getPort();
            String weight = RpcContext.getContext().getUrl().getParameter("weight");
            Integer count = statistics.get(port);
            if(count==null){
                statistics.put(port,1);
            }else{
                statistics.put(port,count+1);
            }
            System.out.println(port + " "+ weight);
        }
        Assert.assertEquals(statistics.get(23334),Integer.valueOf(10));

    }
}
