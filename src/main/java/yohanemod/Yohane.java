package yohanemod;

import java.util.ArrayList;

import basemod.animations.SpriterAnimation;

import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyMods;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import yohanemod.patches.YohaneEnum;
import characters.CustomCharSelectInfo;
import characters.AbstractPlayerWithMinions;

public class Yohane extends AbstractPlayerWithMinions {
	public static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
    public CustomCharSelectInfo getInfo() {
        return (CustomCharSelectInfo) getLoadout ();
    }

    public Yohane (String name, PlayerClass setClass) {
        super(name, setClass, null, null, null, new SpriterAnimation("charassets/animations.scml"));
		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

		initializeClass(null, "charassets/shoulder2.png", "charassets/shoulder.png", "charassets/corpse.png",
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
		
		if (Settings.dailyModsEnabled() && DailyMods.cardMods.get("Diverse")) {
			this.masterMaxOrbs = 1;
		}
	}


	public static ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Strike_Grey");
		retVal.add("Strike_Grey");
		retVal.add("Strike_Grey");
		retVal.add("Strike_Grey");
        retVal.add("Strike_Grey");
		retVal.add("Defend_Grey");
		retVal.add("Defend_Grey");
		retVal.add("Defend_Grey");
		retVal.add("Defend_Grey");
		retVal.add("Descent");
		retVal.add("Well_Laid_Ambush");
		return retVal;
	}
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("AngelWings");
		UnlockTracker.markRelicAsSeen("AngelWings");
		return retVal;
	}

    public static CharSelectInfo getLoadout() {

        CharSelectInfo info = new CustomCharSelectInfo (
                "Yohane",
                "A fallen angel from Japan. NL Uses dark magic and idol techniques.",
                70, //currentHP
                70, //maxHP
                0,  //maxOrbs
                2,  //maxMinions
                99, //gold
                5,  //cardDraw
                YohaneEnum.FallenAngel,
                getStartingRelics(),
                getStartingDeck(),
                false);
        return info;
    }

}
