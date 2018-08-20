package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.actions.LittleDemonChangeAction;
import yohanemod.patches.AbstractCardEnum;


public class Little_Demon_Change extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Change";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Change.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Change() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LittleDemonChangeAction(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Change();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}
