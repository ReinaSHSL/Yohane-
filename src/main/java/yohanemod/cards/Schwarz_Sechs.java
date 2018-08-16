package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import yohanemod.powers.SchwarzSechsPower;

public class Schwarz_Sechs extends CustomCard {
    public static final String ID = "Yohane:Schwarz_Sechs";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Schwarz_Sechs.png";
    private static final int COST = 0;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int FALLEN_ENERGY = 6;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Schwarz_Sechs() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.GREY, rarity,
                target, POOL);

        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FallenEnergy.POWER_ID) && p.getPower(FallenEnergy.POWER_ID).amount >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SchwarzSechsPower(p, 1), 1));
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Schwarz_Sechs();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeMagicNumber(-3);
        }
    }
}
