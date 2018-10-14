package yohanemod.summons.Lily;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import yohanemod.powers.FallenEnergy;

import java.util.ArrayList;


public class Lily extends AbstractFriendlyMonster {
    public static String NAME = "Lily";
    public static String ID = "Lily";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public Lily(float offSetX) {
        super(NAME, ID, LilyNumbers.lilyHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "summons/Lily.png", offSetX, 0);
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
        if (this.hasPower(LilyStrength.POWER_ID) && this.getPower(LilyStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(LilyStrength.POWER_ID).amount;
        }
        int attackDamage = (LilyNumbers.lilyAttackDamage + upgradeCount);
        int chargeAmount = (LilyNumbers.lilyChargeAmount + upgradeCount);
        String attackDesc = String.format("Deal %d damage to a random enemy.", attackDamage);
        String chargeDesc = String.format("Gain %d Fallen Energy.", chargeAmount);
        this.moves.addMove(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            DamageInfo info = new DamageInfo(this,attackDamage,DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target);
            target = AbstractDungeon.getRandomMonster();
            if (target != null) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }

        }));
        this.moves.addMove(new MinionMove("Charge", this, new Texture("summons/bubbles/charge_bubble.png")
                ,chargeDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FallenEnergy(AbstractDungeon.player, chargeAmount), chargeAmount));
        }));
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }
}
