package yohanemod.summons.Hanamaru;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HanamaruChoiceCards extends CustomCard {
    public static final String ID = "HanamaruChoiceCards";
    public static final String NAME = "Hanamaru Moves";

    public HanamaruChoiceCards() {
        super(ID, NAME, "cards/HanamaruChoiceCards.png", -2, "NONE", CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);

    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new HanamaruChoiceCards();
    }

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
