package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.summons.Chika.ChikaNumbers;
import yohanemod.summons.Chika.ChikaStrength;

public class ChikaAttack extends AbstractGameAction {
    private AbstractFriendlyMonster owner;

    public ChikaAttack (AbstractFriendlyMonster owner) {
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
            int attackDamage = (ChikaNumbers.ChikaAttackDamage + (upgradeCount * 2));
            target = AbstractDungeon.getMonsters().getRandomMonster(true);
            DamageInfo info = new DamageInfo(this.owner, attackDamage,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this.owner, target);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            tickDuration();
        }
    }
}
