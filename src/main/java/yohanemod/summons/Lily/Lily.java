package yohanemod.summons.Lily;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import kobting.friendlyminions.actions.ChooseAction;
import kobting.friendlyminions.actions.ChooseActionInfo;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.powers.FallenEnergy;

import java.util.ArrayList;


public class Lily extends AbstractFriendlyMonster {
    public static String NAME = "Lily";
    public static String ID = "Lily";
    private int upgradeCount;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public Lily(float offSetX) {
        super(NAME, ID, LilyNumbers.lilyHP,
                null, -2.0F, 10.0F, 230.0F, 240.0F, "summons/Lily.png", offSetX, 0);

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
            ChooseAction pickAction = new ChooseAction(new LilyChoiceCards(), target, "Choose your attack");
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
        if (this.hasPower(LilyStrength.POWER_ID) && this.getPower(LilyStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(LilyStrength.POWER_ID).amount;
        }
        int attackDamage = (LilyNumbers.lilyAttackDamage + upgradeCount);
        int chargeAmount = (LilyNumbers.lilyChargeAmount + upgradeCount);
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        target = AbstractDungeon.getRandomMonster();
        String attackDesc = String.format("Deal %d damage to a random enemy.", attackDamage);
        String chargeDesc = String.format("Gain %d Fallen Energy.", chargeAmount);
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.id.equals(AwakenedOne.ID) || mo.id.equals(Darkling.ID)) {
                target = mo;
            }
        }
        if ((target != null)) {
            tempInfo.add(new ChooseActionInfo("Attack", attackDesc, () -> {
                DamageInfo info = new DamageInfo(this,attackDamage,DamageInfo.DamageType.NORMAL);
                info.applyPowers(this, target);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            }));
        }
        tempInfo.add(new ChooseActionInfo("Charge", chargeDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FallenEnergy(AbstractDungeon.player, chargeAmount), chargeAmount));
        }));
        return tempInfo;
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }
}
