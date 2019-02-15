package test;

import com.scj.demo.dubbo.consumer.spi.World;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

public class SPITest {

    @Test
    public void testSPI(){
        World world = ExtensionLoader.getExtensionLoader(World.class).getDefaultExtension();
        world.day(URL.valueOf("test://0.0.0.0:8888?person=coder"));
        System.out.println();
        world.day(URL.valueOf("test://0.0.0.0:8888?person=beauty&fish=jinlongyu"));
        System.out.println();
        world.day(URL.valueOf("test://0.0.0.0:8888?person=beauty&fish=jinmao"));
    }


}
