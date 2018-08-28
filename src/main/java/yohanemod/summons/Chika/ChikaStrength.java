package yohanemod.summons.Chika;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.summons.Chika.ChikaNumbers;

public class ChikaStrength extends AbstractPower {
    public static final String POWER_ID = "Yohane:ChikaStrength";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ChikaStrength(AbstractMonster m, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        updateDescription();
        this.img = getChikaStrengthTexture();
    }

    @Override
    public void updateDescription()
    {
        int damage = (ChikaNumbers.ChikaAttackDamage + (this.amount * 2));
        int charge = (ChikaNumbers.ChikaHeal + this.amount);
        this.description = (DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + charge + DESCRIPTIONS[2]);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.owner.increaseMaxHp(3, true);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    private static Texture getChikaStrengthTexture() {
        return new Texture("powers/ChikaStrength.png");
    }
}
