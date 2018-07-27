package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class StrawberryTrapperPower extends AbstractPower {
    public static final String POWER_ID = "StrawberryTrapper";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int hpLoss;

    public StrawberryTrapperPower(AbstractCreature owner, int hpLoss) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.hpLoss = hpLoss;
        this.priority = 0;
        updateDescription();
        this.img = getStrawberryTrapperTexture();
        this.canGoNegative = true;
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.LoseHPAction(this.owner, this.owner, this.hpLoss, AbstractGameAction.AttackEffect.FIRE));
    }

    public void stackPower(int stackAmount)
    {
        //flash();
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.hpLoss += 40;
    }

    public void onShuffle() {

    }

    public void updateDescription()
        {
            this.description = (DESCRIPTIONS[0] + this.hpLoss + DESCRIPTIONS[1]);
        }


    public static Texture getStrawberryTrapperTexture() {
        return new Texture("powers/StrawberryTrapper.png");
    }
}
