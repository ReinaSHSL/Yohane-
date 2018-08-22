package yohanemod.cards.Deprecated;

import basemod.abstracts.CustomCard;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Hanamaru.HanamaruStrength;
import yohanemod.summons.Lily.LilyStrength;
import yohanemod.summons.Mari.MariStrength;
import yohanemod.summons.Ruby.RubyStrength;

public class Awakening extends CustomCard {
    public static final String ID = "Yohane:Awakening";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Awakening.png";
    private static final int COST = 0;
    private static final int DAMAGE_AMT = 8;
    private static final int FALLEN_ENERGY = 6;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;

    public Awakening() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.YOHANE_GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    public boolean hasEnoughEnergy() {
        boolean retVal = super.hasEnoughEnergy();
        if ((AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID) && AbstractDungeon.player.getPower(FallenEnergy.POWER_ID).amount >= this.magicNumber) && (EnergyPanel.getCurrentEnergy() >= this.costForTurn)) {
            retVal = false;
        }
        return retVal;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.
                DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
            if (player.minions.monsters.size() != 0) {
                String summon0 = player.minions.monsters.get(0).id;
                switch (summon0) {
                    case "Lily":
                        AbstractMonster Lily = player.minions.monsters.get(0);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(Lily, 1), 1));
                        break;
                    case "Ruby":
                        AbstractMonster Ruby = player.minions.monsters.get(0);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(Ruby, 1), 1));
                        break;
                    case "Hanamaru":
                        AbstractMonster Hanamaru = player.minions.monsters.get(0);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                        break;
                    case "Chika":
                        AbstractMonster Chika = player.minions.monsters.get(0);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika, p, new ChikaStrength(Chika, 1), 1));
                        break;
                    case "Mari":
                        AbstractMonster Mari = player.minions.monsters.get(0);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari, p, new MariStrength(Mari, 1), 1));
                        break;
                    default:
                        break;
                }
                if (player.minions.monsters.size() == 2) {
                    String summon1 = player.minions.monsters.get(1).id;
                    switch (summon1) {
                        case "Lily":
                            AbstractMonster Lily = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(Lily, 1), 1));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Lily, Lily, 5));
                            break;
                        case "Ruby":
                            AbstractMonster Ruby = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(Ruby, 1), 1));
                            break;
                        case "Hanamaru":
                            AbstractMonster Hanamaru = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                            break;
                        case "Chika":
                            AbstractMonster Chika = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika, p, new ChikaStrength(Chika, 1), 1));
                            break;
                        case "Mari":
                            AbstractMonster Mari = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari, p, new MariStrength(Mari, 1), 1));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Awakening();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(-4);
        }
    }
}
