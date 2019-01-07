package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.summons.Mari.Mari;
import yohanemod.summons.Mari.MariStrength;


public class Little_Demon_Mari extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Mari";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Mari.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Mari() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                player.addMinion(new Mari(-750F, true));
                AbstractMonster Mari0 = player.minions.getMonster(Mari.ID);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari0, p, new MariStrength(Mari0, 0), 0));
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals(Mari.ID)) {
                    //Upgrade
                    AbstractMonster Mari0Upgraded = player.minions.getMonster(Mari.ID);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari0Upgraded, p, new MariStrength(Mari0Upgraded, 1), 1));
                } else {
                    //No Upgrade
                    player.addMinion(new Mari(-1150F, false));
                    AbstractMonster Mari1 = player.minions.getMonster(Mari.ID);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari1, p, new MariStrength(Mari1, 0), 0));
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals(Mari.ID)) {
                    //Upgrade
                    AbstractMonster Mari0Upgraded = player.minions.getMonster(Mari.ID);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari0Upgraded, p, new MariStrength(Mari0Upgraded, 1), 1));
                } else if (player.minions.monsters.get(1).id.equals(Mari.ID)) {
                    //Upgrade
                    AbstractMonster Mari1Upgraded = player.minions.getMonster(Mari.ID);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari1Upgraded, p, new MariStrength(Mari1Upgraded, 1), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Mari();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
