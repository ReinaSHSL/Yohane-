package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.StrawberryTrapperPower;

public class Strawberry_Trapper extends CustomCard{
    public static final String ID = "Yohane:Strawberry_Trapper";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "cards/Strawberry_Trapper.png";
    private static final int STR_AMOUNT = 10;
    private static final int DRAW = 1;
    private static final int DRAW_UPGRADE = 1;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Strawberry_Trapper() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = STR_AMOUNT;
        this.misc = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.misc));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrawberryTrapperPower(p,40)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Strawberry_Trapper();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(5);
            this.misc += DRAW_UPGRADE;
            this.rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }

}
