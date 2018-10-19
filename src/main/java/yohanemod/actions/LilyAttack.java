package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.summons.Chika.ChikaNumbers;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Lily.LilyNumbers;
import yohanemod.summons.Lily.LilyStrength;

public class LilyAttack extends AbstractGameAction {
    private AbstractFriendlyMonster owner;

    public LilyAttack (AbstractFriendlyMonster owner) {
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
            int attackDamage = (LilyNumbers.lilyAttackDamage + upgradeCount);
            target = AbstractDungeon.getMonsters().getRandomMonster(true);
            DamageInfo info = new DamageInfo(this.owner, attackDamage,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this.owner, target);
            if (target != null) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }
            tickDuration();
        }
    }
}
