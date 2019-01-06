package yohanemod.summons.Chika;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import yohanemod.actions.ChikaAttack;
import yohanemod.actions.ChikaHeal;
import yohanemod.tools.TextureLoader;

import java.util.ArrayList;

public class Chika extends AbstractFriendlyMonster {
    public static String NAME = "Chika";
    public static String ID = "Chika";
    private int upgradeCount;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    private AbstractPlayer p = AbstractDungeon.player;
    private AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
    private static Texture intentOne = TextureLoader.getTexture("summons/intents/chika/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("summons/intents/chika/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("summons/intents/chika/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("summons/intents/chika/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("summons/intents/chika/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("summons/intents/chika/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("summons/intents/chika/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};

    public Chika(float offSetX) {
        super(NAME, ID, ChikaNumbers.ChikaHP,
                -2.0F, 10.0F, 230.0F, 240.0F, "summons/Chika.png", offSetX, 0, intentImgs);
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
        ArrayList<MinionMove> chikaMoves = new ArrayList<>();
        int attackDamage = (ChikaNumbers.ChikaAttackDamage);
        int healAmount = (ChikaNumbers.ChikaHeal);
        String attackDesc = String.format("Deal %d damage to the lowest HP enemy. Scales twice as fast from Evolution."
                , attackDamage);
        String healDesc = String.format("Heal ALL Summons for %d Health.", healAmount);
        chikaMoves.add(new MinionMove("rubyPic", this, TextureLoader.getTexture("summons/bubbles/chikabubble.png")
                , "", () -> this.setTakenTurn(false)));
        chikaMoves.add(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ChikaAttack(this));
        }));
        chikaMoves.add(new MinionMove("Heal", this, new Texture("summons/bubbles/heal_bubble.png")
                ,healDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new ChikaHeal(this));
        }));
        this.moves = new MinionMoveGroup(chikaMoves, 400F * Settings.scale, -300F * Settings.scale);
    }

    @Override
    protected void getMove(int i) {

    }


}
