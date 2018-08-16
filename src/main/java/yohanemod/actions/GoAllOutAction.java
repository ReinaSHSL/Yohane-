package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import yohanemod.powers.FallenEnergy;

public class GoAllOutAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private int magicNumber = 0;
    private AbstractPlayer p;

    public GoAllOutAction (int magicNumber) {
        this.p = AbstractDungeon.player;
        this.magicNumber = magicNumber;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = 0.8F;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (this.p != null)) {
            int FallenGain = 0;
            for (AbstractCard c : this.p.drawPile.group) {
                if (c.type != AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, this.p.drawPile));
                    FallenGain++;
                }
            }
            for (AbstractCard c : this.p.discardPile.group) {
                if (c.type != AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, this.p.discardPile));
                    FallenGain++;
                }
            }
            for (AbstractCard c : this.p.hand.group) {
                if (c.type != AbstractCard.CardType.ATTACK) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, this.p.hand));
                    FallenGain++;
                }
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new FallenEnergy(this.p, FallenGain * this.magicNumber), FallenGain * this.magicNumber));
        }
        tickDuration();
    }
}
