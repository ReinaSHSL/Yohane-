package yohanemod.cards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class Allure_of_Darkness extends CustomCard{
    public static final String ID = "Yohane:Allure_of_Darkness";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Yohane:Allure_of_Darkness");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "cards/Allure_of_Darkness.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Allure_of_Darkness() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.GREY, rarity,
                target, POOL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded){
            if ((p.hasPower(FallenEnergy.POWER_ID)) && (p.getPower(FallenEnergy.POWER_ID).amount >= 2)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), -2));
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
                AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, false));
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 1, false));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Allure_of_Darkness();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
