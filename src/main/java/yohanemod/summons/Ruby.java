package yohanemod.summons;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import monsters.AbstractFriendlyMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import actions.ChooseAction;
import actions.ChooseActionInfo;
import cards.MonsterCard;
import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;

public class Ruby extends AbstractFriendlyMonster {
    public static String NAME = "Ruby";
    public static String ID = "Ruby";
    private ArrayList<ChooseActionInfo> moveInfo;
    private boolean hasAttacked = false;
    private AbstractMonster target;
    public int upgradeCount;
    public int[] multiDamage;
    public DamageInfo.DamageType damageTypeForTurn;
    public boolean isDamageModified = false;

    public Ruby() {
        super(NAME, ID, 12,
                null, -8.0F, 10.0F, 230.0F, 240.0F, "summons/Ruby.png", -1150.0F, 0);
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

    private ArrayList<ChooseActionInfo> makeMoves(){
        ArrayList<ChooseActionInfo> tempInfo = new ArrayList<>();
        if (this.hasPower("RubyStrength") && this.getPower("RubyStrength").amount != 0) {
            upgradeCount = this.getPower("RubyStrength").amount;
        }
        float attackDamage = (2 + upgradeCount);
        int blockAmount = (6 + upgradeCount);
        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
        float[] tmp = new float[m.size()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = attackDamage;
        }
        for (int i = 0; i < tmp.length; i++) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                tmp[i] = p.atDamageGive(tmp[i], this.damageTypeForTurn);
                if (attackDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                tmp[i] = p.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
                if (attackDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] < 0.0F) {
                tmp[i] = 0.0F;
            }
        }
        multiDamage = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            this.multiDamage[i] = MathUtils.floor(tmp[i]);
        }
        attackDamage = multiDamage[0];
        String attackDesc = String.format("Deal %f to ALL enemies.", attackDamage);
        String blockDesc = String.format("Give %d Block to Yohane.", blockAmount);
        tempInfo.add(new ChooseActionInfo("Attack", attackDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(AbstractDungeon.player,
                    multiDamage, DamageInfo.DamageType.NORMAL, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        }));
        tempInfo.add(new ChooseActionInfo("Defend", blockDesc, () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockAmount));
        }));
        return tempInfo;
    }

    @Override
    protected void getMove(int i) {

    }
}
