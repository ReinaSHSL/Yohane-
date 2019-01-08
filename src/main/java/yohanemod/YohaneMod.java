package yohanemod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yohanemod.cards.*;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.patches.F;
import yohanemod.patches.YohaneEnum;
import yohanemod.powers.FallenEnergy;
import yohanemod.relics.AngelWings;
import yohanemod.screens.LittleDemonScreen;
import yohanemod.summons.Chika.ChikaChoiceCards;
import yohanemod.summons.Chika.ChikaNumbers;
import yohanemod.summons.Hanamaru.HanamaruChoiceCards;
import yohanemod.summons.Hanamaru.HanamaruNumbers;
import yohanemod.summons.Lily.LilyChoiceCards;
import yohanemod.summons.Lily.LilyNumbers;
import yohanemod.summons.Mari.MariChoiceCards;
import yohanemod.summons.Mari.MariNumbers;
import yohanemod.summons.Ruby.RubyChoiceCards;
import yohanemod.summons.Ruby.RubyNumbers;
import yohanemod.tools.TextureLoader;

import java.nio.charset.StandardCharsets;


@SpireInitializer
public class YohaneMod implements
        EditCharactersSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber,
        RenderSubscriber {
    public static final Logger logger = LogManager.getLogger(YohaneMod.class.getName());

    private static final String MODNAME = "Yohane!";
    private static final String AUTHOR = "Reina";
    private static final String DESCRIPTION = "Adds Yohane as a playable character.";

    private static final float BUTTON_ENABLE_X = 350.0f;
    private static final float BUTTON_ENABLE_Y = 750.0f;
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
    public static boolean optOutMetrics = false;
    public static LittleDemonScreen lds;
    private static FallenEnergyCounter fallenEnergyCounter;

    public YohaneMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(AbstractCardEnum.YOHANE_GREY,
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
            switch (Settings.language) {
                case ZHS:
                    BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/Yohane-RelicStrings-zh.json");
                    BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/Yohane-PowerStrings-zh.json");
                    BaseMod.loadCustomStringsFile(CardStrings.class, "localization/Yohane-Cardstrings-zh.json");
                    BaseMod.loadCustomStringsFile(UIStrings.class, "localization/Yohane-UIStrings-zh.json");
                    break;
                default:
                    BaseMod.loadCustomStringsFile(RelicStrings.class, "localization/Yohane-RelicStrings-eng.json");
                    BaseMod.loadCustomStringsFile(PowerStrings.class, "localization/Yohane-PowerStrings-eng.json");
                    BaseMod.loadCustomStringsFile(CardStrings.class, "localization/Yohane-Cardstrings-eng.json");
                    BaseMod.loadCustomStringsFile(UIStrings.class, "localization/Yohane-UIStrings-eng.json");
            }
        }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Yohane("Yohane"),
                Yohane_Button,
                Yohane_Portrait,
                YohaneEnum.FallenAngel);
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("begin editing keywords");
        switch (Settings.language) {
            case ZHS:
                final String[] FallenEnergyZH = {"堕天之力"};
                BaseMod.addKeyword(FallenEnergyZH,"某些卡牌需要它才能被打出。 每回合获得2点。");
                final String[] SummonZH = {"小恶魔", "小恶魔"};
                BaseMod.addKeyword(SummonZH,"小恶魔可以在战斗中帮助你。 你最多只能召唤两名小恶魔，不会有复数同名小恶魔出现在战场上。");
                final String[] LilyZH = {"梨梨"};
                String lilyDescZH = String.format("拥有 %d 点生命值，可以对随机敌人造成 %d 点伤害，或者给予你 %d 点堕天之力。",
                        LilyNumbers.lilyHP, LilyNumbers.lilyAttackDamage, LilyNumbers.lilyChargeAmount);
                BaseMod.addKeyword(LilyZH, lilyDescZH);
                final String[] RubyZH = {"露比"};
                String rubyDescZH = String.format("拥有 %d 点生命值，可以对所有敌人造成 %d 点伤害，或者给予你 %d 点格挡。",
                        RubyNumbers.rubyHP, RubyNumbers.rubyAttackDamage, RubyNumbers.rubyBlockAmount);
                BaseMod.addKeyword(RubyZH, rubyDescZH);
                final String[] EvolvesZH = {"进化", "进化"};
                BaseMod.addKeyword(EvolvesZH,"无特别说明的情况下，除生命值外的其他各项数值提升1点，生命值提升3点。");
                final String[] SinZH = {"罪印"};
                BaseMod.addKeyword(SinZH,"攻击和被攻击时额外造成相当于罪印层数的伤害，被命中后移除。");
                final String[] SecretZH = {"奥秘"};
                BaseMod.addKeyword(SecretZH,"卡牌会被保留在手中并获得某些效果。打出后失去此效果。");
                final String[] HanamaruZH = {"花丸"};
                String hanamaruDescZH = String.format("拥有 %d 点生命值，可以对所有敌人施加 %d 层罪印，或者给予所有友方单位 %d 点格挡， 并且仅有一次机会发掘一张已消耗的卡牌。",
                        HanamaruNumbers.hanamaruHP, HanamaruNumbers.hanamaruSin, HanamaruNumbers.hanamaruBlock);
                BaseMod.addKeyword(HanamaruZH, hanamaruDescZH);
                final String[] ChikaZH = {"千歌"};
                String chikaDescZH = String.format("拥有 %d 点生命值，可以对生命值最低的敌人造成 %d点伤害，或者恢复所有小恶魔 %d 点生命。进化会额外提升 2 点攻击力。",
                        ChikaNumbers.ChikaHP, ChikaNumbers.ChikaAttackDamage, ChikaNumbers.ChikaHeal);
                BaseMod.addKeyword(ChikaZH, chikaDescZH);
                final String[] MariZH = {"鞠莉"};
                String mariDescZH = String.format("拥有 %d 点生命值， 可以对生命值最低的敌人造成 %d 点伤害，自我进化，或消耗 %d 点生命值并获得 2 层无实体。进化会提升鞠莉6点攻击力。",
                        MariNumbers.MariHP, MariNumbers.MariAttackDamage, MariNumbers.MariHealthLoss);
                BaseMod.addKeyword(MariZH, mariDescZH);
                final String[] DodgeZH = {"闪避"};
                BaseMod.addKeyword(DodgeZH,"你受到的下一次伤害变为0点。");
                break;
            default:
                final String[] FallenEnergy = {"fallen"};
                BaseMod.addKeyword(FallenEnergy,"Used to pay for cards that require it. Gain 2 per turn.");
                final String[] Summon = {"summon", "summons"};
                BaseMod.addKeyword(Summon,"Summon an ally to help you in battle. There can only be maximum two summons out at a time. You cannot summon more than one of the same kind of Summon.");
                final String[] Lily = {"lily"};
                String lilyDesc = String.format("A Little Demon with %d HP and can either deal %d damage to a random enemy, or give you %d Fallen Energy.",
                        LilyNumbers.lilyHP, LilyNumbers.lilyAttackDamage, LilyNumbers.lilyChargeAmount);
                BaseMod.addKeyword(Lily, lilyDesc);
                final String[] Ruby = {"ruby"};
                String rubyDesc = String.format("A Little Demon with %d HP and can either deal %d damage to all enemies, or give you %d Block.",
                        RubyNumbers.rubyHP, RubyNumbers.rubyAttackDamage, RubyNumbers.rubyBlockAmount);
                BaseMod.addKeyword(Ruby, rubyDesc);
                final String[] Evolves = {"evolves", "evolve"};
                BaseMod.addKeyword(Evolves,"Everything done by the Summon gets more effective by 1 and raise max HP by 3.");
                final String[] Sin = {"sin"};
                BaseMod.addKeyword(Sin,"Deal and take extra damage equal to the amount of Sin a monster has. Removed upon being hit.");
                final String[] Secret = {"secret"};
                BaseMod.addKeyword(Secret,"These cards are retained and have effects while in the hand. Can be played for no effect.");
                final String[] Hanamaru = {"hanamaru"};
                String hanamaruDesc = String.format("A Little Demon with %d HP and can apply %d Sin to ALL enemies, give %d Block to ALL allies and herself, or Exhume one card one time only..",
                        HanamaruNumbers.hanamaruHP, HanamaruNumbers.hanamaruSin, HanamaruNumbers.hanamaruBlock);
                BaseMod.addKeyword(Hanamaru, hanamaruDesc);
                String[] Chika = {"chika"};
                String chikaDesc = String.format("A Little Demon with %d HP and can deal %d damage to the lowest HP enemy or heal all Summons for %d HP. Evolving makes Chika's attack do 2 more damage.",
                        ChikaNumbers.ChikaHP, ChikaNumbers.ChikaAttackDamage, ChikaNumbers.ChikaHeal);
                BaseMod.addKeyword(Chika, chikaDesc);
                final String[] Mari = {"mari"};
                String mariDesc = String.format("A Little Demon with %d HP and can deal %d damage to the lowest HP enemy, Evolve or gain 2 Intangible in exchange for %d HP. Evolving increases Mari's attack damage by 6.",
                        MariNumbers.MariHP, MariNumbers.MariAttackDamage, MariNumbers.MariHealthLoss);
                BaseMod.addKeyword(Mari, mariDesc);
                final String[] Dodge = {"dodge"};
                BaseMod.addKeyword(Dodge,"Next attack you take deals 0 damage.");
        }
        logger.info("finish editing keywords");
    }

	 @Override
         public void receiveEditCards() {
             BaseMod.addDynamicVariable(new F());
             BaseMod.addCard(new Academic_Prowess());
             BaseMod.addCard(new Adept_Technology());
             BaseMod.addCard(new Accelerator());
             BaseMod.addCard(new Allure_of_Darkness());
             BaseMod.addCard(new Angel_Tears());
             BaseMod.addCard(new Awaken_The_Power());
             BaseMod.addCard(new Backfoot());
             BaseMod.addCard(new Blazing());
             BaseMod.addCard(new City_Of_Sin());
             BaseMod.addCard(new Culinary_Genius());
             BaseMod.addCard(new Curse_The_Heavens());
             BaseMod.addCard(new Cursed_Strike());
             BaseMod.addCard(new Dark_Rest());
             BaseMod.addCard(new Dark_Shield());
             BaseMod.addCard(new Debut());
             BaseMod.addCard(new Defend_Grey());
             BaseMod.addCard(new Descent());
             BaseMod.addCard(new Energetic_Performance());
             BaseMod.addCard(new Energy_Change());
             BaseMod.addCard(new Envious());
             BaseMod.addCard(new Excitement());
             BaseMod.addCard(new Fallen_Dragon_Phoenix_Hold());
             BaseMod.addCard(new Gluttony());
             BaseMod.addCard(new Go_All_Out());
             BaseMod.addCard(new Grace_Under_Fire());
             BaseMod.addCard(new Grand_Entrance());
             BaseMod.addCard(new Guilty_Kiss());
             BaseMod.addCard(new Hakodate());
             BaseMod.addCard(new Hell_Zone());
             BaseMod.addCard(new Ice_Cream_Assault());
             BaseMod.addCard(new Imagine_Breaker());
             BaseMod.addCard(new Impressive_Display());
             BaseMod.addCard(new Indulge());
             BaseMod.addCard(new Inspiration());
             BaseMod.addCard(new Introspection());
             BaseMod.addCard(new Kowareyasuki());
             BaseMod.addCard(new Lailapse());
             //BaseMod.addCard(new Little_Demon_Change());
             BaseMod.addCard(new Little_Demon_Chika());
             BaseMod.addCard(new Little_Demon_Hanamaru());
             BaseMod.addCard(new Little_Demon_Mari());
             BaseMod.addCard(new Little_Demon_Lily());
             BaseMod.addCard(new Little_Demon_Recruit());
             BaseMod.addCard(new Little_Demon_Ruby());
             BaseMod.addCard(new Little_Demon_Support());
             BaseMod.addCard(new Livestream_Donation());
             BaseMod.addCard(new Lust());
             BaseMod.addCard(new Mijuku_Dreamer());
             BaseMod.addCard(new Miracle_Wave());
             BaseMod.addCard(new Misfortune());
             BaseMod.addCard(new Modore());
             BaseMod.addCard(new My_Mai_Tonight());
             BaseMod.addCard(new One_Two());
             BaseMod.addCard(new Perfection());
             //BaseMod.addCard(new Price_Of_Power());
             BaseMod.addCard(new Prideful_Crash());
             BaseMod.addCard(new Reckless_Greed());
             BaseMod.addCard(new Retaliation());
             BaseMod.addCard(new Runaway_Umbrella());
             BaseMod.addCard(new Schwarz_Sechs());
             BaseMod.addCard(new Scorn());
             BaseMod.addCard(new Shadow_Gate());
             BaseMod.addCard(new Sloth());
             BaseMod.addCard(new Snow_Halation());
             BaseMod.addCard(new Soul_Peek());
             BaseMod.addCard(new Stealth_Mode());
             BaseMod.addCard(new Strawberry_Trapper());
             BaseMod.addCard(new Strike_Grey());
             BaseMod.addCard(new Take_Flight());
             BaseMod.addCard(new Unstable_World());
             BaseMod.addCard(new Voodoo_Doll());
             BaseMod.addCard(new Well_Laid_Ambush());
             BaseMod.addCard(new Witch_Trick());
             BaseMod.addCard(new Wrath());
             BaseMod.addCard(new Yousoro());

             BaseMod.addCard(new ChikaChoiceCards());
             BaseMod.addCard(new HanamaruChoiceCards());
             BaseMod.addCard(new LilyChoiceCards());
             BaseMod.addCard(new RubyChoiceCards());
             BaseMod.addCard(new MariChoiceCards());
             BaseMod.addCard(new LittleDemonFirst());
             BaseMod.addCard(new LittleDemonSecond());
             BaseMod.addCard(new AccelerateAttack());
             BaseMod.addCard(new AccelerateDodge());
	 }

    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(Gdx.files.internal("badge/YohaneModBadge.png"));

        ModPanel settingsPanel = new ModPanel();

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        fallenEnergyCounter = new FallenEnergyCounter(TextureLoader.getTexture("FallenEnergyCounter.png"));
    }


    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new AngelWings(), AbstractCardEnum.YOHANE_GREY);
    }


    @Override
    public void receiveRender(SpriteBatch sb) {
        if (AbstractDungeon.player != null && CardCrawlGame.dungeon != null && AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID) && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (!AbstractDungeon.isScreenUp) {
                fallenEnergyCounter.render(sb);
                fallenEnergyCounter.update();
            }
        }
    }
}
