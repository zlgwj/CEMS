import jep.JepConfig;
import jep.MainInterpreter;
import jep.SubInterpreter;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {

    String key = "1ea9cfa19083e0dfa80d11a0ef1f0c3f";

    String jepPath = "D:\\Environment\\anaconda\\Lib\\site-packages\\jep\\jep.dll";
    @Test
    public void test() {
        MainInterpreter.setJepLibraryPath(jepPath);
        JepConfig config = new JepConfig();
        config.addIncludePaths("E:\\GisData\\code\\TVDI");
        try (SubInterpreter subInterpreter = new SubInterpreter(config)) {
            subInterpreter.eval("from TVDI import *");
            Object invokeNoArgs = subInterpreter.invoke("calTVDI",
                    "E:\\GisData\\code\\TVDI\\MyData\\MOD13A3\\NDVItif_warp_SpatialIinterpolation_SGFilter\\2023-01-01_wrap_SpatialIinterpolation_SGfilter.tif",
                    "E:\\GisData\\code\\TVDI\\MyData\\MOD11A2\\LSTtif_warp_SpatialIinterpolation_SGFilter_Corr_Month\\2023-01Month.tif",
                    "E:\\GisData\\code\\TVDI\\MyData\\TVDIResult\\rest.tif");

            System.out.println("结果："+invokeNoArgs);
            System.out.println("有结果吗？");
        }
    }

    public static void main(String[] args) {
        System.out.printf("%s");
    }
}
