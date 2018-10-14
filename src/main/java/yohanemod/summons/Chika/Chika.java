package yohanemod.summons.Chika;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

import java.util.ArrayList;

public class Chika extends AbstractFriendlyMonster {
    public static String NAME = "Chika";
    public static String ID = "Chika";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;

    public Chika(float offSetX) {
        super(NAME, ID, ChikaNumbers.ChikaHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "summons/Chika.png", offSetX, 0);
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
        if (this.hasPower(ChikaStrength.POWER_ID) && this.getPower(ChikaStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(ChikaStrength.POWER_ID).amount;
        }
        int attackDamage = (ChikaNumbers.ChikaAttackDamage + (upgradeCount * 2));
        int healAmount = (ChikaNumbers.ChikaHeal + upgradeCount);
        DamageInfo info = new DamageInfo(this,attackDamage,DamageInfo.DamageType.NORMAL);
        info.applyPowers(this, target);
        String attackDesc = String.format("Deal %d damage to the lowest HP enemy. Scales twice as fast from Evolution."
                , info.base);
        String healDesc = String.format("Heal ALL Summons for %d Health.", healAmount);
        this.moves.addMove(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            target = AbstractDungeon.getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
        }));
        this.moves.addMove(new MinionMove("Heal", this, new Texture("summons/bubbles/heal_bubble.png")
                ,healDesc, () -> {
            for (AbstractMonster mo : player.minions.monsters) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(mo, this, healAmount));
            }
        }));
    }

    @Override
    protected void getMove(int i) {

    }


}
