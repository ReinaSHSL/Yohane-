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
import yohanemod.YohaneMod;
import yohanemod.cards.*;
import yohanemod.summons.Chika.Chika;
import yohanemod.summons.Chika.ChikaStrength;
import yohanemod.summons.Hanamaru.Hanamaru;
import yohanemod.summons.Hanamaru.HanamaruStrength;
import yohanemod.summons.Lily.Lily;
import yohanemod.summons.Lily.LilyStrength;
import yohanemod.summons.Ruby.Ruby;
import yohanemod.summons.Ruby.RubyStrength;

import java.util.ArrayList;
import java.util.Iterator;

public class LittleDemonChangeAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private static final String[] TEXT = new String[]{"Add a Summon to your hand."};
    private boolean selected0;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> list = new ArrayList<>();
    private ArrayList<AbstractCard> summons = new ArrayList<>();

    public LittleDemonChangeAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = p;
        AbstractCard card = new LittleDemonFirst();
        this.list.add(card);
        card = new LittleDemonSecond();
        this.list.add(card);
        card = new Little_Demon_Chika();
        this.summons.add(card);
        card = new Little_Demon_Ruby();
        this.summons.add(card);
        card = new Little_Demon_Hanamaru();
        this.summons.add(card);
        card = new Little_Demon_Lily();
        this.summons.add(card);
    }

    public void update() {
        AbstractMonster summonToChange;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            YohaneMod.lds.open(this.list, null, "Pick Little Demon to change.");
            tickDuration();
            return;
        }

        if (YohaneMod.lds.selected0.cardID.equals(LittleDemonFirst.ID)) this.selected0 = true;
        else if (YohaneMod.lds.selected0.cardID.equals(LittleDemonSecond.ID)) this.selected0 = false;

        if (this.p instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
            if (this.selected0) {
                summonToChange = player.minions.monsters.get(0);
            } else {
                summonToChange = player.minions.monsters.get(1);
            }
            AbstractCard card;
            if (this.duration == Settings.ACTION_DUR_FAST) {
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                Iterator var5 = this.p.drawPile.group.iterator();

                while (var5.hasNext()) {
                    AbstractCard c = (AbstractCard) var5.next();
                    if (summons.contains(c)) {
                        tmp.addToRandomSpot(c);
                    }
                }

                if (tmp.size() == 0) {
                    this.isDone = true;
                } else if (tmp.size() == 1) {
                    card = tmp.getTopCard();
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false);
                    this.tickDuration();
                }
            } else {
                if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                    Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                    while (var1.hasNext()) {
                        card = (AbstractCard) var1.next();
                        card.unhover();
                        int upgradeCount = 0;
                        switch (card.cardID) {
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
                                            Ruby = player.minions.monsters.get(0);
                                            player.addMinion(new Ruby(-750F));
                                        } else {
                                            Ruby = player.minions.monsters.get(1);
                                            player.addMinion(new Ruby(-1150F));
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
                                            Ruby = player.minions.monsters.get(0);
                                            player.addMinion(new Chika(-750F));
                                        } else {
                                            Ruby = player.minions.monsters.get(1);
                                            player.addMinion(new Chika(-1150F));
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
                                            Ruby = player.minions.monsters.get(0);
                                            player.addMinion(new Ruby(-750F));
                                        } else {
                                            Ruby = player.minions.monsters.get(1);
                                            player.addMinion(new Ruby(-1150F));
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
                                            Lily = player.minions.monsters.get(0);
                                            player.addMinion(new Lily(-750F));
                                        } else {
                                            Lily = player.minions.monsters.get(1);
                                            player.addMinion(new Lily(-1150F));
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
                                            Lily = player.minions.monsters.get(0);
                                            player.addMinion(new Chika(-750F));
                                        } else {
                                            Lily = player.minions.monsters.get(1);
                                            player.addMinion(new Chika(-1150F));
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
                                            Lily = player.minions.monsters.get(0);
                                            player.addMinion(new Lily(-750F));
                                        } else {
                                            Lily = player.minions.monsters.get(1);
                                            player.addMinion(new Lily(-1150F));
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
                                            Hanamaru = player.minions.monsters.get(0);
                                            player.addMinion(new Hanamaru(-750F));
                                        } else {
                                            Hanamaru = player.minions.monsters.get(1);
                                            player.addMinion(new Hanamaru(-1150F));
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
                                            Hanamaru = player.minions.monsters.get(0);
                                            player.addMinion(new Chika(-750F));
                                        } else {
                                            Hanamaru = player.minions.monsters.get(1);
                                            player.addMinion(new Chika(-1150F));
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
                                            Hanamaru = player.minions.monsters.get(0);
                                            player.addMinion(new Hanamaru(-750F));
                                        } else {
                                            Hanamaru = player.minions.monsters.get(1);
                                            player.addMinion(new Hanamaru(-1150F));
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
            tickDuration();
        }
    }
}
