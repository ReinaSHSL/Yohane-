package yohanemod.summons.Mari;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.summons.Mari.MariNumbers;

public class MariStrength extends AbstractPower {
    public static final String POWER_ID = "Yohane:MariStrength";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MariStrength(AbstractMonster m, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        updateDescription();
        this.img = getMariStrengthTexture();
    }

    @Override
    public void updateDescription()
    {
        int damage = (MariNumbers.MariAttackDamage + (this.amount * 4));
        int charge = (MariNumbers.MariHealthLoss);
        this.description = (DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + charge + DESCRIPTIONS[2]);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.owner.increaseMaxHp(3, true);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    private static Texture getMariStrengthTexture() {
        return new Texture("powers/MariStrength.png");
    }
}
