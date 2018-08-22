package yohanemod.summons.Mari;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.summons.Mari.MariChoiceCards;

public class MariChoiceCards extends CustomCard {
    public static final String ID = "MariChoiceCards";
    public static final String NAME = "Mari Moves";

    public MariChoiceCards() {
        super(ID, NAME, "cards/MariChoiceCards.png", -2, "NONE", AbstractCard.CardType.ATTACK,
                AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF_AND_ENEMY);

    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new MariChoiceCards();
    }

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
