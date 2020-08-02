package test;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author shengchaojie
 * @date 2020/8/2
 **/
public class GenericResultTest {

    static GenericService genericService;

    Object result;

    @BeforeClass
    public static void setUp(){
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("test"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface("com.scj.demo.dubbo.provider.service.impl.GenericReturnValueService");
        referenceConfig.setGeneric(true);
        genericService = referenceConfig.get();
    }


    private void runGenericMethod(String methodName) {
        result = genericService.$invoke(
                methodName,
                new String[]{},
                new Object[]{});
        System.out.println(JSON.toJSONString(result));
        System.out.println(result.getClass());
    }

    @Test
    public void testReturnString(){
        runGenericMethod("returnString");
    }


    @Test
    public void testReturnLong(){
        runGenericMethod("returnLong");
    }

    @Test
    public void testReturnLongPrimitive(){
        runGenericMethod("returnPrimitiveLong");
    }

    @Test
    public void testReturnListString(){
        runGenericMethod("returnListString");
    }

    @Test
    public void testReturnArrayString(){
        runGenericMethod("returnArrayString");
    }

    @Test
    public void testReturnSetString(){
        runGenericMethod("returnSetString");
    }

    @Test
    public void testReturnPojo(){
        runGenericMethod("returnPojo");
    }

    @Test
    public void testReturnListPojo(){
        runGenericMethod("returnListPerson");
    }

}
