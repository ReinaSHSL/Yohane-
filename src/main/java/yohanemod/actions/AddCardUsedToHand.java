package yohanemod.actions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AddCardUsedToHand {
    private AbstractCard targetCard;

    public AddCardUsedToHand (AbstractCard card) {
        this.targetCard = card;
        AbstractDungeon.player.discardPile.removeCard(targetCard);
        AbstractDungeon.player.hand.addToTop(targetCard);
        AbstractDungeon.player.hand.refreshHandLayout();
        return;
    }

}
