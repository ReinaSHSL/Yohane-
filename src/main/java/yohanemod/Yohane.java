package yohanemod;

import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.characters.CustomCharSelectInfo;
import yohanemod.cards.Descent;
import yohanemod.patches.YohaneEnum;
import yohanemod.screens.LittleDemonScreen;

import java.util.ArrayList;

public class Yohane extends AbstractPlayerWithMinions {
	public static final int ENERGY_PER_TURN = 3; // how much energy you get every turn
	private static final Color GREY = CardHelper.getColor(131.0f, 156.0f, 165.0f);
    public CustomCharSelectInfo getInfo() {
        return (CustomCharSelectInfo) getLoadout ();
    }

    public Yohane (String name) {
        super(name,YohaneEnum.FallenAngel, null, null, new SpriterAnimation("charassets/animations.scml"));
		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

		initializeClass(null, "charassets/shoulder2.png", "charassets/shoulder.png", "charassets/corpse.png",
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

		YohaneMod.lds = new LittleDemonScreen();
	}


	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Yohane:Strike_Grey");
		retVal.add("Yohane:Strike_Grey");
		retVal.add("Yohane:Strike_Grey");
		retVal.add("Yohane:Strike_Grey");
		retVal.add("Yohane:Strike_Grey");
		retVal.add("Yohane:Defend_Grey");
		retVal.add("Yohane:Defend_Grey");
		retVal.add("Yohane:Defend_Grey");
		retVal.add("Yohane:Defend_Grey");
		retVal.add("Yohane:Descent");
		retVal.add("Yohane:Well_Laid_Ambush");
		return retVal;
	}

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Yohane:AngelWings");
		UnlockTracker.markRelicAsSeen("Yohane:AngelWings");
		return retVal;
	}

    public CharSelectInfo getLoadout() {

        CharSelectInfo info = new CustomCharSelectInfo (
                "Yohane",
                "A fallen angel from Japan. NL Uses dark magic and idol techniques.",
                60, //currentHP
                60, //maxHP
                0,  //maxOrbs
                2,  //maxMinions
                99, //gold
                5,  //cardDraw
                this,
                getStartingRelics(),
                getStartingDeck(),
                false);
        return info;
    }

	@Override
	public String getTitle(PlayerClass playerClass) {
		return "the Fallen Angel";
	}

	@Override
	public Color getCardColor() {
		return GREY;
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new Descent();
	}

	@Override
	public Color getCardTrailColor() {
		return GREY;
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 5;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("ATTACK_FIRE", MathUtils.random(-0.2f, 0.2f));
		CardCrawlGame.sound.playA("ATTACK_FAST", MathUtils.random(-0.2f, 0.2f));
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);

	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_FIRE";
	}

	@Override
	public String getLocalizedCharacterName() {
		return "Yohane";
	}

	@Override
	public AbstractPlayer newInstance() {
		return new Yohane(this.name);
	}

}
