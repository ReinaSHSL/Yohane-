package yohanemod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class SoulLink extends AbstractPower{
    public static final String POWER_ID = "SoulLink";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private DamageInfo soulLink;
    private AbstractPower powerToApply;

    public SoulLink(AbstractPlayer p, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = p;
        this.amount = damage;
        updateDescription();
        this.img = getSoulLinkTexture();
        this.soulLink = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);

    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        int damageTaken = info.base;
        this.soulLink = new DamageInfo(this.owner, damageTaken, DamageInfo.DamageType.THORNS);
        if (((info.owner != null) && (info.owner != this.owner))){
            flash();
            AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DamageAction(this.owner,
                    this.soulLink, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        return damageAmount;
    }

    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0]);
    }

    public static Texture getSoulLinkTexture() {
        return new Texture("powers/SoulLink.png");
    }
}
