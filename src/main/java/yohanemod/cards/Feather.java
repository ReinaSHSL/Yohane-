package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Feather extends CustomCard {
    public static final String ID = "Yohane:Feather";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Feather.png";
    private static final int COST = -2;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.CURSE;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.NONE;


    public Feather() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.CURSE,
                AbstractCard.CardColor.CURSE, rarity,
                target, POOL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic("Blue Candle")) {
            useBlueCandle(p);
            } else {
                for (AbstractCard c : p.hand.group) {
                    if (c.cardID != Feather.ID || !c.rawDescription.contains("Feather")) {
                        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ExhaustAction(AbstractDungeon.player, AbstractDungeon.player,
                                1, true, true));
                    }
                }
            }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
            this.dontTriggerOnUseCard = true;
            AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(this, true));
        }

    @Override
    public AbstractCard makeCopy() {
        return new Feather();
    }

    @Override
    public void upgrade() {

    }
}
