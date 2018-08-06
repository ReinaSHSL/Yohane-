package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.NoFallenLossFlightPower;

public class Envious extends CustomCard {
    public static final String ID = "Envious";
    public static final String NAME = "Envious";
    public static final String DESCRIPTION = "Deal !D! damage. NL Copy all enemy Buffs and Debuffs. NL Exhaust.";
    public static final String IMG_PATH = "cards/Envious.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.ENEMY;


    public Envious() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.GREY, rarity,
                target, POOL);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        for (AbstractPower powersToCopy : m.powers) {
            if (powersToCopy.ID == FlightPower.POWER_ID) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new NoFallenLossFlightPower(p, powersToCopy.amount), powersToCopy.amount));
            }
            if (powersToCopy.ID == IntangiblePower.POWER_ID) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new IntangiblePlayerPower(p, powersToCopy.amount), powersToCopy.amount));
            }
            if (powersToCopy.ID != FlightPower.POWER_ID && powersToCopy.ID != IntangiblePower.POWER_ID) {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, powersToCopy, powersToCopy.amount));
            }
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
