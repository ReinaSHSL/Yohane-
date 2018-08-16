package yohanemod.cards;

import basemod.abstracts.CustomCard;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.powers.LilyStrength;
import yohanemod.powers.RubyStrength;
import yohanemod.powers.Sin;


public class Koi_Ni_Naritai extends CustomCard {
    public static final String ID = "Yohane:Koi_Ni_Naritai";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Koi_Ni_Naritai.png";
    private static final int COST = 2;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.ENEMY;
    private int SinAmount = 0;

    public Koi_Ni_Naritai() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(Sin.POWER_ID)) {
            if (p instanceof AbstractPlayerWithMinions) {
                AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) p;
                SinAmount = m.getPower(Sin.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, Sin.POWER_ID));
                if (player.minions.monsters.size() != 0) {
                    String summon0 = player.minions.monsters.get(0).id;
                    String summon1 = player.minions.monsters.get(1).id;
                    switch (summon0) {
                        case "Lily":
                            AbstractMonster Lily = player.minions.monsters.get(0);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(player, SinAmount), SinAmount));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Lily, Lily, 5));
                            break;
                        case "Ruby":
                            AbstractMonster Ruby = player.minions.monsters.get(0);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(player, SinAmount), SinAmount));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Ruby, Ruby, 5));
                            break;
                        default:
                            break;
                    }
                    switch (summon1) {
                        case "Lily":
                            AbstractMonster Lily = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(player, SinAmount), SinAmount));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Lily, Lily, 5));
                            break;
                        case "Ruby":
                            AbstractMonster Ruby = player.minions.monsters.get(1);
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(player, SinAmount), SinAmount));
                            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.HealAction(Ruby, Ruby, 5));
                            break;
                        default:
                            break;
                    }
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "This enemy has no Sin!", 1.0F, 2.0F));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Koi_Ni_Naritai();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }
}
