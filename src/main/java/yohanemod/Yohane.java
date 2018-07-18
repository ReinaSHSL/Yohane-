package yohanemod;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Defect;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyMods;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import yohanemod.YohaneEnum;
import yohanemod.relics.AngelWings;

public class Yohane extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
	public static final String YOHANE_SHOULDER_2 = "charasssets/shoulder2.png"; // campfire pose
    public static final String YOHANE_SHOULDER_1 = "charassets/shoulder1.png"; // another campfire pose
	public static final String YOHANE_CORPSE = "charassets/corpse.png"; // dead corpse

	public Yohane (String name, PlayerClass setClass) {
		super(name, setClass, null, null, null, new SpriterAnimation("charassets/animations.scml"));
		
		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values
		
		initializeClass(null, YOHANE_SHOULDER_2, // required call to load textures and setup energy/loadout
				YOHANE_SHOULDER_1,
				YOHANE_CORPSE, 
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

	public static CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
		return new CharSelectInfo("Yohane", "A fallen angel from Japan who has descended upon the Spire. NL Uses dark magic and idol techniques.",
				70, 70, 0, 99, 5,
			YohaneEnum.FallenAngel	, getStartingRelics(), getStartingDeck(), false);
	}
	
}
