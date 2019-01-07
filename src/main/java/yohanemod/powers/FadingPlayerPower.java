package yohanemod.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class FadingPlayerPower extends AbstractPower {
    public static final String POWER_ID = "Yohane:FadingPlayerPower";
       private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
       public static final String NAME = powerStrings.NAME;
       public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
       public FadingPlayerPower(AbstractCreature owner, int turns) {
           this.name = NAME;
           this.ID = POWER_ID;
           this.owner = owner;
           this.amount = turns;
           updateDescription();
           loadRegion("fading");
           this.type = PowerType.DEBUFF;
       }
    
       public void updateDescription()
       {
             if (this.amount == 1) {
                   this.description = DESCRIPTIONS[2];
                 } else {
                   this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
                 }
           }
    
       public void onRemove()
       {
           AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.owner.hb.cX, this.owner.hb.cY), 0.1F));
           AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 999));
       }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(this.owner, this.owner, FadingPlayerPower.POWER_ID, 1));
        updateDescription();
    }
}
