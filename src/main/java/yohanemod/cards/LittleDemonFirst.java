package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LittleDemonFirst extends CustomCard {
    public static final String ID = "LittleDemonFirst";
    public static final String NAME = "Right Little Demon";

    public LittleDemonFirst() {
        super(ID, NAME, "cards/LittleDemonFirst.png", -2,
                "NONE", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF_AND_ENEMY);

    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new LittleDemonFirst();
    }

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}
