package yohanemod;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import yohanemod.cards.*;
import yohanemod.relics.*;


@SpireInitializer
public class YohaneMod implements EditCharactersSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber{
	
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
        public void receiveEditStrings() {
            String relicStrings = Gdx.files.internal("localization/Yohane-RelicStrings-eng.json").readString(
                    String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
            String powerStrings = Gdx.files.internal("localization/Yohane-PowerStrings-eng.json").readString(
                    String.valueOf(StandardCharsets.UTF_8));
            BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        }
	
	 @Override
		public void receiveEditCharacters() {
			BaseMod.addCharacter(Yohane.class, "The Fallen Angel", "uwu",
					AbstractCardEnum.GREY.toString(), "Yohane",
					Yohane_Button , Yohane_Portrait,
					YohaneEnum.FallenAngel.toString());
		}

	 @Override
	 	public void receiveEditCards() {
		BaseMod.addCard(new Strike_Grey());
		BaseMod.addCard(new Defend_Grey());
		BaseMod.addCard(new Descent());
		BaseMod.addCard(new Well_Laid_Ambush());
		BaseMod.addCard(new Allure_of_Darkness());
		BaseMod.addCard(new Misfortune());
		BaseMod.addCard(new Unstable_World());
		BaseMod.addCard(new Perfection());
		BaseMod.addCard(new Dark_Rest());
		BaseMod.addCard(new Inspiration());
		BaseMod.addCard(new Fallen_Dragon_Phoenix_Hold());
		BaseMod.addCard(new Angel_Tears());
		BaseMod.addCard(new Strawberry_Trapper());
		BaseMod.addCard(new Energy_Change());
		BaseMod.addCard(new Livestream_Donation());
		BaseMod.addCard(new Shadow_Gate());
		BaseMod.addCard(new Reckless_Greed());
		BaseMod.addCard(new Prideful_Crash());
		BaseMod.addCard(new Sloth());
		BaseMod.addCard(new Take_Flight());
		BaseMod.addCard(new Backfoot());
		BaseMod.addCard(new Dark_Shield());
		BaseMod.addCard(new Imagine_Breaker());
		BaseMod.addCard(new Gluttony());
		BaseMod.addCard(new Wrath());
		BaseMod.addCard(new Kowareyasuki());
		BaseMod.addCard(new Nocturne());
		BaseMod.addCard(new Shark_Summon());
		BaseMod.addCard(new Ice_Cream_Assault());
		BaseMod.addCard(new Impressive_Display());
	 }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new AngelWings(), AbstractCardEnum.GREY.toString());
    }

}
