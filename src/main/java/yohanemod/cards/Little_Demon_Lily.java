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
import com.megacrit.cardcrawl.powers.WeakPower;
import yohanemod.summons.Lily;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import yohanemod.powers.SoulLink;

public class Little_Demon_Lily extends CustomCard {
    public static final String ID = "Little_Demon_Lily";
    public static final String NAME = "Little Demon Lily";
    public static final String DESCRIPTION = "Summon 1 Lily.";
    public static final String IMG_PATH = "cards/Little_Demon_Lily.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Lily() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
    }

    //Can't be upgraded
    @Override
    public void upgrade() {

    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Lily();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

        if(abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            MonsterGroup minions = player.getMinions();
            if (minions.monsters.size() < 2) {
                if (minions.monsters.size() == 0) {
                    player.addMinion(new Lily());
                    AbstractMonster Lily = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, abstractPlayer, new SoulLink(player,1), 1));
                } else {
                    player.addMinion(new Lily());
                    AbstractMonster Lily = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, abstractPlayer, new SoulLink(player,1), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }

}
