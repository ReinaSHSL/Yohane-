package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.YohaneMod;
import yohanemod.cards.AccelerateAttack;
import yohanemod.cards.AccelerateDodge;
import yohanemod.powers.DodgePower;

import java.util.ArrayList;

public class AcceleratorAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;
    private boolean attack;
    private boolean dodge;
    private int damage;
    private int vuln;
    private ArrayList<AbstractCard> list = new ArrayList<>();

    public AcceleratorAction(AbstractPlayer p, AbstractMonster m, int damage, int vuln) {
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.vuln = vuln;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractCard c = new AccelerateAttack();
            this.list.add(c);
            c = new AccelerateDodge();
            this.list.add(c);

            YohaneMod.lds.open(this.list, null, "Choose");
            tickDuration();
            return;
        }
        AbstractCard attackPicked = new AccelerateAttack();
        AbstractCard dodgePicked = new AccelerateDodge();
        if (YohaneMod.lds.selected0.cardID.equals(attackPicked.cardID)) {
            this.attack = true;
        }
        else if (YohaneMod.lds.selected0.cardID.equals(dodgePicked.cardID)) {
            this.dodge = true;
        }
        if (this.attack) {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.
                    DamageAction(m, new DamageInfo(this.p, this.damage, DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.isDone = true;
            return;
        }
        if (this.dodge) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new DodgePower(this.p, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.p,
                    this.p, new com.megacrit.cardcrawl.powers.VulnerablePower(this.p, this.vuln, false), this.vuln));
            this.isDone = true;
        }
    }
}
