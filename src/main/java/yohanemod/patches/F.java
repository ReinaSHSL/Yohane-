package yohanemod.patches;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class F extends DynamicVariable {
    @Override
    public String key()
    {
        return "F";
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return false;
    }

    @Override
    public int value(AbstractCard card)
    {
        return card.misc;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        return card.misc;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgraded;
    }
}
