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
import yohanemod.summons.Lily.Lily;
import yohanemod.summons.Lily.LilyStrength;

public class Little_Demon_Lily extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Lily";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Lily.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Lily() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                rarity, target);
    }

    @Override
    public void upgrade() {
        this.upgradeName();
        this.upgradeBaseCost(0);

    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Lily();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                player.addMinion(new Lily(-750F, true));
                AbstractMonster Lily0 = player.minions.monsters.get(0);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily0, abstractPlayer, new LilyStrength(Lily0, 0), 0));
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals(Lily.ID)) {
                    //Upgrade
                    AbstractMonster lily0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily0Upgraded, abstractPlayer, new LilyStrength(lily0Upgraded, 1), 1));
                } else {
                    //No Upgrade
                    AbstractYohaneMinion yohaneMinion = null;
                    AbstractMonster summonedMonster = player.minions.monsters.get(0);
                    if (summonedMonster instanceof AbstractYohaneMinion) {
                        yohaneMinion = (AbstractYohaneMinion)summonedMonster;
                    }
                    if (yohaneMinion != null && yohaneMinion.slotOne)  {
                        player.addMinion(new Lily(-1150F, false));
                    } else {
                        player.addMinion(new Lily(-750F, true));
                    }
                    AbstractMonster Lily1 = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily1, abstractPlayer, new LilyStrength(Lily1, 0), 0));
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals(Lily.ID)) {
                    //Upgrade
                    AbstractMonster lily0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily0Upgraded, abstractPlayer, new LilyStrength(lily0Upgraded, 1), 1));
                } else if (player.minions.monsters.get(1).id.equals(Lily.ID)) {
                    //Upgrade
                    AbstractMonster lily1Upgraded = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily1Upgraded, abstractPlayer, new LilyStrength(lily1Upgraded, 1), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }
}