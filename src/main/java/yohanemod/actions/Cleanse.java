package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.powers.StrawberryTrapperPower;

public class Cleanse extends com.megacrit.cardcrawl.actions.AbstractGameAction {

    public Cleanse (AbstractCreature target, int turns) {
        this.target = target;
        this.amount = turns;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = 0.8F;
    }

    public void update() {
        if ((this.duration == 0.8F) && (this.target != null)) {
            for (AbstractPower po : this.target.powers) {
                if (po.type == AbstractPower.PowerType.DEBUFF) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.target, this.target, po.ID));
                }
            }
        }
        tickDuration();
    }
}
