package yohanemod.patches;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;
import java.io.*;
import java.net.*;
import java.util.*;

import static com.megacrit.cardcrawl.metrics.Metrics.timestampFormatter;

@SpirePatch(
        cls="com.megacrit.cardcrawl.metrics.Metrics",
        method="run"
)
public class MetricsPatch {
    @SpireInsertPatch(rloc=12)
    public static void Insert(Metrics __dataToSend) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        final Logger logger = LogManager.getLogger(Metrics.class.getName());
        Gson gson = new Gson();
        if (Settings.isModded) {
            HashMap<String, Object> paramData = new HashMap<String, Object>();
            boolean death = __dataToSend.death;
            MonsterGroup monsters = __dataToSend.monsters;
            long lastPlaytimeEnd;
            System.out.println("PATCH SUCCESS");
            paramData.put("play_id", UUID.randomUUID().toString());
            paramData.put("build_version", CardCrawlGame.TRUE_VERSION_NUM);
            paramData.put("seed_played", Settings.seed.toString());
            paramData.put("chose_seed", Settings.seedSet);
            paramData.put("seed_source_timestamp", Settings.seedSourceTimestamp);
            paramData.put("is_daily", Settings.isDailyRun);
            paramData.put("special_seed", Settings.specialSeed);
            if (Settings.dailyMods != null && Settings.dailyMods.getEnabledModIDs().size() > 0) {
                paramData.put("daily_mods", Settings.dailyMods.getEnabledModIDs());
            }

            paramData.put("is_trial", Settings.isTrial);
            paramData.put("is_endless", Settings.isEndless);
            if (death) {
                AbstractPlayer player = AbstractDungeon.player;
                CardCrawlGame.metricData.current_hp_per_floor.add(player.currentHealth);
                CardCrawlGame.metricData.max_hp_per_floor.add(player.maxHealth);
                CardCrawlGame.metricData.gold_per_floor.add(player.gold);
            }

            paramData.put("is_ascension_mode", AbstractDungeon.isAscensionMode);
            paramData.put("ascension_level", AbstractDungeon.ascensionLevel);
            paramData.put("neow_bonus", CardCrawlGame.metricData.neowBonus);
            paramData.put("neow_cost", CardCrawlGame.metricData.neowCost);
            paramData.put("is_beta", Settings.isBeta);
            paramData.put("is_prod", Settings.isDemo);
            paramData.put("victory", !death);
            paramData.put("floor_reached", AbstractDungeon.floorNum);
            paramData.put("score", DeathScreen.calcScore(!death));
            lastPlaytimeEnd = System.currentTimeMillis() / 1000L;
            paramData.put("timestamp", lastPlaytimeEnd);
            paramData.put("local_time", timestampFormatter.format(Calendar.getInstance().getTime()));
            paramData.put("playtime", (long)CardCrawlGame.playtime);
            paramData.put("player_experience", Settings.totalPlayTime);
            paramData.put("master_deck", AbstractDungeon.player.masterDeck.getCardIdsForMetrics());
            paramData.put("relics", AbstractDungeon.player.getRelicNames());
            paramData.put("gold", AbstractDungeon.player.gold);
            paramData.put("campfire_rested", CardCrawlGame.metricData.campfire_rested);
            paramData.put("campfire_upgraded", CardCrawlGame.metricData.campfire_upgraded);
            paramData.put("purchased_purges", CardCrawlGame.metricData.purchased_purges);
            paramData.put("potions_floor_spawned", CardCrawlGame.metricData.potions_floor_spawned);
            paramData.put("potions_floor_usage", CardCrawlGame.metricData.potions_floor_usage);
            paramData.put("current_hp_per_floor", CardCrawlGame.metricData.current_hp_per_floor);
            paramData.put("max_hp_per_floor", CardCrawlGame.metricData.max_hp_per_floor);
            paramData.put("gold_per_floor", CardCrawlGame.metricData.gold_per_floor);
            paramData.put("path_per_floor", CardCrawlGame.metricData.path_per_floor);
            paramData.put("path_taken", CardCrawlGame.metricData.path_taken);
            paramData.put("items_purchased", CardCrawlGame.metricData.items_purchased);
            paramData.put("item_purchase_floors", CardCrawlGame.metricData.item_purchase_floors);
            paramData.put("items_purged", CardCrawlGame.metricData.items_purged);
            paramData.put("items_purged_floors", CardCrawlGame.metricData.items_purged_floors);
            paramData.put("character_chosen", AbstractDungeon.player.chosenClass.name());
            paramData.put("card_choices", CardCrawlGame.metricData.card_choices);
            paramData.put("event_choices", CardCrawlGame.metricData.event_choices);
            paramData.put("boss_relics", CardCrawlGame.metricData.boss_relics);
            paramData.put("damage_taken", CardCrawlGame.metricData.damage_taken);
            paramData.put("potions_obtained", CardCrawlGame.metricData.potions_obtained);
            paramData.put("relics_obtained", CardCrawlGame.metricData.relics_obtained);
            paramData.put("campfire_choices", CardCrawlGame.metricData.campfire_choices);
            paramData.put("circlet_count", AbstractDungeon.player.getCircletCount());
            String data;
            Prefs pref;
            switch(AbstractDungeon.player.chosenClass) {
                default:
                    pref = SaveHelper.getPrefs("FallenAngel");
            }

            int numVictory = pref.getInteger("WIN_COUNT", 0);
            int numDeath = pref.getInteger("LOSE_COUNT", 0);
            if (numVictory <= 0) {
                paramData.put("win_rate", 0.0F);
            } else {
                paramData.put("win_rate", numVictory / (numDeath + numVictory));
            }

            if (death && monsters != null) {
                paramData.put("killed_by", AbstractDungeon.lastCombatMetricKey);
            } else {
                paramData.put("killed_by", (Object)null);
            }
            HashMap event = new HashMap();
            event.put("event", paramData);
            if (Settings.isBeta) {
                event.put("host", CardCrawlGame.playerName);
            } else {
                event.put("host", CardCrawlGame.alias);
            }

            event.put("time", System.currentTimeMillis() / 1000L);
            data = gson.toJson(event);
            System.out.println(data);


           String urlMod = "https://api.jsonbin.io/b";
           String metricsKey = "$2a$10$2FQgw/H4kHPyrskUP5ht9ekPhkaFtG1n0Qds.Ea/vE7tybZTFUbeu";
            HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
            Net.HttpRequest httpRequest = requestBuilder.newRequest().method("POST").url(urlMod).header("Content-Type",
                    "application/json").header("Accept", "application/json").header("User-Agent", "curl/7.43.0").header
                    ("secret-key", metricsKey).header("collection-id", "5b792360181c7944f023c63b").header("private", "false").build();
            httpRequest.setContent(data);
            Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    logger.info("Metrics: http request response: " + httpResponse.getResultAsString());

                }

                public void failed(Throwable t) {
                    logger.info("Metrics: http request failed: " + t.toString());
                }

                public void cancelled() {
                    logger.info("Metrics: http request cancelled.");
                }
            });
        }
    }
}
