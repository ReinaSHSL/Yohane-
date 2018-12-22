package yohanemod.summons.Hanamaru;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.ExhumeAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

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
        AbstractFriendlyMonster hanamaru = (AbstractFriendlyMonster)this.owner;
        if (!Hanamaru.canExhume) {
            hanamaru.addMove(new MinionMove("Exhume", hanamaru, new Texture("summons/bubbles/exhume_bubble.png")
                    , "Add one card from your Exhaust pile to your hand. NL You can only do this once until Evolved again.", () -> {
                AbstractDungeon.actionManager.addToBottom(new ExhumeAction(false));
                Hanamaru.canExhume = false;
                hanamaru.removeMove("Exhume");
            }));
        }
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        Hanamaru.canExhume = true;
    }

    private static Texture getHanamaruStrengthTexture() {
        return new Texture("powers/HanamaruStrength.png");
    }
}

