package org.example.garencrawling.global;

import org.openqa.selenium.chrome.ChromeOptions;

import java.text.SimpleDateFormat;
import java.util.*;

public class GlobalConstants {

    public static final int corePoolSize = 10;
    public static final int maxPoolSize = 20;
    public static final int queueCapacity = 100;

    public static final int threadSize = 5;
    public static final int saveSize = 10;

    public static final int waitTime = 10;
    public static final int sleepTime = 5000;

    public static final int tryMaxCount = 5;

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final HashMap<String, String> championNames = new HashMap<>();

    public static ChromeOptions options;

}