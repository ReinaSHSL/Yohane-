package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import yohanemod.YohaneMod;
import yohanemod.cards.LittleDemonFirst;
import yohanemod.cards.LittleDemonSecond;

import java.util.ArrayList;

public class LittleDemonChangeAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private boolean selected0;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> list = new ArrayList<>();
    private AbstractCard card;

    public LittleDemonChangeAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = p;
    }



    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
            if (player.minions.monsters.size() > 1) {
                AbstractCard c = new LittleDemonSecond();
                this.list.add(c);
                c = new LittleDemonFirst();
                this.list.add(c);
                YohaneMod.lds.open(this.list, null, "Pick Little Demon to change.");
                tickDuration();
                return;
            } else {
                AbstractDungeon.actionManager.addToBottom(new DemonSwapAction(this.p, true));
                this.isDone = true;
                return;
            }
        }
        AbstractCard firstDemon = new LittleDemonFirst();
        AbstractCard secondDemon = new LittleDemonSecond();
        if (YohaneMod.lds.selected0.cardID.equals(secondDemon.cardID)) {
            this.selected0 = false;
        }
        else if (YohaneMod.lds.selected0.cardID.equals(firstDemon.cardID)) {
            this.selected0 = true;
        }
        AbstractDungeon.actionManager.addToBottom(new DemonSwapAction(this.p, this.selected0));
        this.isDone = true;
    }
}