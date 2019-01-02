package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class Envious extends CustomCard {
    public static final String ID = "Yohane:Envious";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Envious.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int POOL = 1;
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
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for (AbstractPower powersToCopy : m.powers) {
            if (powersToCopy.ID.equals(FlightPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new NoFallenLossFlightPower(p, powersToCopy.amount), powersToCopy.amount));
                return;
            }
            if (powersToCopy.ID.equals(IntangiblePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new IntangiblePlayerPower(p, powersToCopy.amount), powersToCopy.amount));
                return;
            }
            if (powersToCopy.ID.equals(FadingPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new FadingPlayerPower(p, powersToCopy.amount), powersToCopy.amount));
                return;
            }
            if (powersToCopy.ID.equals(CurlUpPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CurlUpPlayerPower(p, powersToCopy.amount), powersToCopy.amount));
                return;
            }
            powersToCopy.owner = p;
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, powersToCopy, powersToCopy.amount));
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
