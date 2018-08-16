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
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Energy_Change extends CustomCard {
    public static final String ID = "Yohane:Energy_Change";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Energy_Change.png";
    private static final int COST = 0;
    private static final int FALLEN_ENERGY = 4;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Energy_Change() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);

        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FallenEnergy.POWER_ID) && p.getPower(FallenEnergy.POWER_ID).amount >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), -this.magicNumber));
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(1));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Energy_Change();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-2);
        }
    }
}
