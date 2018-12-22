package yohanemod.powers;

import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CurlUpPlayerPower extends AbstractPower {
    public static final String POWER_ID = "Yohane:CurlUp";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private boolean triggered = false;

    public CurlUpPlayerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Curl Up";
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        this.loadRegion("closeUp");
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (!this.triggered && damageAmount < this.owner.currentHealth && damageAmount > 0 && info.owner != null && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            this.triggered = true;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction((AbstractMonster)this.owner, "CLOSED"));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Curl Up"));
        }

        return damageAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Curl Up");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
