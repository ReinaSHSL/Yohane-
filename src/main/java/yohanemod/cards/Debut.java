package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import yohanemod.powers.Sin;


public class Debut extends CustomCard {
    public static final String ID = "Yohane:Debut";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String IMG_PATH = "cards/Debut.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int FALLEN_ENERGY = 5;
    private static final int SIN = 7;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ALL_ENEMY;

    public Debut() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target);
        this.isInnate = true;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.misc = SIN;
    }

    public boolean hasEnoughEnergy() {
        boolean retVal = super.hasEnoughEnergy();
        if ((AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID) && AbstractDungeon.player.getPower(FallenEnergy.POWER_ID).amount < this.magicNumber)) {
            retVal = false;
        }
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FallenEnergy.POWER_ID) && p.getPower(FallenEnergy.POWER_ID).amount >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), -this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.animations.VFXAction(p, new
                    ShockWaveEffect(p.hb.cX, p.hb.cY, com.megacrit.cardcrawl.core.Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 1.5F));
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Sin(mo, this.misc), this.misc));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Debut();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.misc += 4;
        }
    }
}
