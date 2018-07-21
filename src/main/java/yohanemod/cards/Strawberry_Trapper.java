package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.powers.StrawberryTrapperPower;
import yohanemod.AbstractCardEnum;

public class Strawberry_Trapper extends CustomCard{
    public static final String ID = "Strawberry_Trapper";
    public static final String NAME = "Strawberry Trapper";
    public static final String DESCRIPTION = "Gain !M! Strength. NL Draw !D! Card. NL At the end of your turn, lose 40 HP.";
    public static final String UPGRADED_DESCRIPTION = "Gain !M! Strength. NL Draw !D! Cards. NL At the end of your turn, lose 40 Health.";
    public static final String IMG_PATH = "cards/Strawberry_Trapper.png";
    private static final int ATTACK_DMG = 1;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int STR_AMOUNT = 10;
    private static final int COST = 3;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Strawberry_Trapper() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.GREY,
                rarity, target, POOL);

        this.damage = this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = STR_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.damage));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrawberryTrapperPower(p,40)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Strawberry_Trapper();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(5);
            this.rawDescription = UPGRADED_DESCRIPTION;
            initializeDescription();
        }
    }

}
