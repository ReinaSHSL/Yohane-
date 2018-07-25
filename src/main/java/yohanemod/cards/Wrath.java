package yohanemod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import yohanemod.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.WeakPower;


public class Wrath extends CustomCard {
    public static final String ID = "Wrath";
    public static final String NAME = "Wrath";
    public static final String DESCRIPTION = "Deal !D! damage to ALL enemies 5 times. NL Apply !M! Weak to self.";
    public static final String IMG_PATH = "cards/Wrath.png";
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int WEAK_AMT = 2;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget target = CardTarget.ALL_ENEMY;

    public Wrath() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = WEAK_AMT;
        this.damage = this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int effect = 5;
        for (int i = 0; i < effect; i++) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p,
                    this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, this.magicNumber, false), this.magicNumber));
    }


    @Override
    public AbstractCard makeCopy() {
        return new Wrath();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
