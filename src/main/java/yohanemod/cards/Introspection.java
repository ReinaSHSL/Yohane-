package yohanemod.cards;


import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;


public class Introspection extends CustomCard {
    public static final String ID = "Introspection";
    public static final String NAME = "Introspection";
    public static final String DESCRIPTION = "Gain !B! Block. NL Remove all debuffs. NL Exhaust all Curses.";
    public static final String IMG_PATH = "cards/Introspection.png";
    private static final int BLOCK_AMOUNT = 10;
    private static final int UPGRADE_BLOCK_DMG = 4;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Introspection() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.block = this.baseBlock = BLOCK_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.Cleanse(p, 99));
        for (AbstractCard c : p.drawPile.group) {
            if (c.type == CardType.CURSE) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.drawPile));
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.CURSE) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.discardPile));
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.CURSE) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Introspection();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_DMG);
        }
    }
}
