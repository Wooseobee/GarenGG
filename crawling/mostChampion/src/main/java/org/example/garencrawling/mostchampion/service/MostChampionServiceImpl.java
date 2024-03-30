package org.example.garencrawling.mostchampion.service;

import lombok.RequiredArgsConstructor;
import org.example.garencrawling.mostchampion.domain.readmongo.Participant;
import org.example.garencrawling.mostchampion.domain.readmongo.PlayerMatch;
import org.example.garencrawling.mostchampion.domain.readmysql.PlayerInfo;
import org.example.garencrawling.mostchampion.domain.tmp.WinLose;
import org.example.garencrawling.mostchampion.domain.writemongo.MostData;
import org.example.garencrawling.mostchampion.domain.writemongo.PlayerMost;
import org.example.garencrawling.mostchampion.repository.PlayerInfoRepository;
import org.example.garencrawling.mostchampion.repository.PlayerMatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

@Service
@RequiredArgsConstructor
public class MostChampionServiceImpl implements MostChampionService {

    private final PlayerMatchRepository playerMatchRepository;
    private final PlayerInfoRepository playerInfoRepository;
    private final PlayerMostRepository playerMostRepository;

    @Override
    public void calculateMostChampion() {

    }
}