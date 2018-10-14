package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;


public class Scorn extends CustomCard {
    public static final String ID = "Yohane:Scorn";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Scorn.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int FALLEN_ENERGY = 7;
    private static final int DAMAGE_AMT = 6;
    private static final int DAMAGE_UPGRADE = 4;
    private static final int VULN_AMT = 2;
    private static final int VULN_UPGRADE = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Scorn() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.YOHANE_GREY,
                        rarity, target);
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.misc = VULN_AMT;
    }

    public boolean hasEnoughEnergy() {
        boolean retVal = super.hasEnoughEnergy();
        if ((AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID) && AbstractDungeon.player.getPower(FallenEnergy.POWER_ID).amount < this.magicNumber)) {
            retVal = false;
        }
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FallenEnergy.POWER_ID) && p.getPower(FallenEnergy.POWER_ID).amount >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.misc, false), this.misc));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Scorn();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DAMAGE_UPGRADE);
            this.misc += VULN_UPGRADE;
        }
    }
}
