package yohanemod.cards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.powers.FallenEnergy;
import yohanemod.powers.IceCreamSlow;
import yohanemod.patches.AbstractCardEnum;

public class Ice_Cream_Assault extends CustomCard{
    public static final String ID = "Ice_Cream_Assault";
    public static final String NAME = "Ice Cream Assault";
    public static final String DESCRIPTION = "Pay !M! Fallen Energy. NL Deal !D! damage. NL Apply Slow until the end of the turn.";
    public static final String IMG_PATH = "cards/Ice_Cream_Assault.png";
    private static final int DAMAGE_AMT = 3;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int FALLEN_ENERGY = 4;
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Ice_Cream_Assault() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("FallenEnergy") && p.getPower("FallenEnergy").amount >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new IceCreamSlow(m,0), 0));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ice_Cream_Assault();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(-1);
        }
    }
}
