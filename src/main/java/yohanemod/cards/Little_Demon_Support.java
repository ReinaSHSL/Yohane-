package yohanemod.cards;

import basemod.abstracts.CustomCard;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;

public class Little_Demon_Support extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Support";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Support.png";
    private static final int BLOCK_AMT = 10;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Little_Demon_Support() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.block = this.baseBlock = BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            if (player.minions.monsters.size() != 0) {
                for (AbstractMonster mo : player.minions.monsters) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, p, this.block));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I have no Little Demons!", 1.0F, 2.0F));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Support();
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBlock(4);
    }
}

