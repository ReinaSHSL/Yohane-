package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;

public class Reckless_Greed extends CustomCard{
    public static final String ID = "Yohane:Reckless_Greed";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Reckless_Greed.png";
    private static final int DRAW = 2;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Reckless_Greed() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                rarity, target);
        this.misc = DRAW;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    if(!this.upgraded) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.misc));
        AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.Reckless_Greed(new Reckless_Greed(), this.misc, true, true, false));
    } else {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.misc));
        AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.Reckless_Greed(new Reckless_Greed(), this.misc, true, true, true));
    }


        }

    @Override
    public AbstractCard makeCopy() {
        return new Reckless_Greed();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.misc += 1;
        }
    }
}
