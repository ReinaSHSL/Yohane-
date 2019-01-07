package yohanemod.summons.Ruby;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import yohanemod.actions.RubyAttack;
import yohanemod.actions.RubyBlock;
import yohanemod.summons.AbstractYohaneMinion;
import yohanemod.tools.TextureLoader;

import java.util.ArrayList;

public class Ruby extends AbstractYohaneMinion {
    public static String NAME = "Ruby";
    public static String ID = "Ruby";
    private boolean hasAttacked = false;
    private AbstractMonster target;
    public int upgradeCount;
    public int[] multiDamage;
    public DamageInfo.DamageType damageTypeForTurn;
    public boolean isDamageModified = false;
    private static Texture intentOne = TextureLoader.getTexture("summons/intents/ruby/attack_intent_1.png");
    private static Texture intentTwo = TextureLoader.getTexture("summons/intents/ruby/attack_intent_2.png");
    private static Texture intentThree = TextureLoader.getTexture("summons/intents/ruby/attack_intent_3.png");
    private static Texture intentFour = TextureLoader.getTexture("summons/intents/ruby/attack_intent_4.png");
    private static Texture intentFive = TextureLoader.getTexture("summons/intents/ruby/attack_intent_5.png");
    private static Texture intentSix = TextureLoader.getTexture("summons/intents/ruby/attack_intent_6.png");
    private static Texture intentSeven = TextureLoader.getTexture("summons/intents/ruby/attack_intent_7.png");
    private static Texture[] intentImgs = {intentOne, intentTwo, intentThree, intentFour, intentFive, intentSix, intentSeven};

    public Ruby(float offsetX, boolean slotOne) {
        super(NAME, ID, RubyNumbers.rubyHP,
                15.0F, 10.0F, 230.0F, 240.0F, "summons/Ruby.png", offsetX, 0, intentImgs, slotOne);
        addMoves();
    }

    @Override
    public void applyStartOfTurnPowers() {
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(this, this, this.currentBlock));
    }

    private void addMoves() {
        ArrayList<MinionMove> rubyMoves = new ArrayList<>();
        if (this.hasPower(RubyStrength.POWER_ID) && this.getPower(RubyStrength.POWER_ID).amount != 0) {
            upgradeCount = this.getPower(RubyStrength.POWER_ID).amount;
        }
        int attackDamage = (RubyNumbers.rubyAttackDamage + upgradeCount);
        int blockAmount = (RubyNumbers.rubyBlockAmount + upgradeCount);
        String attackDesc = String.format("Deal %d damage to ALL enemies.", attackDamage);
        String blockDesc = String.format("Give %d Block to Yohane.", blockAmount);
        rubyMoves.add(new MinionMove("rubyPic", this, TextureLoader.getTexture("summons/bubbles/rubybubble.png")
                , "", () -> this.setTakenTurn(false)));
        rubyMoves.add(new MinionMove("Attack", this, new Texture("summons/bubbles/atk_bubble.png")
                , attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new RubyAttack(this));
        }));
        rubyMoves.add(new MinionMove("Block", this, new Texture("summons/bubbles/block_bubble.png")
                ,blockDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new RubyBlock(this));
        }));
        if (slotOne) {
            this.moves = new MinionMoveGroup(rubyMoves, 400F * Settings.scale, -200F * Settings.scale);
        } else {
            this.moves = new MinionMoveGroup(rubyMoves, 400F * Settings.scale, -300F * Settings.scale);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.hasAttacked = false;
    }

    @Override
    protected void getMove(int i) {
    }

}
