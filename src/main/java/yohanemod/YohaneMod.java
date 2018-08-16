package yohanemod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yohanemod.cards.*;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.patches.F;
import yohanemod.patches.YohaneEnum;
import yohanemod.relics.AngelWings;

import java.nio.charset.StandardCharsets;



@SpireInitializer
public class YohaneMod implements EditCharactersSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber, EditStringsSubscriber  {
    public static final Logger logger = LogManager.getLogger(YohaneMod.class.getName());
	
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
        String cardStrings = Gdx.files.internal("localization/Yohane-Cardstrings-eng.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        }
	
	 @Override
		public void receiveEditCharacters() {
			BaseMod.addCharacter(Yohane.class,  "The Fallen Angel", "uwu",
					AbstractCardEnum.GREY.toString(), "Yohane",
					Yohane_Button , Yohane_Portrait,
					YohaneEnum.FallenAngel.toString());
		}

    @Override
    public void receiveEditKeywords() {
        logger.info("begin editing keywords");
        final String[] FallenEnergy = {"fallen"};
        BaseMod.addKeyword(FallenEnergy,"Used to pay for cards that require it. Cards which say pay means you must possess enough Fallen Energy. Cards which say lose can be played regardless of Fallen Energy count.");
        final String[] Summon = {"summon, summons"};
        BaseMod.addKeyword(Summon,"Summon an ally to help you in battle. There can only be maximum two summons out at a time. You cannot summon more than one of the same kind of ally.");
        final String[] Lily = {"lily"};
        BaseMod.addKeyword(Lily,"A Little Demon with 15 HP and can either deal 4 damage to a random enemy, or give you 8 Fallen Energy.");
        final String[] Ruby = {"ruby"};
        BaseMod.addKeyword(Ruby,"A Little Demon with 12 HP and can either deal 2 damage to all enemies, or give you 6 Block.");
        final String[] Evolves = {"evolves, evolve"};
        BaseMod.addKeyword(Evolves,"Strengthen a summon, dependant on the summon, and heal them for 5 HP.");
        final String[] Feather = {"feather, feathers"};
        BaseMod.addKeyword(Feather,"A Curse which exhausts your entire hand except for cards that say Feather at the end of the turn.");
		final String[] Sin = {"sin"};
		BaseMod.addKeyword(Sin,"Deal and take extra damage equal to the amount of Sin a monster has. Removed upon being hit.");
        final String[] Secret = {"secret"};
        BaseMod.addKeyword(Secret,"These cards are retained and have effects while in the hand. Discarded upon use.");
        logger.info("finish editing keywords");
    }

	 @Override
         public void receiveEditCards() {
             BaseMod.addDynamicVariable(new F());
             BaseMod.addCard(new Academic_Prowess());
             BaseMod.addCard(new Allure_of_Darkness());
             BaseMod.addCard(new Angel_Tears());
             BaseMod.addCard(new Awakening());
             BaseMod.addCard(new Backfoot());
             BaseMod.addCard(new Counter());
             BaseMod.addCard(new Cursed_Strike());
             BaseMod.addCard(new Dark_Rest());
             BaseMod.addCard(new Dark_Shield());
             BaseMod.addCard(new Debut());
             BaseMod.addCard(new Defend_Grey());
             BaseMod.addCard(new Descent());
             BaseMod.addCard(new Energy_Change());
             BaseMod.addCard(new Envious());
             BaseMod.addCard(new Excitement());
             BaseMod.addCard(new Fallen_Dragon_Phoenix_Hold());
             BaseMod.addCard(new Feather());
             BaseMod.addCard(new Feather_Curse());
             BaseMod.addCard(new Feather_Storm());
             BaseMod.addCard(new Feather_Tap());
             BaseMod.addCard(new Gluttony());
             BaseMod.addCard(new Go_All_Out());
             BaseMod.addCard(new Grand_Entrance());
             BaseMod.addCard(new Guilty_Kiss());
             BaseMod.addCard(new Hakodate());
             BaseMod.addCard(new Hell_Zone());
             BaseMod.addCard(new Ice_Cream_Assault());
             BaseMod.addCard(new Imagine_Breaker());
             BaseMod.addCard(new Impressive_Display());
             BaseMod.addCard(new Inspiration());
             BaseMod.addCard(new Introspection());
             BaseMod.addCard(new Koi_Ni_Naritai());
             BaseMod.addCard(new Kowareyasuki());
             BaseMod.addCard(new Little_Demon_Lily());
             BaseMod.addCard(new Little_Demon_Recruit());
             BaseMod.addCard(new Little_Demon_Ruby());
             BaseMod.addCard(new Little_Demon_Support());
             BaseMod.addCard(new Livestream_Donation());
             BaseMod.addCard(new Mijuku_Dreamer());
             BaseMod.addCard(new Miracle_Wave());
             BaseMod.addCard(new Misfortune());
             BaseMod.addCard(new Modore());
             BaseMod.addCard(new My_Mai_Tonight());
             BaseMod.addCard(new Nocturne());
             BaseMod.addCard(new One_Two());
             BaseMod.addCard(new Perfection());
             BaseMod.addCard(new Price_Of_Power());
             BaseMod.addCard(new Prideful_Crash());
             BaseMod.addCard(new Reckless_Greed());
             BaseMod.addCard(new Retaliation());
             BaseMod.addCard(new Runaway_Umbrella());
             BaseMod.addCard(new Schwarz_Sechs());
             BaseMod.addCard(new Shadow_Gate());
             BaseMod.addCard(new Silence());
             BaseMod.addCard(new Sloth());
             BaseMod.addCard(new Soul_Peek());
             BaseMod.addCard(new Strawberry_Trapper());
             BaseMod.addCard(new Strike_Grey());
             BaseMod.addCard(new Take_Flight());
             BaseMod.addCard(new Unstable_World());
             BaseMod.addCard(new Well_Laid_Ambush());
             BaseMod.addCard(new Wrath());
	 }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new AngelWings(), AbstractCardEnum.GREY.toString());
    }


}
