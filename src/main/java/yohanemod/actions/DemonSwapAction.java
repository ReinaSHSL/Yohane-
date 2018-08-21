package yohanemod.actions;

import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import yohanemod.cards.Little_Demon_Chika;
import yohanemod.cards.Little_Demon_Hanamaru;
import yohanemod.cards.Little_Demon_Lily;
import yohanemod.cards.Little_Demon_Ruby;
import yohanemod.summons.Chika.Chika;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Hanamaru.Hanamaru;
import yohanemod.summons.Hanamaru.HanamaruStrength;
import yohanemod.summons.Lily.Lily;
import yohanemod.summons.Lily.LilyStrength;
import yohanemod.summons.Ruby.Ruby;
import yohanemod.summons.Ruby.RubyStrength;

public class DemonSwapAction extends AbstractGameAction {
    private static final String[] TEXT = new String[]{"Change to who?"};
    private AbstractPlayer p;
    private boolean selected0;
    private AbstractCard card;
    private AbstractMonster summonToChange;


    DemonSwapAction(AbstractPlayer p, boolean selected0) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.selected0 = selected0;
    }

    private boolean summonCheck(AbstractCard c, AbstractPlayerWithMinions player) {
        return c.cardID.equals(Little_Demon_Ruby.ID) || c.cardID.equals(Little_Demon_Lily.ID)
                || c.cardID.equals(Little_Demon_Hanamaru.ID) || c.cardID.equals(Little_Demon_Chika.ID)
                && (!c.rawDescription.contains(player.minions.monsters.get(0).id) || !c.rawDescription.contains(player.minions.monsters.get(1).id));
    }

    @Override
    public void update() {
        AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
        if (this.duration == Settings.ACTION_DUR_FAST) {
                if (this.selected0) {
                    this.summonToChange = player.minions.monsters.get(0);
                    System.out.println(this.summonToChange);
                } else {
                    summonToChange = player.minions.monsters.get(1);
                }
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard ca : this.p.drawPile.group) {
                    if (summonCheck(ca, player)) {
                        System.out.println("Check draw");
                        tmp.addToRandomSpot(ca);
                    }
                }

                for (AbstractCard ca : this.p.discardPile.group) {
                    if (summonCheck(ca, player)) {
                        System.out.println("Check disc");
                        tmp.addToRandomSpot(ca);
                    }
                }

                for (AbstractCard ca : this.p.hand.group) {
                    if (summonCheck(ca, player)) {
                        System.out.println("Check hand");
                        tmp.addToRandomSpot(ca);
                    }
                }

                if (tmp.size() == 0) {
                    System.out.println("Check -1");
                    this.isDone = true;
                } else if (tmp.size() == 1) {
                    System.out.println("Check 0");
                    this.card = tmp.getTopCard();
                    this.isDone = true;
                } else {
                    System.out.println("Check 1");
                    AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false);
                    this.tickDuration();
            }
        } else {
                if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                    System.out.println("Check 2");
                    for (AbstractCard ca : AbstractDungeon.gridSelectScreen.selectedCards) {
                        this.card = ca;
                        card.unhover();
                    }
                    int upgradeCount = 0;
                    switch (this.card.cardID) {
                        case "Yohane:Little_Demon_Chika":
                            switch (summonToChange.id) {
                                case "Lily":
                                    AbstractMonster Chika;
                                    if (summonToChange.hasPower(LilyStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(LilyStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Chika(-750F));
                                        Chika = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Chika(-1150F));
                                        Chika = player.minions.monsters.get(1);
                                    }

                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika, p, new ChikaStrength(Chika, 1), 1));
                                    }
                                    break;
                                case "Ruby":
                                    if (summonToChange.hasPower(RubyStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(RubyStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Chika(-750F));
                                        Chika = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Chika(-1150F));
                                        Chika = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika, p, new ChikaStrength(Chika, 1), 1));
                                    }
                                    break;
                                case "Hanamaru":
                                    if (summonToChange.hasPower(HanamaruStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(HanamaruStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Chika(-750F));
                                        Chika = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Chika(-1150F));
                                        Chika = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika, p, new ChikaStrength(Chika, 1), 1));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "Yohane:Little_Demon_Ruby":
                            switch (summonToChange.id) {
                                case "Lily":
                                    AbstractMonster Ruby;
                                    if (summonToChange.hasPower(LilyStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(LilyStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Ruby(-750F));
                                        Ruby = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Ruby(-1150F));
                                        Ruby = player.minions.monsters.get(1);
                                    }

                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(Ruby, 1), 1));
                                    }
                                    break;
                                case "Chika":
                                    if (summonToChange.hasPower(ChikaStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(ChikaStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Ruby(-750F));
                                        Ruby = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Ruby(-1150F));
                                        Ruby = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(Ruby, 1), 1));
                                    }
                                    break;
                                case "Hanamaru":
                                    if (summonToChange.hasPower(HanamaruStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(HanamaruStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Ruby(-750F));
                                        Ruby = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Ruby(-1150F));
                                        Ruby = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(Ruby, 1), 1));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "Yohane:Little_Demon_Lily":
                            switch (summonToChange.id) {
                                case "Ruby":
                                    AbstractMonster Lily;
                                    if (summonToChange.hasPower(RubyStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(RubyStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Lily(-750F));
                                        Lily = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Lily(-1150F));
                                        Lily = player.minions.monsters.get(1);
                                    }

                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(Lily, 1), 1));
                                    }
                                    break;
                                case "Chika":
                                    if (summonToChange.hasPower(ChikaStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(ChikaStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Lily(-750F));
                                        Lily = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Lily(-1150F));
                                        Lily = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(Lily, 1), 1));
                                    }
                                    break;
                                case "Hanamaru":
                                    if (summonToChange.hasPower(HanamaruStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(HanamaruStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Lily(-750F));
                                        Lily = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Lily(-1150F));
                                        Lily = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(Lily, 1), 1));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "Yohane:Little_Demon_Hanamaru":
                            switch (summonToChange.id) {
                                case "Ruby":
                                    AbstractMonster Hanamaru;
                                    if (summonToChange.hasPower(RubyStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(RubyStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Hanamaru(-750F));
                                        Hanamaru = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Hanamaru(-1150F));
                                        Hanamaru = player.minions.monsters.get(1);
                                    }

                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                                    }
                                    break;
                                case "Chika":
                                    if (summonToChange.hasPower(ChikaStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(ChikaStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Hanamaru(-750F));
                                        Hanamaru = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Hanamaru(-1150F));
                                        Hanamaru = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                                    }
                                    break;
                                case "Lily":
                                    if (summonToChange.hasPower(LilyStrength.POWER_ID)) {
                                        upgradeCount = summonToChange.getPower(LilyStrength.POWER_ID).amount;
                                    }
                                    summonToChange.die();
                                    if (selected0) {
                                        player.addMinion(new Hanamaru(-750F));
                                        Hanamaru = player.minions.monsters.get(0);
                                    } else {
                                        player.addMinion(new Hanamaru(-1150F));
                                        Hanamaru = player.minions.monsters.get(1);
                                    }
                                    for (int i = 0; i < upgradeCount; i++) {
                                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                }
            this.tickDuration();
        }
    }
}
