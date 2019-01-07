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
import yohanemod.summons.AbstractYohaneMinion;
import yohanemod.summons.Chika.Chika;
import yohanemod.summons.Hanamaru.HanamaruStrength;
import yohanemod.summons.Hanamaru.Hanamaru;


public class Little_Demon_Hanamaru extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Hanamaru";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Hanamaru.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Hanamaru() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                player.addMinion(new Hanamaru(-750F, true));
                AbstractMonster Hanamaru0 = player.minions.monsters.get(0);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru0, p, new HanamaruStrength(Hanamaru0, 0), 0));
                Hanamaru.canExhume = true;
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals(Hanamaru.ID)) {
                    //Upgrade
                    AbstractMonster Hanamaru0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru0Upgraded, p, new HanamaruStrength(Hanamaru0Upgraded, 1), 1));
                } else {
                    AbstractYohaneMinion yohaneMinion = null;
                    AbstractMonster summonedMonster = player.minions.monsters.get(0);
                    if (summonedMonster instanceof AbstractYohaneMinion) {
                        yohaneMinion = (AbstractYohaneMinion)summonedMonster;
                    }
                    if (yohaneMinion != null && yohaneMinion.slotOne)  {
                        player.addMinion(new Hanamaru(-1150F, false));
                    } else {
                        player.addMinion(new Hanamaru(-750F, true));
                    }
                    Hanamaru.canExhume = true;
                    AbstractMonster Hanamaru1 = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru1, p, new HanamaruStrength(Hanamaru1, 0), 0));
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals(Hanamaru.ID)) {
                    //Upgrade
                    AbstractMonster Hanamaru0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru0Upgraded, p, new HanamaruStrength(Hanamaru0Upgraded, 1), 1));
                } else if (player.minions.monsters.get(1).id.equals(Hanamaru.ID)) {
                    //Upgrade
                    AbstractMonster Hanamaru1Upgraded = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru1Upgraded, p, new HanamaruStrength(Hanamaru1Upgraded, 1), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Hanamaru();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }
}
