package yohanemod.cards;

import basemod.abstracts.CustomCard;
import basemod.interfaces.PreMonsterTurnSubscriber;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import yohanemod.patches.AbstractCardEnum;


public class Stealth_Mode extends CustomCard {
    public static final String ID = "Yohane:Stealth_Mode";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Stealth_Mode.png";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int INTANGIBLE = 1;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;

    public Stealth_Mode() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.magicNumber = this.baseMagicNumber = INTANGIBLE;
        this.retain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.retain = true;
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.drawPile.group.isEmpty()) {
            for (AbstractCard c : p.drawPile.group) {
                c.current_y = (-200.0F * Settings.scale);
                c.target_x = (Settings.WIDTH / 2.0F + 200.0F);
                c.target_y = (Settings.HEIGHT / 2.0F);
                c.targetAngle = 0.0F;
                c.lighten(false);
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.75F;
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.drawPile));
            }
        }
        if (!p.discardPile.group.isEmpty()) {
            for (AbstractCard c : p.discardPile.group) {
                c.current_y = (-200.0F * Settings.scale);
                c.target_x = (Settings.WIDTH / 2.0F + 200.0F);
                c.target_y = (Settings.HEIGHT / 2.0F);
                c.targetAngle = 0.0F;
                c.lighten(false);
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.75F;
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.discardPile));
            }
        } else {
            for (AbstractCard c : p.hand.group) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
    }
    

    @Override
    public AbstractCard makeCopy() {
        return new Stealth_Mode();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

}
