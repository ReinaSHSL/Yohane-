package yohanemod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

@SpirePatch(
        cls="com.megacrit.cardcrawl.metrics.Metrics",
        method="run"
)
public class MetricsPatch {
    private static boolean death;
    private static MonsterGroup monsters;
    private static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    @SpireInsertPatch(rloc=12)
    public static void Insert(Metrics __dataToSend) throws NoSuchMethodException {
        if (Settings.isModded) {
            System.out.println("PATCH SUCCESS");
            Method gatherData = Metrics.class.getDeclaredMethod("gatherAllData", boolean.class, MonsterGroup.class);
            gatherData.setAccessible(true);
        }
    }
}
