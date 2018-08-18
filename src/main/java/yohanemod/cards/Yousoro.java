package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.YousoroPower;


public class Yousoro extends CustomCard {
    public static final String ID = "Yohane:Yousoro";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Yousoro.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int DRAW = 2;
    private static final int ENERGY = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Yousoro() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = ENERGY;
        this.misc = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.misc));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new YousoroPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Yousoro();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.misc += 1;
        }
    }
}
