package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import yohanemod.actions.CulinaryGeniusAction;
import yohanemod.patches.AbstractCardEnum;


public class Culinary_Genius extends CustomCard {
    public static final String ID = "Yohane:Culinary_Genius";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Culinary_Genius.png";
    private static final int COST = -1;
    private static final int POOL = 1;
    private static final int HEAL = 1;
    private static final int DAMAGE = 5;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ALL_ENEMY;

    public Culinary_Genius() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.exhaust = true;
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = HEAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new CulinaryGeniusAction(p, this.multiDamage, this.damageTypeForTurn,
                this.freeToPlayOnce, this.energyOnUse, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Culinary_Genius();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
        }
    }
}
