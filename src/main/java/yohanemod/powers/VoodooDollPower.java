package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VoodooDollPower extends AbstractPower {
    public static final String POWER_ID = "Yohane:VoodooDollPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public VoodooDollPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.img = getVoodooDollPowerTexture();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((!card.purgeOnUse) && (this.amount > 0)  && ((card.type == AbstractCard.CardType.ATTACK) || (card.type == AbstractCard.CardType.SKILL))) {
               flash();
               AbstractMonster m = null;
               if (action.target != null) {
                   m = (AbstractMonster)action.target;
               }
               int tempDamage = card.damage/2;
               int tempMagic = card.magicNumber/2;
               int tempMisc = card.misc/2;
               AbstractCard tmp = card.makeStatEquivalentCopy();
               tmp.baseDamage = tempDamage;
               tmp.baseMagicNumber = tempMagic;
               tmp.misc = tempMisc;
               AbstractDungeon.player.limbo.addToBottom(tmp);
               tmp.current_x = card.current_x;
               tmp.current_y = card.current_y;
               tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
               tmp.target_y = (Settings.HEIGHT / 2.0F);
               tmp.freeToPlayOnce = true;
               if (m != null) {
                   tmp.calculateCardDamage(m);
               }
               tmp.purgeOnUse = true;
               AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmp, m, card.energyOnUse));
               if (tmp.cardID.equals("Rampage")) {
                   AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ModifyDamageAction(card, tmp.magicNumber));
               }
               this.amount -= 1;
               if (this.amount == 0) {
                   AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
               }
         }
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    private static Texture getVoodooDollPowerTexture() {
        return new Texture("powers/VoodooDollPower.png");
    }
}
