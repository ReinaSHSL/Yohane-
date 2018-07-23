package yohanemod.cards;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.ShadowGatePower;

public class Shadow_Gate extends CustomCard{
    public static final String ID = "Shadow_Gate";
    public static final String NAME = "Shadow Gate";
    public static final String DESCRIPTION = "Draw !M! card and lose 5 Fallen Energy when a card is played. NL Removed if Fallen Energy drops to 0.";
    public static final String UPGRADED_DESCRIPTION = "Draw !M! cards and lose 5 Fallen Energy when a card is played. NL Removed if Fallen Energy drops to 0.";
    public static final String IMG_PATH = "cards/Shadow_Gate.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final int DRAW = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Shadow_Gate() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("FallenEnergy")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShadowGatePower(p, this.magicNumber), this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shadow_Gate();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
