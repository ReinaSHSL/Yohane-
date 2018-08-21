package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import yohanemod.patches.AbstractCardEnum;


public class Adept_Technology extends CustomCard {
    public static final String ID = "Yohane:Adept_Technology";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Adept_Technology.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final int ATTACK_DMG = 11;
    private static final int ATTACK_DMG_UPGRADE = 3;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ALL_ENEMY;

    public Adept_Technology() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p,
                this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        for (AbstractCard c : p.hand.group) {
            c.retain = true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Adept_Technology();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_DMG_UPGRADE);
        }
    }
}
