package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import yohanemod.patches.AbstractCardEnum;

public class Sloth extends CustomCard{
    public static final String ID = "Yohane:Sloth";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Sloth.png";
    private static final int COST = 0;
    private static final int HEAL = 10;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Sloth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                rarity, target);
        this.magicNumber = this.baseMagicNumber = HEAL;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public boolean hasEnoughEnergy() {
        boolean returnValue = super.hasEnoughEnergy();
        for (AbstractCard playedCard : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (playedCard.type == AbstractCard.CardType.ATTACK) {
                return false;
            }
        }
        return returnValue;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(p, p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EntanglePower(p)));
        this.exhaust = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sloth();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }
}
