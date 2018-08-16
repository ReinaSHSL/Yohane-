package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ShadowGatePower extends AbstractPower{

        public static final String POWER_ID = "Yohane:ShadowGate";
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        public static final String NAME = powerStrings.NAME;
        public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        private static int lossAmount = 0;

        public ShadowGatePower(AbstractCreature owner, int amount, int lossAmount) {
            this.name = NAME;
            this.ID = POWER_ID;
            this.owner = owner;
            this.amount = amount;
            updateDescription();
            this.img = getShadowGateTexture();
            this.lossAmount = lossAmount;

        }

        public void onUseCard(AbstractCard card, UseCardAction action) {
            final int FallenLoss = this.amount * this.lossAmount;
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new FallenEnergy(owner, -FallenLoss), -FallenLoss));
            if (this.owner.getPower(FallenEnergy.POWER_ID).amount - FallenLoss <= 0) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
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

        public static Texture getShadowGateTexture() {
            return new Texture("powers/ShadowGate.png");
        }

}
