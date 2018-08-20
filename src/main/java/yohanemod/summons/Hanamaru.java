package yohanemod.summons;

import actions.ChooseAction;
import actions.ChooseActionInfo;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostPowerApplySubscriber;
import cards.MonsterCard;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import com.megacrit.cardcrawl.monsters.beyond.Darkling;
import com.megacrit.cardcrawl.powers.AbstractPower;
import monsters.AbstractFriendlyMonster;
import yohanemod.powers.FallenEnergy;
import yohanemod.powers.HanamaruStrength;
import yohanemod.powers.LilyStrength;
import yohanemod.powers.Sin;

import java.util.ArrayList;

public class Hanamaru extends AbstractFriendlyMonster implements PostPowerApplySubscriber {
    public static String NAME = "Hanamaru";
    public static String ID = "Yohane:Hanamaru";
    private int upgradeCount;
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
    private boolean canExhume = true;

    public Hanamaru(float offSetX) {
        super(NAME, ID, HanamaruNumbers.hanamaruHP,
                null, -2.0F, 10.0F, 230.0F, 240.0F, "summons/Hanamaru.png", offSetX, 0);

    }

    @Override
    public void takeTurn() {
        if(!hasAttacked){
            moveInfo = makeMoves();
            ChooseAction pickAction = new ChooseAction(new MonsterCard(), target, "Choose your attack");
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
        if (this.hasPower(HanamaruStrength.POWER_ID) && this.getPower(HanamaruStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(LilyStrength.POWER_ID).amount;
        }
        int sinAmount = (HanamaruNumbers.hanamaruSin + upgradeCount);
        int blockAmount = (HanamaruNumbers.hanamaruBlock + upgradeCount);
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        String sinDesc = String.format("Apply %d Sin to ALL enemies.", sinAmount);
        String blockDesc = String.format("Give %d Block to Yohane and ALL Summons.", blockAmount);
            tempInfo.add(new ChooseActionInfo("Sin", sinDesc, () -> {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new Sin(mo, sinAmount), sinAmount));
                }
            }));
        tempInfo.add(new ChooseActionInfo("Block", blockDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this, blockAmount));
            for (AbstractMonster mo : player.minions.monsters) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, p, blockAmount));
            }
        }));
        if (this.canExhume) {
            tempInfo.add(new ChooseActionInfo("Exhume", "Add one card from your Exhaust pile to your hand. NL You can only do this once until Evolved again.", () -> {
                AbstractDungeon.actionManager.addToBottom(new ExhumeAction(false));
            }));
            this.canExhume = false;
        }
        return tempInfo;
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {

    }

    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower.ID.equals(HanamaruStrength.POWER_ID)) {
            this.canExhume = true;
        }
    }
}
