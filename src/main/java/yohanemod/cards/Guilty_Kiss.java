package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import yohanemod.actions.GuiltyKissAction;
import yohanemod.patches.AbstractCardEnum;


public class Guilty_Kiss extends CustomCard {
    public static final String ID = "Yohane:Guilty_Kiss";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Guilty_Kiss.png";
    private static final int DAMAGE_AMT = 8;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int VULNERABLE_AMT = 2;
    private static final int COST = -1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Guilty_Kiss() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.YOHANE_GREY,
                rarity, target);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = VULNERABLE_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
             if (this.energyOnUse < EnergyPanel.totalCount) {
                   this.energyOnUse = EnergyPanel.totalCount;
                 }
             AbstractDungeon.actionManager.addToBottom(new GuiltyKissAction(p, m,
                     this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, this.magicNumber));
           }

    @Override
    public AbstractCard makeCopy() {
        return new Guilty_Kiss();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.upgradeMagicNumber(1);
        }
    }
}
