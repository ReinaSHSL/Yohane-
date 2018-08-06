package yohanemod.cards;

import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.FallenEnergy;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import yohanemod.powers.LilyStrength;
import yohanemod.powers.RubyStrength;

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
                CardType.ATTACK, AbstractCardEnum.GREY,
                rarity, target, POOL);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = FALLEN_ENERGY;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (p.hasPower(FallenEnergy.POWER_ID) && p.getPower(FallenEnergy.POWER_ID).amount > this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FallenEnergy(p, -this.magicNumber), -this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.
                    DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if (p instanceof AbstractPlayerWithMinions) {
                AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
                if (player.minions.monsters.size() != 0) {
                    String summon0 = player.minions.monsters.get(0).id;
                    String summon1 = player.minions.monsters.get(1).id;
                    switch (summon0) {
                        case "Lily":
                            AbstractMonster Lily = player.minions.monsters.get(0);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(player, 1), 1));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Lily, Lily, 5));
                            break;
                        case "Ruby":
                            AbstractMonster Ruby = player.minions.monsters.get(0);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(player, 1), 1));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Ruby, Ruby, 5));
                            break;
                        default:
                            break;
                    }
                    switch (summon1) {
                        case "Lily":
                            AbstractMonster Lily = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(player, 1), 1));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Lily, Lily, 5));
                            break;
                        case "Ruby":
                            AbstractMonster Ruby = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(player, 1), 1));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Ruby, Ruby, 5));
                            break;
                        default:
                            break;
                    }
                }
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I don't have enough Fallen Energy!", 1.0F, 2.0F));
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
