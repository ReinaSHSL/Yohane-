package yohanemod.summons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import monsters.AbstractFriendlyMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import actions.ChooseAction;
import actions.ChooseActionInfo;
import cards.MonsterCard;
import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.core.Settings;


public class Lily extends AbstractFriendlyMonster {
    public static String NAME = "Lily";
    public static String ID = "Lily";
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;

    public Lily() {
        super(NAME, ID, 15,
                null, -8.0F, 10.0F, 230.0F, 240.0F, "summons/Lily.png", -700F * Settings.scale, 0);
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
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();

        target = AbstractDungeon.getRandomMonster();

        tempInfo.add(new ChooseActionInfo("Attack", "Deal 4 damage to a random enemy.", () -> {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                    new DamageInfo(this, 4, DamageInfo.DamageType.NORMAL)));
        }));
        tempInfo.add(new ChooseActionInfo("Charge", "Gain 8 Fallen Energy", () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FallenEnergy(AbstractDungeon.player, 8), 8));
        }));

        return tempInfo;
    }


    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {

    }
}
