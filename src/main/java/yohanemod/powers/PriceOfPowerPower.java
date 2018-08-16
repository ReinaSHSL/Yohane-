package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.Yohane;
import yohanemod.cards.Price_Of_Power;

import java.util.ArrayList;

public class PriceOfPowerPower extends AbstractPower {
    public static final String POWER_ID = "Yohane:PriceOfPowerPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private ArrayList<AbstractCard> affectedCards = new ArrayList<>();
    private ArrayList<AbstractCard> toRestoreCost = new ArrayList<>();

    public PriceOfPowerPower(AbstractPlayer p, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = p;
        this.amount = amount;
        updateDescription();
        this.img = getPriceOfPowerTexture();
    }

    //Below From https://github.com/JohnnyDevo/The-Mystic-Project/blob/master/src/main/java/MysticMod/Powers/SpontaneousCasterPower.java

    @Override
    public void onInitialApplication() {
        for (final AbstractCard handCard : AbstractDungeon.player.hand.group) {
            if (!this.affectedCards.contains(handCard) && handCard.costForTurn != 0) {
                handCard.modifyCostForTurn(-1);
                this.affectedCards.add(handCard);
            }
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        for (final AbstractCard handCard : AbstractDungeon.player.hand.group) {
            if (!this.affectedCards.contains(handCard) && handCard.costForTurn != 0) {
                handCard.modifyCostForTurn(-1);
                this.affectedCards.add(handCard);
            }
        }
    }

    @Override
    public void duringTurn() {
        for (final AbstractCard handCard : AbstractDungeon.player.hand.group) {
            if (!this.affectedCards.contains(handCard) && handCard.costForTurn != 0) {
                handCard.modifyCostForTurn(-1);
                this.affectedCards.add(handCard);
            } else if (this.affectedCards.contains(handCard)) {
                this.toRestoreCost.add(handCard);
            }
        }
        if (this.toRestoreCost.size() > 0) {
            for (AbstractCard cardToRestore : this.toRestoreCost) {
                if (cardToRestore.costForTurn == 0) {
                    cardToRestore.costForTurn++;
                }
                else {
                    cardToRestore.modifyCostForTurn(1);
                }
                this.affectedCards.remove(cardToRestore);
            }
            this.toRestoreCost.clear();
        }
    }

    @Override
    public void onDrawOrDiscard() {
        for (final AbstractCard handCard : AbstractDungeon.player.hand.group) {
            if (!this.affectedCards.contains(handCard) && handCard.costForTurn != 0) {
                handCard.modifyCostForTurn(-1);
                this.affectedCards.add(handCard);
            } else if (this.affectedCards.contains(handCard)) {
                this.toRestoreCost.add(handCard);
            }
        }
        if (this.toRestoreCost.size() > 0) {
            for (AbstractCard cardToRestore : this.toRestoreCost) {
                if (cardToRestore.costForTurn == 0) {
                    cardToRestore.costForTurn++;
                }
                else {
                    cardToRestore.modifyCostForTurn(1);
                }
                this.affectedCards.remove(cardToRestore);
            }
            this.toRestoreCost.clear();
        }
    }

    @Override
    public void onUseCard (AbstractCard c, UseCardAction action) {
        if (!c.cardID.equals("Yohane:Price_Of_Power")) {
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
        }

    }

    public void onRemove() {
        for (AbstractCard cardToRestore : AbstractDungeon.player.hand.group) {
            if (cardToRestore.cost != 0) {
                cardToRestore.costForTurn++;
                cardToRestore.isCostModifiedForTurn = false;
            }
            else {
                cardToRestore.modifyCostForTurn(1);
                cardToRestore.isCostModifiedForTurn = false;
            }
            this.affectedCards.remove(cardToRestore);
        }
        this.toRestoreCost.clear();
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
    }

    private static Texture getPriceOfPowerTexture() {
        return new Texture("powers/PriceOfPower.png");
    }
}
