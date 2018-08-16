package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Gluttony extends CustomCard {
    public static final String ID = "Yohane:Gluttony";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Gluttony.png";
    private static final int FALLEN_ENERGY = 6;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Gluttony() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        int count = AbstractDungeon.player.hand.size();
        for (int i = 0; i < count; i++) {
             AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ExhaustAction(AbstractDungeon.player, AbstractDungeon.player,
                     1, true, true));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, 0), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Gluttony();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }

}
