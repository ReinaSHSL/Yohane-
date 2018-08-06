package yohanemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.lang.reflect.Field;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.charSelect.CharacterOption", method="updateHitbox")
public class AscensionPatch {
    @SpireInsertPatch(rloc=37)
    public static void Insert(Object __obj_instance) {
        Field maxAscensionLevel;
        try {
            Prefs pref = null;
            AbstractPlayer.PlayerClass chosenClass = CardCrawlGame.chosenCharacter;
            if (chosenClass.toString() == "FallenAngel") {
                pref = SaveHelper.getPrefs("FallenAngel");
                CardCrawlGame.sound.playV("AUTOMATON_ORB_SPAWN", 1.75f);
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);

                CharacterOption obj = (CharacterOption) __obj_instance;
                maxAscensionLevel = obj.getClass().getDeclaredField("maxAscensionLevel");
                maxAscensionLevel.setAccessible(true);

                CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = pref.getInteger("ASCENSION_LEVEL", 1);
                if (15 < CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel) {
                    CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel = 15;
                }
                maxAscensionLevel.set(obj, pref.getInteger("ASCENSION_LEVEL", 1));
                if (15 < maxAscensionLevel.getInt(obj)) {
                    maxAscensionLevel.set(obj, 15);
                }
                int ascensionLevel = CardCrawlGame.mainMenuScreen.charSelectScreen.ascensionLevel;
                if (ascensionLevel > maxAscensionLevel.getInt(obj)) {
                    ascensionLevel = maxAscensionLevel.getInt(obj);
                }
                if (ascensionLevel > 0) {
                    CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[ascensionLevel - 1];
                }
                else {
                    CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = "";
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}