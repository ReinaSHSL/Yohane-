package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.summons.Chika.ChikaNumbers;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Ruby.RubyNumbers;
import yohanemod.summons.Ruby.RubyStrength;

public class RubyAttack extends AbstractGameAction {
    private AbstractFriendlyMonster owner;

    public RubyAttack (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(RubyStrength.POWER_ID) && this.owner.getPower(RubyStrength.POWER_ID).amount != 0) {
                upgradeCount = this.owner.getPower(RubyStrength.POWER_ID).amount;
            }
            int attackDamage = RubyNumbers.rubyAttackDamage + upgradeCount;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                DamageInfo info = new DamageInfo(this.owner, attackDamage, DamageInfo.DamageType.NORMAL);
                info.applyPowers(mo, this.owner);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, info));
            }
            this.isDone = true;
        }
    }
}
