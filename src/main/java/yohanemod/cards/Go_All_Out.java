package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Go_All_Out extends CustomCard{
    public static final String ID = "Yohane:Go_All_Out";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Go_All_Out.png";
    private static final int FALLEN_ENERGY = 5;
    private static final int DRAW = 4;
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Go_All_Out() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.isInnate = false;
        this.exhaust = true;
        this.misc = DRAW;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.GoAllOutAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.misc));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Go_All_Out();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
        }
    }
}
