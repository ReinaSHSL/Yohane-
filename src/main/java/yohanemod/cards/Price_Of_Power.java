package yohanemod.cards;

import basemod.abstracts.CustomCard;
import basemod.interfaces.PostDrawSubscriber;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;


public class Price_Of_Power extends CustomCard implements PostDrawSubscriber {
    public static final String ID = "Yohane:Price_Of_Power";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Price_Of_Power.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int DAMAGE = 2;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Price_Of_Power() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                        rarity, target, POOL);
        this.retain = true;
        this.magicNumber = this.baseMagicNumber = DAMAGE;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.retain = true;
    }

    @Override
    public void receivePostDraw(AbstractCard c) {
            if (c.costForTurn != 0 && !c.isCostModified) {
                c.modifyCostForTurn(-1);
            }
    }

    @Override
    public void triggerWhenDrawn() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn != 0 && !c.isCostModified) {
                c.modifyCostForTurn(-1);
            }
        }
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
        for (AbstractCard ca : AbstractDungeon.player.hand.group) {
            if (ca.costForTurn != 0 && !ca.isCostModified) {
                ca.modifyCostForTurn(-1);
            }
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.costForTurn != 0 && c.isCostModified) {
                c.modifyCostForTurn(1);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Price_Of_Power();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}
