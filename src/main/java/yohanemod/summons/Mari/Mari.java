package yohanemod.summons.Mari;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import yohanemod.actions.MariAttack;
import yohanemod.summons.AbstractYohaneMinion;
import yohanemod.tools.TextureLoader;

import java.util.ArrayList;

public class Mari extends AbstractYohaneMinion {
    public static String NAME = "Mari";
    public static String ID = "Mari";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private String attackDesc;
    private String evolveDesc;
    private static Texture intentOne = TextureLoader.getTexture("summons/intents/mari/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("summons/intents/mari/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("summons/intents/mari/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("summons/intents/mari/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("summons/intents/mari/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("summons/intents/mari/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("summons/intents/mari/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};


    public Mari(float offSetX, boolean slotOne) {
        super(NAME, ID, MariNumbers.MariHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "summons/Mari.png", offSetX, 0, intentImgs, slotOne);
        addMoves();
    }

    @Override
    public void applyStartOfTurnPowers() {
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    public void addMoves() {
        ArrayList<MinionMove> mariMoves = new ArrayList<>();
        if (this.hasPower(MariStrength.POWER_ID) && this.getPower(MariStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(MariStrength.POWER_ID).amount;
        }
        int attackDamage = (MariNumbers.MariAttackDamage + (upgradeCount * 4));
        int healthLoss = (MariNumbers.MariHealthLoss);
        attackDesc = String.format("Deal %d damage to the lowest health enemy. Scales 4 times as fast with Evolve.", attackDamage);
        evolveDesc = "Evolve this card.";
        String intangibleDesc = String.format("Mari gains 1 Intangible. NL Lose %d Max HP. NL Cannot be used under %d max HP. Does not scale with Evolve.", healthLoss, healthLoss);
        mariMoves.add(new MinionMove("rubyPic", this, TextureLoader.getTexture("summons/bubbles/maribubble.png")
                , "", () -> this.setTakenTurn(false)));
        mariMoves.add(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new MariAttack(this));
        }));
        mariMoves.add(new MinionMove("Evolve", this, new Texture("summons/bubbles/evolve_bubble.png")
                , evolveDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MariStrength(this, 1), 1));
        }));
        mariMoves.add(new MinionMove("Intangible", this, new Texture("summons/bubbles/intangible_bubble.png")
                , intangibleDesc, () -> {
            if (this.currentHealth > healthLoss) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2), 1));
                this.decreaseMaxHealth(healthLoss);
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Mari is too damaged!", 1.0F, 2.0F));
            }
        }));
        this.moves = new MinionMoveGroup(mariMoves, 400F * Settings.scale, -300F * Settings.scale);
    }



    //Not needed unless doing some kind of random move like normal Monsters
    @Override
    protected void getMove(int i) {
    }
}
