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

public class SchwarzSechsPower extends AbstractPower
{
    public static final String POWER_ID = "Yohane:SchwarzSechsPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Yohane:SchwarzSechsPower");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SchwarzSechsPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Yohane:SchwarzSechsPower";
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.img = getSchwarzSechsPowerTexture();
    }

    @Override
    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ((!card.purgeOnUse) && (card.cost == 0) && (this.amount > 0)) {
            flash();
            AbstractMonster m = null;

            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractCard tmp = card. makeSameInstanceOf();
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
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ModifyDamageAction(card.uuid, tmp.magicNumber));
            }
            this.amount -= 1;
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Yohane:SchwarzSechsPower"));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Yohane:SchwarzSechsPower"));
        }
    }

    private static Texture getSchwarzSechsPowerTexture() {
        return new Texture("powers/SchwarzSechsPower.png");
    }
}


