package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import basemod.abstracts.CustomCard;
import yohanemod.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Backfoot extends CustomCard{
    public static final String ID = "Backfoot";
    public static final String NAME = "Backfoot";
    public static final String DESCRIPTION = "Gain !B! Block. NL Lose !M! Fallen Energy.";
    public static final String IMG_PATH = "cards/Defend_Grey.png";
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 10;
    private static final int UPGRADE_BLOCK_DMG = 4;
    private static final int FALLEN_ENERGY = 10;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Backfoot() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Backfoot();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_DMG);
            this.upgradeMagicNumber(-4);
        }
    }
}
