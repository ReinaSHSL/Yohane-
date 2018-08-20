package yohanemod.summons.Chika;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.summons.Chika.ChikaChoiceCards;

public class ChikaChoiceCards extends CustomCard {
    public static final String ID = "ChikaChoiceCards";
    public static final String NAME = "Chika Moves";

    public ChikaChoiceCards() {
        super(ID, NAME, "cards/ChikaChoiceCards.png", -2, "NONE", CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);

    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new ChikaChoiceCards();
    }

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
