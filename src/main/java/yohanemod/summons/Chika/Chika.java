package yohanemod.summons.Chika;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.actions.ChooseAction;
import kobting.friendlyminions.actions.ChooseActionInfo;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import java.util.ArrayList;

public class Chika extends AbstractFriendlyMonster {
    public static String NAME = "Chika";
    public static String ID = "Chika";
    private int upgradeCount;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;

    public Chika(float offSetX) {
        super(NAME, ID, ChikaNumbers.ChikaHP,
                null, -2.0F, 10.0F, 230.0F, 240.0F, "summons/Chika.png", offSetX, 0);

    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
        System.out.println(this.name + " " + this.currentHealth);
    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new ChikaChoiceCards(), target, "Choose your attack");
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


    private ArrayList<ChooseActionInfo> makeMoves(){
        if (this.hasPower(ChikaStrength.POWER_ID) && this.getPower(ChikaStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(ChikaStrength.POWER_ID).amount;
        }
        int attackDamage = (ChikaNumbers.ChikaAttackDamage + (upgradeCount * 2));
        int healAmount = (ChikaNumbers.ChikaHeal + upgradeCount);
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        String attackDesc = String.format("Deal %d damage to the lowest HP enemy. Scales twice as fast from Evolution.", attackDamage);
        String healDesc = String.format("Heal ALL Summons for %d Health.", healAmount);
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
        tempInfo.add(new ChooseActionInfo("Heal", healDesc, () -> {
            for (AbstractMonster mo : player.minions.monsters) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(mo, this, healAmount));
            }
        }));
        return tempInfo;
    }



    @Override
    protected void getMove(int i) {

    }

    @Override
    public void update() {
        System.out.println(this.drawX);
        AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) AbstractDungeon.player;
        if (player.hasMinions()) {
            if (this.drawX == -1150F * Settings.scale) {
                if (player.minions.monsters.size() == 1) {
                    AbstractMonster moveToRightSummon = player.minions.monsters.get(0);
                    moveToRightSummon.drawX = -750F * Settings.scale;
                }
            }
        }
    }
}
