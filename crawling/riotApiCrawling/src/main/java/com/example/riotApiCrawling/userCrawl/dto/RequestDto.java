package com.example.riotApiCrawling.userCrawl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    String tier;
    Integer startRank;
    Integer endRank;
    Integer startPageNum;
    Integer endPageNum;
    String apiKey;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Integer getStartRank() {
        return startRank;
    }

    public void setStartRank(Integer startRank) {
        this.startRank = startRank;
    }

    public Integer getEndRank() {
        return endRank;
    }

    public void setEndRank(Integer endRank) {
        this.endRank = endRank;
    }

    public Integer getStartPageNum() {
        return startPageNum;
    }

    public void setStartPageNum(Integer startPageNum) {
        this.startPageNum = startPageNum;
    }

    public Integer getEndPageNum() {
        return endPageNum;
    }

    public void setEndPageNum(Integer endPageNum) {
        this.endPageNum = endPageNum;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
