package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import monsters.AbstractFriendlyMonster;
import yohanemod.summons.Hanamaru;
import yohanemod.summons.HanamaruNumbers;
import yohanemod.summons.LilyNumbers;

public class HanamaruStrength extends AbstractPower {
    public static final String POWER_ID = "Yohane:HanamaruStrength";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HanamaruStrength(AbstractMonster m, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        updateDescription();
        this.img = getHanamaruStrengthTexture();
    }

    @Override
    public void onInitialApplication() {
        Hanamaru.canExhume = true;
        this.owner.increaseMaxHp(3, true);
    }

    @Override
    public void updateDescription()
    {
        int sinAmount = (HanamaruNumbers.hanamaruSin + this.amount);
        int blockAmount = (HanamaruNumbers.hanamaruBlock + this.amount);
        this.description = (DESCRIPTIONS[0] + sinAmount + DESCRIPTIONS[1] + blockAmount + DESCRIPTIONS[2]);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.owner.increaseMaxHp(3, true);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        Hanamaru.canExhume = true;
    }

    private static Texture getHanamaruStrengthTexture() {
        return new Texture("powers/HanamaruStrength.png");
    }
}

