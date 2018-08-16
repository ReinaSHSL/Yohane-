package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;


public class Academic_Prowess extends CustomCard {
    public static final String ID = "Yohane:Academic_Prowess";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/Academic_Prowess.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;
    private static final int ENERGY = 1;
    private static final int CARDS_PLAYED = 4;

    public Academic_Prowess() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = ENERGY;
        this.misc = CARDS_PLAYED;
        this.retain = true;
    }

    public boolean canPlay(AbstractCard card) {
           if (AbstractDungeon.player.cardsPlayedThisTurn >= this.misc) {
               card.cantUseMessage = EXTENDED_DESCRIPTION[0];
               return false;
           }
           return true;
       }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        this.retain = true;
        if (AbstractDungeon.player.cardsPlayedThisTurn == 0) {
            this.rawDescription = (EXTENDED_DESCRIPTION[1] + this.misc + EXTENDED_DESCRIPTION[2]);
        } else if (AbstractDungeon.player.cardsPlayedThisTurn == 1) {
            this.rawDescription = (EXTENDED_DESCRIPTION[1] + this.misc + EXTENDED_DESCRIPTION[3] +
                    AbstractDungeon.player.cardsPlayedThisTurn + EXTENDED_DESCRIPTION[4]);
        }
        else {
            this.rawDescription = (EXTENDED_DESCRIPTION[1] + this.misc + EXTENDED_DESCRIPTION[3] +
                    AbstractDungeon.player.cardsPlayedThisTurn + EXTENDED_DESCRIPTION[5]);
        }
        initializeDescription();
    }

    @Override
    public void atTurnStart() {
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public AbstractCard makeCopy() {
        return new Academic_Prowess();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.misc += 1;
        }
    }
}
