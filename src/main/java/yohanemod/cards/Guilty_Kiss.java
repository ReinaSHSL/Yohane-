package yohanemod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import yohanemod.actions.GuiltyKissAction;
import yohanemod.patches.AbstractCardEnum;


public class Guilty_Kiss extends CustomCard {
    public static final String ID = "Guilty_Kiss";
    public static final String NAME = "Guilty Kiss";
    public static final String DESCRIPTION = "If only [G]  was used, apply !M! Vulnerable. NL If more was used, deal !D! damage X times. ";
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
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = VULNERABLE_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
           {
             if (this.energyOnUse < EnergyPanel.totalCount) {
                   this.energyOnUse = EnergyPanel.totalCount;
                 }
             com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new GuiltyKissAction(p, m,
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
