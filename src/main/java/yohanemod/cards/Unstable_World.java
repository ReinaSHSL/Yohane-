package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;


public class Unstable_World extends CustomCard{

    public static final String ID = "Yohane:Unstable_World";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/In_This_Unstable_World.png";
    private static final int COST = 0;
    private static final int ATTACK_DMG = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int FALLEN_ENERGY = 3;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ALL_ENEMY;


    public Unstable_World() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.YOHANE_GREY, rarity,
                target);

        this.damage = this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    public boolean hasEnoughEnergy() {
        boolean retVal = super.hasEnoughEnergy();
        if (!AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID)) {
            retVal = false;
        }
        if ((AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID) && AbstractDungeon.player.getPower(FallenEnergy.POWER_ID).amount < this.magicNumber)) {
            retVal = false;
        }
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FallenEnergy.POWER_ID) && (p.getPower(FallenEnergy.POWER_ID).amount >= this.magicNumber)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), -this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p,
                    this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(makeStatEquivalentCopy()));
            this.exhaust = true;
        }  else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Unstable_World();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
