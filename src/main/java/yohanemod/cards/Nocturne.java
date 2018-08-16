package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yohanemod.patches.AbstractCardEnum;

public class Nocturne extends CustomCard {
    public static final String ID = "Yohane:Lailapse";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Nocturne.png";
    private static final int COST = 2;
    private static final int VULNERABLE_AMT = 2;
    private static final int BLOCK_AMT = 9;
    private static final int BLOCK_UPGRADE = 4;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Nocturne() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = VULNERABLE_AMT;
        this.block = this.baseBlock = BLOCK_AMT;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Nocturne();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UPGRADE);
            this.upgradeMagicNumber(1);
        }
    }
}
