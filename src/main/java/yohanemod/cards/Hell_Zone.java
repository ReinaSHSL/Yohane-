package yohanemod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import yohanemod.actions.GuiltyKissAction;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;

import java.util.ArrayList;

public class Hell_Zone extends CustomCard{
    public static final String ID = "Hell_Zone";
    public static final String NAME = "Hell Zone";
    public static final String DESCRIPTION = "Deal damage equal to half of your Fallen Energy to ALL enemies. NL Exhaust. NL Retain.";
    public static final String UPGRADED_DESCRIPTION = "Deal damage equal to half of your Fallen Energy to ALL enemies. NL Retain.";
    public static final String IMG_PATH = "cards/Hell_Zone.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ALL_ENEMY;

    public Hell_Zone() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.exhaust = true;
        this.retain = true;
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        this.retain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (p.hasPower("FallenEnergy") && (p.getPower("FallenEnergy").amount > 1)) {
            float damage = p.getPower("FallenEnergy").amount/2;
            ArrayList<AbstractMonster> mo = AbstractDungeon.getCurrRoom().monsters.monsters;
            float[] tmp = new float[mo.size()];
            for (int i = 0; i < tmp.length; i++) {
                tmp[i] = damage;
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower pl : AbstractDungeon.player.powers) {
                    tmp[i] = pl.atDamageGive(tmp[i], this.damageTypeForTurn);
                    if (damage != (int)tmp[i]) {
                        this.isDamageModified = true;
                    }
                }
            }
            for (int i = 0; i < tmp.length; i++) {
                for (AbstractPower pl : AbstractDungeon.player.powers) {
                    tmp[i] = pl.atDamageFinalGive(tmp[i], this.damageTypeForTurn);
                    if (damage != (int)tmp[i]) {
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
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p,
                    multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
        }  else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hell_Zone();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
            this.exhaust = false;
        }
    }

}
