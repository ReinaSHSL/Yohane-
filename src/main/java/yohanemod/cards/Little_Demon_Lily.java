package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import characters.AbstractPlayerWithMinions;
import yohanemod.powers.LilyStrength;
import yohanemod.powers.SoulLink;

public class Little_Demon_Lily extends CustomCard {
    public static final String ID = "Little_Demon_Lily";
    public static final String NAME = "Little Demon Lily";
    public static final String DESCRIPTION = "Summon 1 Lily. NL Playing this card with Lily already on the field Evolves her.";
    public static final String IMG_PATH = "cards/Little_Demon_Lily.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Lily() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
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
                player.addMinion(new yohanemod.summons.Lily());
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals("Lily")) {
                    //Upgrade
                    AbstractMonster lily0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily0Upgraded, abstractPlayer, new LilyStrength(player, 1), 1));
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(lily0Upgraded, lily0Upgraded, 5));
                } else {
                    //No Upgrade
                    player.addMinion(new yohanemod.summons.Lily());
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals("Lily")) {
                    //Upgrade
                    AbstractMonster lily0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily0Upgraded, abstractPlayer, new LilyStrength(player, 1), 1));
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(lily0Upgraded, lily0Upgraded, 5));
                } else if (player.minions.monsters.get(1).id.equals("Lily")) {
                    //Upgrade
                    AbstractMonster lily1Upgraded = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily1Upgraded, abstractPlayer, new LilyStrength(player, 1), 1));
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(lily1Upgraded, lily1Upgraded, 5));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }
}


            /*
            switch (summonCount) {
                case 0:

                case 1:

                case 2:
                    if (player.minions.monsters.get(1).id.equals("Lily")) {
                        //Upgrade
                        AbstractMonster lily1Upgraded = player.minions.monsters.get(1);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily1Upgraded, abstractPlayer, new SoulLink(player, 0), 1));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lily1Upgraded, abstractPlayer, new LilyStrength(player, 1), 1));
                    } else {
                        //No Upgrade
                        player.addMinion(new yohanemod.summons.Lily());
                        AbstractMonster lilyNoUpgrade1 = player.minions.monsters.get(1);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(lilyNoUpgrade1, abstractPlayer, new SoulLink(player, 0), 1));
                        break;
                    }*/
