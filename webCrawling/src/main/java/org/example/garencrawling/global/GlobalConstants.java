package org.example.garencrawling.global;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.*;

public class GlobalConstants {

    public static final int corePoolSize = 12;
    public static final int maxPoolSize = 12;
    public static final int queueCapacity = 50;

    public static final int threadSize = 12;
    public static final int saveSize = 10;
    public static final int waitTime = 5;

    public static final HashMap<String, String> championNames = new HashMap<>();
    public static final String[] officialChampions = {
            "Aatrox", "Ahri", "Akali", "Akshan", "Alistar", "Amumu", "Anivia", "Annie", "Aphelios", "Ashe",
            "Aurelion Sol", "Azir", "Bard", "Bel'Veth", "Blitzcrank", "Brand", "Braum", "Briar", "Caitlyn", "Camille",
            "Cassiopeia", "Cho'Gath", "Corki", "Darius", "Diana", "Draven",  "Dr. Mundo","Ekko", "Elise", "Evelynn",
            "Ezreal", "Fiddlesticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen", "Gnar", "Gragas", "Graves",
            "Gwen", "Hecarim", "Heimerdinger", "Hwei", "Illaoi", "Irelia", "Ivern", "Janna", "Jarvan IV", "Jax",
            "Jayce", "Jhin", "Jinx",  "Kai'Sa", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina",
            "Kayle", "Kayn", "Kennen", "Kha'Zix", "Kindred", "Kled", "Kog'Maw", "K'Sante","LeBlanc", "Lee Sin", "Leona",
            "Lillia", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai", "Master Yi", "Milio",
            "Miss Fortune", "Wukong", "Mordekaiser", "Morgana", "Naafiri", "Nami", "Nasus", "Nautilus", "Neeko", "Nidalee", "Nilah",
            "Nocturne", "Nunu & Willump", "Olaf", "Orianna", "Ornn", "Pantheon", "Poppy", "Pyke", "Qiyana", "Quinn",
            "Rakan", "Rammus", "Rek'Sai", "Rell", "Renata Glasc", "Renekton", "Rengar", "Riven", "Rumble", "Ryze",
            "Samira", "Sejuani", "Senna", "Seraphine", "Sett", "Shaco", "Shen", "Shyvana", "Singed", "Sion",
            "Sivir", "Skarner", "Smolder", "Sona", "Soraka", "Swain", "Sylas", "Syndra", "Tahm Kench", "Taliyah",
            "Talon", "Taric", "Teemo", "Thresh", "Tristana", "Trundle", "Tryndamere", "Twisted Fate", "Twitch", "Udyr",
            "Urgot", "Varus", "Vayne", "Veigar", "Vel'Koz", "Vex", "Vi", "Viego", "Viktor", "Vladimir",
            "Volibear", "Warwick", "Xayah", "Xerath", "Xin Zhao", "Yasuo", "Yone", "Yorick", "Yuumi",
            "Zac", "Zed", "Zeri", "Ziggs", "Zilean", "Zoe", "Zyra"
    };


    public static ArrayList<ChromeOptions> optionsList = new ArrayList<>();
    public static ArrayList<String> proxyList = new ArrayList<>();
    public static ArrayList<ChromeDriver> drivers = new ArrayList<>();
    public static ArrayList<WebDriverWait> waits = new ArrayList<>();

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}