package yohanemod.powers;

 import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
 import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IceCreamSlow extends AbstractPower {
    public static final String POWER_ID = "IceCreamSlow";
       private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
       public static final String NAME = powerStrings.NAME;
       public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
       public IceCreamSlow(AbstractCreature owner, int amount) {
             this.name = NAME;
             this.ID = "IceCreamSlow";
             this.owner = owner;
             this.amount = amount;
             updateDescription();
             this.img = getIceCreamSlowTexture();
             this.type = AbstractPower.PowerType.DEBUFF;
           }
    
       public void atEndOfRound()
       {
             AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "IceCreamSlow"));
           }
    
       public void updateDescription()
       {
             this.description = (DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1]);
        
             if (this.amount != 0) {
                   this.description = (this.description + DESCRIPTIONS[2] + this.amount * 10 + DESCRIPTIONS[3]);
                 }
           }
    
       public void onAfterUseCard(AbstractCard card, UseCardAction action)
       {
             com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.owner,
                this.owner, new IceCreamSlow(this.owner, 1), 1));
           }
    
       public float atDamageReceive(float damage, DamageInfo.DamageType type)
       {
             if (type == DamageInfo.DamageType.NORMAL) {
                   return damage * (1.0F + this.amount * 0.1F);
                 }
             return damage;
           }

        public void stackPower(int stackAmount)
        {

            this.fontScale = 8.0F;
            this.amount += stackAmount;
        }

    public static Texture getIceCreamSlowTexture() {
        return new Texture("powers/IceCreamSlow.png");
    }
}
