package org.example.garencrawling;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class testProxy {

    public static void main(String[] args) throws InterruptedException {

        ChromeOptions options;
        ChromeDriver driver;

        String[] proxyAddress = new String[]{
//                "52.79.226.142:3128", // 진용
//                "15.164.142.18:3128", // 우섭
//                "13.209.64.11:3128", // 준범
//                "15.164.221.137:3128", // 진용2
//                "115.144.140.64:8302" // 하이온넷

                "15.236.106.236:3128"

        };

        String[] proxyAddress2 = new String[]{

        };


        for (int i = 0; i < proxyAddress.length; i++) {

            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxyAddress[i])
                    .setFtpProxy(proxyAddress[i])
                    .setSslProxy(proxyAddress[i])
            ;

            WebDriverManager.chromedriver().setup();
            options = new ChromeOptions();

            options.setProxy(proxy);
            options.addArguments("--disable-gpu"); // 최신 버전에서는 필요하지 않지만 호환성을 위해 추천

            ////////////////////////////////////////////////////////////////////////////////////////////

            driver = new ChromeDriver(options);

            driver.get("https://www.fow.kr");
//            driver.get("https://www.op.gg/summoners/kr/진뚱이용-KR1");
//            driver.get("https://www.naver.com");

            Thread.sleep(1000);
            driver.quit();
        }
    }
}
