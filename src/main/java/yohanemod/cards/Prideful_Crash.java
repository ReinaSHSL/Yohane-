package yohanemod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Prideful_Crash extends CustomCard{
    public static final String ID = "Prideful_Crash";
    public static final String NAME = "Prideful Crash";
    public static final String DESCRIPTION = "Deal !D! damage. NL Gain !M! Fallen Energy. Apply !B! Vulnerable to self.";
    public static final String IMG_PATH = "cards/Prideful_Crash.png";
    private static final int COST = 3;
    private static final int FALLEN_ENERGY = 20;
    private static final int FALLEN_ENERGY_UPGRADE = 10;
    private static final int VULNERABLE_AMT = 3;
    private static final int VULNERABLE_AMT_UPGRADE = 1;
    private static final int DAMAGE_AMOUNT = 30;
    private static final int DAMAGE_AMOUNT_UPGRADE = 8;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ENEMY;

    public Prideful_Crash() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMOUNT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.block = this.baseBlock = VULNERABLE_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p,
                this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, this.block, false), this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Prideful_Crash();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DAMAGE_AMOUNT_UPGRADE);
            this.upgradeMagicNumber(FALLEN_ENERGY_UPGRADE);
            this.upgradeBlock(VULNERABLE_AMT_UPGRADE);
        }
    }

}
