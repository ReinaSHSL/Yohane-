package yohanemod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

public class Mijuku_Dreamer extends CustomCard {
    public static final String ID = "Mijuku_Dreamer";
    public static final String NAME = "Mijuku Dreamer";
    public static final String DESCRIPTION = "Gain !M! Strength for every Summon you have. NL Exhaust";
    public static final String IMG_PATH = "cards/Mijuku_Dreamer.png";
    private static final int STRENGTH_AMT = 1;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Mijuku_Dreamer() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = STRENGTH_AMT;
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            MonsterGroup minions = player.getMinions();
            if (minions.monsters.size() > 0) {
                if (minions.monsters.size() == 1) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.StrengthPower(p, this.magicNumber), this.magicNumber));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.StrengthPower(p, this.magicNumber*2), this.magicNumber*2));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I have no Little Demons!", 1.0F, 2.0F));
            }
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Mijuku_Dreamer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}