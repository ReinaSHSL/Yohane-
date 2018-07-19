package yohanemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Angel_Tears extends CustomCard{
    public static final String ID = "Angel_Tears";
    public static final String NAME = "Angel Tears";
    public static final String DESCRIPTION = "Deal !D! Damage. NL Apply !M! Weak. NL Gain 8 Fallen Energy.";
    public static final String IMG_PATH = "cards/Angel_Tears.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int WEAK_AMT = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.BASIC;
    private static final CardTarget target = CardTarget.ENEMY;
    public Angel_Tears() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.GREY, rarity,
                target, POOL);

        this.damage = this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = WEAK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.
                DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(m,
                p, new com.megacrit.cardcrawl.powers.WeakPower(m, this.magicNumber, false), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 8), 8));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Angel_Tears();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(1);
        }
    }
}
