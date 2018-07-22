package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import characters.AbstractPlayerWithMinions;
import yohanemod.summons.Lily;

public class Little_Demon_Lily extends CustomCard {
    public static final String ID = "Little_Demon_Lily";
    public static final String NAME = "Little Demon Lily";
    public static final String DESCRIPTION = "Summon 1 Lily.";
    public static final String IMG_PATH = "cards/Little_Demon_Lily.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF_AND_ENEMY;

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

    //When selected a custom Action will happen. This isn't needed.
    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            player.addMinion(new Lily());
        }
    }

}
