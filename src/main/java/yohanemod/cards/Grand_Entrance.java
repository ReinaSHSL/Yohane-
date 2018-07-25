package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

public class Grand_Entrance extends CustomCard {
    public static final String ID = "Grand_Entrance";
    public static final String NAME = "Grand Entrance";
    public static final String DESCRIPTION = "Deal !D! damage to ALL enemies. NL Gain !M! Fallen Energy. NL Innate. NL Exhaust.";
    public static final String IMG_PATH = "cards/Grand_Entrance.png";
    private static final int DAMAGE_AMT = 6;
    private static final int FALLEN_ENERGY = 8;
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = CardTarget.ALL_ENEMY;

    public Grand_Entrance() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
        this.isInnate = true;
        this.exhaust = true;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p,
                this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(makeStatEquivalentCopy()));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new FallenEnergy(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Grand_Entrance();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(4);
        }
    }

}
