package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.CurlUpPlayerPower;
import yohanemod.powers.FadingPlayerPower;
import yohanemod.powers.NoFallenLossFlightPower;
import yohanemod.powers.Sin;

public class Envious extends CustomCard {
    public static final String ID = "Yohane:Envious";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Envious.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.ENEMY;


    public Envious() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.YOHANE_GREY, rarity,
                target);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        try {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            for (AbstractPower powersToCopy : m.powers) {
                if (powersToCopy.ID.equals(FlightPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NoFallenLossFlightPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(IntangiblePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(FadingPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FadingPlayerPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(CurlUpPower.POWER_ID) || powersToCopy.ID.equals("conspire:Shedding")) {
                    if (powersToCopy.amount < 0) {
                        powersToCopy.amount = 8;
                    }
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CurlUpPlayerPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(ReactivePower.POWER_ID)) {
                    continue;
                }
                if (powersToCopy.ID.equals(ArtifactPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(Sin.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Sin(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(MalleablePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MalleablePower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(PlatedArmorPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(ShiftingPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShiftingPower(p), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(StrengthPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(SlowPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SlowPower(p, powersToCopy.amount), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(VulnerablePower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, powersToCopy.amount, false), powersToCopy.amount));
                    continue;
                }
                if (powersToCopy.ID.equals(WeakPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, powersToCopy.amount, false), powersToCopy.amount));
                    continue;
                }
                powersToCopy.owner = p;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, powersToCopy, powersToCopy.amount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Envious();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
