package yohanemod.cards;

import basemod.abstracts.CustomCard;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import yohanemod.patches.AbstractCardEnum;

public class Mijuku_Dreamer extends CustomCard {
    public static final String ID = "Yohane:Mijuku_Dreamer";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Mijuku_Dreamer.png";
    private static final int STRENGTH_AMT = 2;
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
