package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.summons.Chika.ChikaNumbers;
import yohanemod.summons.Chika.ChikaStrength;

public class ChikaHeal extends AbstractGameAction {
    private AbstractFriendlyMonster owner;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;

    public ChikaHeal (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(ChikaStrength.POWER_ID) && this.owner.getPower(ChikaStrength.POWER_ID).amount != 0) {
                upgradeCount = this.owner.getPower(ChikaStrength.POWER_ID).amount;
            }
            int healAmount = (ChikaNumbers.ChikaHeal + upgradeCount);
            for (AbstractMonster mo : player.minions.monsters) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(mo, this.owner, healAmount));
            }
            this.isDone = true;
        }
    }
}
