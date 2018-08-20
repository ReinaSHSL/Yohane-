package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LittleDemonSecond extends CustomCard {
    public static final String ID = "LittleDemonSecond";
    public static final String NAME = "Left Little Demon";

    public LittleDemonSecond() {
        super(ID, NAME, "cards/LittleDemonSecond.png", -2,
                "NONE", AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF_AND_ENEMY);

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
