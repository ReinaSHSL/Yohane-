package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.powers.FallenEnergy;
import yohanemod.summons.Lily.LilyNumbers;
import yohanemod.summons.Lily.LilyStrength;

public class LilyCharge extends AbstractGameAction {
    private AbstractFriendlyMonster owner;

    public LilyCharge (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(LilyStrength.POWER_ID) && this.owner.getPower(LilyStrength.POWER_ID).amount != 0) {
                upgradeCount = this.owner.getPower(LilyStrength.POWER_ID).amount;
            }
            int chargeAmount = (LilyNumbers.lilyChargeAmount + upgradeCount);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FallenEnergy(AbstractDungeon.player, chargeAmount), chargeAmount));
            this.isDone = true;
        }
    }
}
