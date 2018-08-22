package yohanemod.cards;

import basemod.abstracts.CustomCard;
import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.patches.AbstractCardEnum;
import yohanemod.summons.Chika.Chika;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Ruby.RubyStrength;


public class Little_Demon_Chika extends CustomCard {
    public static final String ID = "Yohane:Little_Demon_Chika";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Little_Demon_Chika.png";
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.UNCOMMON;
    private static final CardTarget target = CardTarget.SELF;

    public Little_Demon_Chika() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.YOHANE_GREY,
                        rarity, target, POOL);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster m) {
        if (abstractPlayer instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) abstractPlayer;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                player.addMinion(new Chika(-750F));
                AbstractMonster Chika0 = player.minions.monsters.get(0);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika0, abstractPlayer, new ChikaStrength(Chika0, 0), 0));
            } else if (summonCount == 1) {
                if (player.minions.monsters.get(0).id.equals(Chika.ID)) {
                    //Upgrade
                    AbstractMonster Chika0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika0Upgraded, abstractPlayer, new ChikaStrength(Chika0Upgraded, 1), 1));
                } else {
                    //No Upgrade
                    player.addMinion(new Chika(-1150F));
                    AbstractMonster Chika1 = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika1, abstractPlayer, new ChikaStrength(Chika1, 0), 0));
                }
            } else if (summonCount == 2) {
                if (player.minions.monsters.get(0).id.equals(Chika.ID)) {
                    //Upgrade
                    AbstractMonster Chika0Upgraded = player.minions.monsters.get(0);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika0Upgraded, abstractPlayer, new ChikaStrength(Chika0Upgraded, 1), 1));
                } else if (player.minions.monsters.get(1).id.equals(Chika.ID)) {
                    //Upgrade
                    AbstractMonster Chika1Upgraded = player.minions.monsters.get(1);
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika1Upgraded, abstractPlayer, new ChikaStrength(Chika1Upgraded, 1), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "I can't summon another Little Demon!", 1.0F, 2.0F));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Little_Demon_Chika();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }
}
