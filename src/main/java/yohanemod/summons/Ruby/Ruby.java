package yohanemod.summons.Ruby;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

import java.util.ArrayList;

public class Ruby extends AbstractFriendlyMonster {
    public static String NAME = "Ruby";
    public static String ID = "Ruby";
    private boolean hasAttacked = false;
    private AbstractMonster target;
    public int upgradeCount;
    public int[] multiDamage;
    public DamageInfo.DamageType damageTypeForTurn;
    public boolean isDamageModified = false;

    public Ruby(float offsetX) {
        super(NAME, ID, RubyNumbers.rubyHP,
                15.0F, 10.0F, 230.0F, 240.0F, "summons/Ruby.png", offsetX, 0);
        addMoves();
    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        System.out.println(this.name + " " + this.currentHealth);
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    public void addMoves() {
        if (this.hasPower(RubyStrength.POWER_ID) && this.getPower(RubyStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(RubyStrength.POWER_ID).amount;
        }
        int attackDamage = (RubyNumbers.rubyAttackDamage + upgradeCount);
        int blockAmount = (RubyNumbers.rubyBlockAmount + upgradeCount);
        String attackDesc = String.format("Deal %d damage to ALL enemies.", attackDamage);
        String blockDesc = String.format("Give %d Block to Yohane.", blockAmount);
        this.moves.addMove(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                DamageInfo info = new DamageInfo(this, attackDamage, DamageInfo.DamageType.NORMAL);
                info.applyPowers(mo, this);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, info));
            }
        }));
        this.moves.addMove(new MinionMove("Block", this, new Texture("summons/bubbles/block_bubble.png")
                ,blockDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockAmount));
        }));
    }

    @Override
    protected void getMove(int i) {
    }

}
