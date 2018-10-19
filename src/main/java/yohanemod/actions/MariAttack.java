package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.summons.Lily.LilyNumbers;
import yohanemod.summons.Lily.LilyStrength;
import yohanemod.summons.Mari.MariNumbers;
import yohanemod.summons.Mari.MariStrength;

public class MariAttack extends AbstractGameAction {
    private AbstractFriendlyMonster owner;

    public MariAttack (AbstractFriendlyMonster owner) {
        this.duration = 0.8F;
        this.owner = owner;
    }

    @Override
    public void update() {
        if ((this.duration == 0.8F) && (AbstractDungeon.player != null)) {
            int upgradeCount = 0;
            if (this.owner.hasPower(MariStrength.POWER_ID) && this.owner.getPower(MariStrength.POWER_ID).amount != 0) {
                upgradeCount = this.owner.getPower(MariStrength.POWER_ID).amount;
            }
            int attackDamage = (MariNumbers.MariAttackDamage + (upgradeCount * 4));
            target = AbstractDungeon.getMonsters().getRandomMonster(true);
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped()) {
                    if (target == null) {
                        target = m;
                    } else if (m.currentHealth < target.currentHealth) {
                        target = m;
                    }
                }
            }
            if (target != null) {
                DamageInfo info = new DamageInfo(this.owner, attackDamage, DamageInfo.DamageType.NORMAL);
                info.applyPowers(this.owner, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }
            tickDuration();
        }
    }
}
