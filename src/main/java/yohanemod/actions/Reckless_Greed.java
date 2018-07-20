package yohanemod.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class Reckless_Greed extends com.megacrit.cardcrawl.actions.AbstractGameAction {

    private AbstractCard cardToMake;
    private boolean randomSpot;
    private boolean cardOffset;
    private boolean upgraded;

    public Reckless_Greed (AbstractCard card, int amount, boolean randomSpot, boolean cardOffset, boolean upgraded) {
        com.megacrit.cardcrawl.unlock.UnlockTracker.markCardAsSeen(card.cardID);
        setValues(this.target, this.source, amount);
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.cardOffset = cardOffset;
        this.upgraded = upgraded;
    }

    public void update () {
        if (!upgraded) {
            if (this.duration == 0.5F) {
                if (this.amount < 6) {
                    for (int i = 0; i < this.amount; i++) {
                        AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, this.randomSpot, this.cardOffset));
                    }
                } else {
                    for (int i = 0; i < this.amount; i++) {
                        AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, this.randomSpot));
                    }
                }
                this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            }
            tickDuration();
        } else {
            if (this.duration == 0.5F) {
                if (this.amount < 6) {
                    for (int i = 0; i < this.amount; i++) {
                        AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, this.randomSpot, this.cardOffset));
                    }
                } else {
                    for (int i = 0; i < this.amount; i++) {
                        AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, this.randomSpot));
                    }
                }
                this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            }
            tickDuration();
        }
    }
}
