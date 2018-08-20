package yohanemod.summons;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LilyChoiceCards extends CustomCard {
    public static final String ID = "LilyChoiceCards";
    public static final String NAME = "Lily Moves";

    public LilyChoiceCards() {
        super(ID, NAME, "cards/LilyChoiceCards.png", -2, "NONE", CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);

    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new LilyChoiceCards();
    }

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
