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
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import yohanemod.summons.Ruby;


public class Solitude extends CustomCard {
    public static final String ID = "Yohane:Solitude";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Solitude.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final int FALLEN_ENERGY = 10;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Solitude() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            int summonCount = player.minions.monsters.size();
            if (summonCount > 0) {
                AbstractMonster minionToKill = player.minions.monsters.get(0);
                AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.LoseHPAction(minionToKill, AbstractDungeon.player, 99));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
                if (player.minions.monsters.get(1) != null) {
                    AbstractMonster minionToKill2 = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.LoseHPAction(minionToKill2, AbstractDungeon.player, 99));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I have no Little Demons!", 1.0F, 2.0F));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Solitude();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }
}
