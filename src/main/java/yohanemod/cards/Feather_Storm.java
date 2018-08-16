package yohanemod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import yohanemod.patches.AbstractCardEnum;

import java.util.ArrayList;

public class Feather_Storm extends CustomCard {
    public static final String ID = "Yohane:Feather_Storm";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Feather_Storm.png";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int POOL = 1;
    private static final AbstractCard.CardRarity rarity = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget target = AbstractCard.CardTarget.ALL_ENEMY;


    public Feather_Storm() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.GREY, rarity,
                target, POOL);
        this.damage = this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int attackDamage = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c.cardID == "Feather") {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.drawPile));
                attackDamage += this.damage;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.cardID == "Feather") {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.discardPile));
                attackDamage += this.damage;
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.cardID == "Feather") {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
                attackDamage += this.damage;
            }
        }
        ArrayList<AbstractMonster> mo = AbstractDungeon.getCurrRoom().monsters.monsters;
        float[] tmp = new float[mo.size()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = attackDamage;
        }
        for (int i = 0; i < tmp.length; i++) {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                tmp[i] = power.atDamageGive(tmp[i], this.damageTypeForTurn);
                if (attackDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                tmp[i] = power.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
                if (attackDamage != (int)tmp[i]) {
                    this.isDamageModified = true;
                }
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] < 0.0F) {
                tmp[i] = 0.0F;
            }
        }
        multiDamage = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            this.multiDamage[i] = MathUtils.floor(tmp[i]);
        }
        attackDamage = multiDamage[0];
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p,
                multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
    }


    @Override
    public AbstractCard makeCopy() {
        return new Feather_Storm();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
