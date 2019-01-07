package yohanemod.summons.Lily;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import yohanemod.actions.LilyAttack;
import yohanemod.actions.LilyCharge;
import yohanemod.powers.FallenEnergy;
import yohanemod.summons.AbstractYohaneMinion;
import yohanemod.tools.TextureLoader;

import java.util.ArrayList;


public class Lily extends AbstractYohaneMinion {
    public static String NAME = "Lily";
    public static String ID = "Lily";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private static Texture intentOne = TextureLoader.getTexture("summons/intents/lily/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("summons/intents/lily/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("summons/intents/lily/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("summons/intents/lily/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("summons/intents/lily/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("summons/intents/lily/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("summons/intents/lily/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};

    public Lily(float offSetX, boolean slotOne) {
        super(NAME, ID, LilyNumbers.lilyHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "summons/Lily.png", offSetX, 0, intentImgs, slotOne);
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
        ArrayList<MinionMove> lilyMoves = new ArrayList<>();
        if (this.hasPower(LilyStrength.POWER_ID) && this.getPower(LilyStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(LilyStrength.POWER_ID).amount;
        }
        int attackDamage = (LilyNumbers.lilyAttackDamage + upgradeCount);
        int chargeAmount = (LilyNumbers.lilyChargeAmount + upgradeCount);
        String attackDesc = String.format("Deal %d damage to a random enemy.", attackDamage);
        String chargeDesc = String.format("Gain %d Fallen Energy.", chargeAmount);
        lilyMoves.add(new MinionMove("rubyPic", this, TextureLoader.getTexture("summons/bubbles/lilybubble.png")
                , "", () -> this.setTakenTurn(false)));
        lilyMoves.add(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new LilyAttack(this));

        }));
        lilyMoves.add(new MinionMove("Charge", this, new Texture("summons/bubbles/charge_bubble.png")
                ,chargeDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new LilyCharge(this));
        }));
        if (slotOne) {
            this.moves = new MinionMoveGroup(lilyMoves, 350F * Settings.scale, -200F * Settings.scale);
        } else {
            this.moves = new MinionMoveGroup(lilyMoves, 350F * Settings.scale, -300F * Settings.scale);
        }
    }

    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }
}
