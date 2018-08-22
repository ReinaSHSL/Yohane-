package yohanemod.summons.Mari;

import actions.ChooseAction;
import actions.ChooseActionInfo;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import monsters.AbstractFriendlyMonster;
import yohanemod.powers.FallenEnergy;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Mari.MariChoiceCards;
import yohanemod.summons.Mari.MariNumbers;
import yohanemod.summons.Mari.MariStrength;

import java.util.ArrayList;

public class Mari extends AbstractFriendlyMonster {
    public static String NAME = "Mari";
    public static String ID = "Mari";
    private int upgradeCount;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public Mari(float offSetX) {
        super(NAME, ID, MariNumbers.MariHP,
                null, -2.0F, 10.0F, 230.0F, 240.0F, "summons/Mari.png", offSetX, 0);

    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, IntangiblePlayerPower.POWER_ID));
    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new MariChoiceCards(), target, "Choose your attack");
            this.moveInfo.forEach( move -> {
                pickAction.add(move.getName(), move.getDescription(), move.getAction());
            });
            AbstractDungeon.actionManager.addToBottom(pickAction);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    //Create possible moves for the monster
    private ArrayList<ChooseActionInfo> makeMoves(){
        if (this.hasPower(MariStrength.POWER_ID) && this.getPower(MariStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(MariStrength.POWER_ID).amount;
        }
        int attackDamage = (MariNumbers.MariAttackDamage + (upgradeCount * 4));
        int healthLoss = (MariNumbers.MariHealthLoss);
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        target = AbstractDungeon.getRandomMonster();
        String attackDesc = String.format("Deal %d damage to the lowest health enemy. Scales 4 times as fast with Evolve.", attackDamage);
        String evolveDesc = "Evolve this card.";
        String intangibleDesc = String.format("Mari gains 1 Intangible. NL Lose %d Max HP. NL Cannot be used under %d max HP. Does not scale with Evolve.", healthLoss, healthLoss);
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                if (target == null) {
                    target = m;
                } else if (m.currentHealth < target.currentHealth) {
                    target = m;
                }
            }
        }
        if ((target != null)) {
            tempInfo.add(new ChooseActionInfo("Attack", attackDesc, () -> {
                DamageInfo info = new DamageInfo(this,attackDamage,DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }));
        }
        tempInfo.add(new ChooseActionInfo("Evolve", evolveDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MariStrength(this, 1), 1));
        }));
        if (this.maxHealth > 5) {
            tempInfo.add(new ChooseActionInfo("Intangible", intangibleDesc, () -> {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 1), 1));
                this.decreaseMaxHealth(healthLoss);
            }));
        }
        return tempInfo;
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }

    @Override
    public void die() {
        AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
        if (player.minions.monsters.get(0) == this) {
            if (player.minions.monsters.size() == 2) {
                AbstractMonster moveToRightSummon = player.minions.monsters.get(1);
                moveToRightSummon.drawX = -1150F * Settings.scale;
            }
        }
        super.die();
    }
}
