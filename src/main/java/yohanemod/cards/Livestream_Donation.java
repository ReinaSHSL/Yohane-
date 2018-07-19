package yohanemod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Livestream_Donation extends CustomCard{
    public static final String ID = "Livestream_Donation";
    public static final String NAME = "Livestream Donation";
    public static final String DESCRIPTION = "Deal !D! Damage. NL Gain !M! Fallen Energy. NL Draw one card. NL Add a copy of this card to your deck.";
    public static final String IMG_PATH = "cards/Livestream_Donation.png";
    private static final int COST = 0;
    private static final int DAMAGE_AMOUNT = 5;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private static final int FALLEN_ENERGY = 4;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Livestream_Donation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMOUNT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
            AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.Livestream_Donation(new Livestream_Donation(), 1, true, false, false));
        } else {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
            AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.Livestream_Donation(new Livestream_Donation(), 1, true, false, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Livestream_Donation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.upgradeMagicNumber(2);
        }
    }

}
