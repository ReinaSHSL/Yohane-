package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;

public class Dragon_Hold extends CustomCard {
    public static final String ID = "Dragon_Hold";
    public static final String NAME = "Dragon Hold";
    public static final String DESCRIPTION = "If the enemy doesn't intend to attack, stun them. NL Exhaust";
    public static final String IMG_PATH = "cards/Dragon_Hold.png";
    private static final int COST = 3;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.ENEMY;

    public Dragon_Hold() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if ((m != null) && ((m.intent !=
                AbstractMonster.Intent.ATTACK) || (m.intent != AbstractMonster.Intent.ATTACK_BUFF)
                || (m.intent != AbstractMonster.Intent.ATTACK_DEBUFF) || (m.intent != AbstractMonster.Intent.ATTACK_DEFEND))) {
            AbstractDungeon.actionManager.addToBottom(new yohanemod.actions.StunMonsterAction(m,  p));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dragon_Hold();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }
}
