package org.example.garencrawling.global;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.*;

public class GlobalConstants {

    public static final int corePoolSize = 8;
    public static final int maxPoolSize = 8;
    public static final int queueCapacity = 50;

    public static final int threadSize = 8;
    public static final int saveSize = 10;
    public static final int waitTime = 5;

    public static final HashMap<String, String> championNames = new HashMap<>();

    public static ArrayList<ChromeOptions> optionsList = new ArrayList<>();
    public static ArrayList<Proxy> proxyList = new ArrayList<>();

    public static ArrayList<ChromeDriver> drivers = new ArrayList<>();
    public static ArrayList<WebDriverWait> waits = new ArrayList<>();

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}