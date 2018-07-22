package yohanemod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.cards.curses.Clumsy;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import yohanemod.patches.AbstractCardEnum;

public class Miracle_Wave extends CustomCard{
    public static final String ID = "Miracle_Wave";
    public static final String NAME = "Miracle Wave";
    public static final String DESCRIPTION = "Deal !D! damage. NL Add an Injury to your deck.";
    public static final String UPGRADED_DESCRIPTION = "Deal !D! damage. NL Add a Clumsy to your deck.";
    public static final String IMG_PATH = "cards/Miracle_Wave.png";
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 12;
    private static final int DAMAGE_UPGRADE = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.COMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Miracle_Wave() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (!this.upgraded) {
            AbstractCard c = new Injury();
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true, true));
        } else {
            AbstractCard c = new Clumsy();
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, true, true));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Miracle_Wave();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DAMAGE_UPGRADE);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();
        }
    }

}
