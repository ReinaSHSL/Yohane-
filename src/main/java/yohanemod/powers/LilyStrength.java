package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.summons.LilyNumbers;

public class LilyStrength extends AbstractPower{
    public static final String POWER_ID = "Yohane:LilyStrength";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LilyStrength(AbstractPlayer p, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = p;
        this.amount = amount;
        updateDescription();
        this.img = getLilyStrengthTexture();
    }

    @Override
    public void updateDescription()
    {
        int damage = (LilyNumbers.lilyAttackDamage + this.amount);
        int charge = (LilyNumbers.lilyChargeAmount + this.amount);
        this.description = (DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + charge + DESCRIPTIONS[2]);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    private static Texture getLilyStrengthTexture() {
        return new Texture("powers/LilyStrength.png");
    }
}

