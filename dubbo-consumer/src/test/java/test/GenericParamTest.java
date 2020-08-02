package test;

import com.google.common.collect.Lists;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengchaojie
 * @date 2020/8/2
 **/
public class GenericParamTest {


    static GenericService genericService;

    Object result;

    Map<String,Object> personMap = new HashMap<>();
    {
        personMap.put("name","scj");
        personMap.put("age","12");
        personMap.put("birth",new Date());
    }

    @BeforeClass
    public static void setUp(){
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(new ApplicationConfig("test"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        referenceConfig.setInterface("com.scj.demo.dubbo.provider.service.impl.ByeService");
        referenceConfig.setGeneric(true);
        genericService = referenceConfig.get();
    }

    @Test
    public void testNormal(){
        result = genericService.$invoke(
                "bye",
                new String[]{"java.lang.String"},
                new Object[]{"scj"});
        System.out.println(result);
    }

    @Test
    public void testMultiParam(){
        result = genericService.$invoke(
                "bye",
                new String[]{"java.lang.String","java.lang.Long","java.util.Date"},
                new Object[]{"scj",12L,new Date()});

        System.out.println(result);
    }

    @Test
    public void testPOJO(){

        result = genericService.$invoke(
                "bye",
                new String[]{"com.scj.demo.dubbo.provider.service.impl.ByeService$Person"},
                new Object[]{personMap});

        System.out.println(result);
    }

    @Test
    public void testMap(){
        result = genericService.$invoke(
                "bye",
                new String[]{"java.util.Map"},
                new Object[]{personMap});

        System.out.println(result);
    }

    @Test
    public void testList(){
        List<String> names = Lists.newArrayList("scj1","scj2");
        result = genericService.$invoke(
                "bye",
                new String[]{"java.util.List"},
                new Object[]{names});

        System.out.println(result);
    }

    @Test
    public void testArray(){
        String[] nameArray = new String[]{"scj1","scj3"};
        result = genericService.$invoke(
                "bye",
                new String[]{"java.lang.String[]"},
                new Object[]{nameArray});

        System.out.println(result);
    }

    @Test
    public void testPOJOList(){
        result = genericService.$invoke(
                "byePersons",
                new String[]{"java.util.List"},
                new Object[]{Lists.newArrayList(personMap,personMap)});

        System.out.println(result);
    }

    @Test
    public void testPOJOArray(){
        result = genericService.$invoke(
                "byePersons",
                new String[]{"com.scj.demo.dubbo.provider.service.impl.ByeService$Person[]"},
                new Object[]{Lists.newArrayList(personMap,personMap)});

        System.out.println(result);
    }
}
