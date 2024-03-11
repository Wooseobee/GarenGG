package org.example.garencrawling.global;

import java.util.*;

public class GlobalConstants {

    public static final int corePoolSize = 10;
    public static final int maxPoolSize = 20;
    public static final int queueCapacity = 100;

    public static final String[] proxyAddress = new String[]{
            "52.79.226.142:3128", // 진용
            "15.164.142.18:3128", // 우섭
            "13.209.64.11:3128", // 준범
            "15.164.221.137:3128" // 진용2
    };

    public static int threadSize;

    public static final int saveSize = 10;
    public static final int sleepTime = 5000;

    public static final HashMap<String, String> championNames = new HashMap<>();
}
