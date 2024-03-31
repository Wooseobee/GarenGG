package org.example.apikeycrawling.global;

import org.example.apikeycrawling.entity.mysql.ApiKey;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

public class GlobalConstants {

    public static final int corePoolSize = 250;
    public static final int maxPoolSize = 250;
    public static final int queueCapacity = 500;

    public static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    public static RestTemplate restTemplate = new RestTemplate();

    public static String[] lowTiers = new String[]{"DIAMOND", "EMERALD", "PLATINUM", "GOLD", "SILVER", "BRONZE", "IRON"};
    public static String[] divisions = new String[]{"I", "II", "III", "IV"};

    //        public static String[] highTiers = new String[]{"CHALLENGER", "GRANDMASTER", "MASTER"};
    public static String[] highTiers = new String[]{"MASTER"};

    public static List<ApiKey> apiKeys;
}
