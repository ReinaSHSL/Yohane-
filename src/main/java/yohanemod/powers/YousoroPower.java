package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class YousoroPower extends AbstractPower {
    public static final String POWER_ID = "Yohane:YousoroPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public YousoroPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.priority = 1;
        updateDescription();
        this.img = getYousoroPowerTexture();
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    @Override
    public float modifyBlock(float blockAmount) {
        return blockAmount - blockAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, AbstractDungeon.player, YousoroPower.POWER_ID));
    }

    @Override
    public void stackPower(int stackAmount)
    {
        //flash();
        this.fontScale = 8.0F;
    }


    @Override
    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0]);
    }


    private static Texture getYousoroPowerTexture() {
        return new Texture("powers/YousoroPower.png");
    }
}
