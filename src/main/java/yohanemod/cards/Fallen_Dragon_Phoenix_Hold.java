package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;

public class Fallen_Dragon_Phoenix_Hold extends CustomCard {
    public static final String ID = "Fallen_Dragon_Phoenix_Hold";
    public static final String NAME = "Phoenix Hold";
    public static final String DESCRIPTION = "Enemy loses !M! Strength for the rest of the turn. NL Exhaust.";
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
