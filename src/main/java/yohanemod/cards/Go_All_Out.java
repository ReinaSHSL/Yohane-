package yohanemod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import basemod.abstracts.CustomCard;
import yohanemod.powers.FallenEnergy;

public class Go_All_Out extends CustomCard{
    public static final String ID = "Go_All_Out";
    public static final String NAME = "Go All Out";
    public static final String DESCRIPTION = "Exhaust ALL skills. NL Draw 4. NL Gain !M! Fallen Energy for each card Exhausted.";
    public static final String UPDATED_DESCRIPTION = "Exhaust ALL skills. NL Draw 4. NL Gain !M! Fallen Energy for each card Exhausted. NL Innate.";
    public static final String IMG_PATH = "cards/Go_All_Out.png";
    private static final int FALLEN_ENERGY = 5;
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Go_All_Out() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.isInnate = false;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int FallenGain = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c.type == CardType.SKILL) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.drawPile));
                FallenGain++;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.SKILL) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.discardPile));
                FallenGain++;
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.type == CardType.SKILL) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
                FallenGain++;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, FallenGain * this.magicNumber), FallenGain * this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 4));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Go_All_Out();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = UPDATED_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
