package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.powers.Sin;
import yohanemod.summons.Chika.ChikaNumbers;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Hanamaru.HanamaruNumbers;
import yohanemod.summons.Hanamaru.HanamaruStrength;

public class HanamaruSin extends AbstractGameAction {
    private AbstractFriendlyMonster owner;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;

    public HanamaruSin (AbstractFriendlyMonster owner) {
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
            int sinAmount = (HanamaruNumbers.hanamaruSin + upgradeCount);
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new Sin(mo, sinAmount), sinAmount));
                }
            }
            tickDuration();
        }
    }
}
