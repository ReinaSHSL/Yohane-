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

public class Fallen_Dragon_Phoenix_Hold extends CustomCard {
    public static final String ID = "Yohane:Fallen_Dragon_Phoenix_Hold";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Fallen_Dragon_Phoenix_Hold.png";
    private static final int COST = 3;
    private static final int STRENGTH_LOSS = 99;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.ENEMY;

    public Fallen_Dragon_Phoenix_Hold() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = STRENGTH_LOSS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new com.megacrit.cardcrawl.powers.StrengthPower(m, -this.magicNumber), -this.magicNumber));
        if ((m != null) && (!m.hasPower("Artifact"))) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new com.megacrit.cardcrawl.powers.GainStrengthPower(m, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Fallen_Dragon_Phoenix_Hold();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }
}
