package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.EntanglePower;
import yohanemod.AbstractCardEnum;

public class Sloth extends CustomCard{
    public static final String ID = "Sloth";
    public static final String NAME = "Sloth";
    public static final String DESCRIPTION = "Heal !M! HP. NL Apply Entangle to self. NL Exhaust.";
    public static final String IMG_PATH = "cards/Sloth.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final int HEAL = 5;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Sloth() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = HEAL;
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
