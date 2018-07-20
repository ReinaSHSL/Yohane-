package yohanemod.actions;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Livestream_Donation extends com.megacrit.cardcrawl.actions.AbstractGameAction {

    private int divideAmount;

    public Livestream_Donation (AbstractCreature target, int divideAmountNum) {
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.divideAmount = divideAmountNum;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.target, com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.drawPile.size() / this.divideAmount));
        }
        tickDuration();
    }
}
