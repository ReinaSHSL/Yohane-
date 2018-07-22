package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class FallenEnergy extends AbstractPower {
    public static final String POWER_ID = "FallenEnergy";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FallenEnergy(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.priority = 0;
        updateDescription();
        this.img = getDarkEnergyPowerTexture();
        this.canGoNegative = true;
        this.isTurnBased = false;
    }

    public void stackPower(int stackAmount)
    {
        //flash();
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    public static Texture getDarkEnergyPowerTexture() {
        return new Texture("powers/FallenEnergy.png");
    }
}
