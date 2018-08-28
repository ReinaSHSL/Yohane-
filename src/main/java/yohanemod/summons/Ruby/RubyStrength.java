package yohanemod.summons.Ruby;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.summons.Ruby.RubyNumbers;

public class RubyStrength extends AbstractPower {
    public static final String POWER_ID = "Yohane:RubyStrength";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RubyStrength(AbstractMonster m, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        updateDescription();
        this.img = getRubyStrengthTexture();
    }

    @Override
    public void updateDescription()
    {
        int damage = (RubyNumbers.rubyAttackDamage + this.amount);
        int block = (RubyNumbers.rubyBlockAmount + this.amount);
        this.description = (DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + block + DESCRIPTIONS[2]);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.owner.increaseMaxHp(3, true);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    private static Texture getRubyStrengthTexture() {
        return new Texture("powers/RubyStrength.png");
    }
}
