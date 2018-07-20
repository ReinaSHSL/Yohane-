package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ImagineBreakerPower extends AbstractPower{
    public static final String POWER_ID = "ImagineBreakerPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ImagineBreakerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.priority = 0;
        updateDescription();
        this.img = getImagineBreakerTexture();
    }

    public void atEndOfTurn(boolean isPlayer) {
        int FallenLoss = this.owner.getPower("FallenEnergy").amount;
        if (this.owner.getPower("FallenEnergy").amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new FallenEnergy(owner, -FallenLoss), -FallenLoss));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, FallenLoss));
        }
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0]);
    }

    public static Texture getImagineBreakerTexture() {
        return new Texture("powers/ImagineBreaker.png");
    }
}
