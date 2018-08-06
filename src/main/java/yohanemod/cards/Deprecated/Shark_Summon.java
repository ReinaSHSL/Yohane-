package yohanemod.cards.Deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.ThornsPower;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Shark_Summon extends CustomCard {
    public static final String ID = "Yohane:Shark_Summon";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Shark_Summon.png";
    private static final int DAMAGE_AMT = 12;
    private static final int DAMAGE_UPGRADE = 4;
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Shark_Summon() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.block = this.baseBlock = DAMAGE_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (p.hasPower(FallenEnergy.POWER_ID) && p.getPower(FallenEnergy.POWER_ID).amount >= 4) {
            int ThornsAmount = p.getPower(FallenEnergy.POWER_ID).amount / 4;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -ThornsAmount), -ThornsAmount));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, ThornsAmount), ThornsAmount));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Shark_Summon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(DAMAGE_UPGRADE);
        }
    }

}
