package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;

public class LittleDemonSecond extends CustomCard {
    public static final String ID = "Yohane:LittleDemonSecond";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/LittleDemonSecond.png";
    private static final int COST = -2;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget target = CardTarget.SELF_AND_ENEMY;

    public LittleDemonSecond() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                rarity, target, POOL);

    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new LittleDemonSecond();
    }

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
