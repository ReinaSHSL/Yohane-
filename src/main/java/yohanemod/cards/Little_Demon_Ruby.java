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
import yohanemod.summons.Ruby.Ruby;
import yohanemod.summons.Ruby.RubyStrength;

public class Little_Demon_Ruby extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Ruby";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Ruby.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.COMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.SELF;

    public Little_Demon_Ruby() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                rarity, target);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);

    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Ruby();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                player.addMinion(new Ruby(-750F, true));
                AbstractMonster Ruby0 = player.minions.monsters.get(0);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby0, abstractPlayer, new RubyStrength(Ruby0, 0), 0));
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals(Ruby.ID)) {
                    //Upgrade
                    AbstractMonster ruby0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ruby0Upgraded, abstractPlayer, new RubyStrength(ruby0Upgraded, 1), 1));
                } else {
                    //No Upgrade
                    player.addMinion(new Ruby(-1150F, false));
                    AbstractMonster Ruby1 = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby1, abstractPlayer, new RubyStrength(Ruby1, 0), 0));
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals(Ruby.ID)) {
                    //Upgrade
                    AbstractMonster ruby0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ruby0Upgraded, abstractPlayer, new RubyStrength(ruby0Upgraded, 1), 1));
                } else if (player.minions.monsters.get(1).id.equals(Ruby.ID)) {
                    //Upgrade
                    AbstractMonster ruby1Upgraded = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(ruby1Upgraded, abstractPlayer, new RubyStrength(ruby1Upgraded, 1), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }
}
