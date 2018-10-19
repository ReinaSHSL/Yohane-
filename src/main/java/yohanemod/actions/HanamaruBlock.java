package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.powers.Sin;
import yohanemod.summons.Hanamaru.HanamaruNumbers;
import yohanemod.summons.Hanamaru.HanamaruStrength;

public class HanamaruBlock extends AbstractGameAction {
    private AbstractFriendlyMonster owner;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;

    public HanamaruBlock (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(HanamaruStrength.POWER_ID) && this.owner.getPower(HanamaruStrength.POWER_ID).amount != 0) {
                upgradeCount = this.owner.getPower(HanamaruStrength.POWER_ID).amount;
            }
            int blockAmount = (HanamaruNumbers.hanamaruBlock + upgradeCount);
            for (AbstractMonster mo : player.minions.monsters) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, this.owner, blockAmount));
            }
            this.isDone = true;
        }
    }
}
