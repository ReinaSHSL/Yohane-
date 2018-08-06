package yohanemod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class Prideful_Crash extends CustomCard {
    public static final String ID = "Yohane:Prideful_Crash";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "cards/Prideful_Crash.png";
    private static final int COST = 3;
    private static final int FALLEN_ENERGY = 20;
    private static final int FALLEN_ENERGY_UPGRADE = 10;
    private static final int DAMAGE_AMOUNT = 40;
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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            }
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p,
                    this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, this.block, false), this.block));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(new com.megacrit.cardcrawl.cards.curses.Pride(), 1));
        } else {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            }
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p,
                    this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, this.block, false), this.block));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(new com.megacrit.cardcrawl.cards.curses.Pride(), 2));
        }
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
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
