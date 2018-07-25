package yohanemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Awakening extends CustomCard {
    public static final String ID = "Awakening";
    public static final String NAME = "Awakening";
    public static final String DESCRIPTION = "Pay !M! Fallen Energy. NL Deal !D! damage NL Remove all Debuffs from self.";
    public static final String IMG_PATH = "cards/Awakening.png";
    private static final int COST = 0;
    private static final int DAMAGE_AMT = 8;
    private static final int FALLEN_ENERGY = 6;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Awakening() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.
                DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        //todo
    }

    @Override
    public AbstractCard makeCopy() {
        return new Awakening();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(-4);
        }
    }
}
