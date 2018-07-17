package yohanemod;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.SetUnlocksSubscriber;

@SpireInitializer
public class YohaneMod implements EditCharactersSubscriber{
	
	private static final String MODNAME = "Yohane!";
    private static final String AUTHOR = "Reina";
    private static final String DESCRIPTION = "Adds Yohane as a playable character.";
    
    private static final Color GREY = CardHelper.getColor(131.0f, 156.0f, 165.0f);
    private static final String ATTACK_GREY = "cardBG/bg_attack_grey.png";
    private static final String SKILL_GREY = "cardBG/bg_skill_grey.png";
    private static final String POWER_GREY = "cardBG/bg_power_grey.png";
    private static final String ENERGY_ORB_GREY = "cardBG/card_grey_orb.png";

    private static final String ATTACK_GREY_PORTRAIT = "cardBGStronk/bg_attack_grey.png";
    private static final String SKILL_GREY_PORTRAIT = "cardBGStronk/bg_skill_grey.png";
    private static final String POWER_GREY_PORTRAIT = "cardBGStronk/bg_power_grey.png";
    private static final String ENERGY_ORB_GREY_PORTRAIT = "cardBGStronk/card_grey_orb.png";
    private static final String Yohane_Portrait = "charstuff/YohaneBG.png";
    private static final String Yohane_Button = "charstuff/YohaneButton.png";
    
	public YohaneMod() {
		//TODO Everything
		BaseMod.subscribe(this);
		BaseMod.addColor(AbstractCardEnum.GREY.toString(),
				GREY, GREY, GREY, GREY, GREY, GREY, GREY,
				ATTACK_GREY, SKILL_GREY, POWER_GREY, ENERGY_ORB_GREY, 
				ATTACK_GREY_PORTRAIT, SKILL_GREY_PORTRAIT, POWER_GREY_PORTRAIT,
				ENERGY_ORB_GREY_PORTRAIT);
	}

	public static void initialize() {
		YohaneMod mod = new YohaneMod();
	}
	
	 @Override
		public void receiveEditCharacters() {
			BaseMod.addCharacter(Yohane.class, "Yohane", "uwu",
					AbstractCardEnum.GREY.toString(), "Yohane",
					Yohane_Button , Yohane_Portrait,
					YohaneEnum.FallenAngel.toString());
		}
	
}
