package yohanemod.actions;

import characters.AbstractPlayerWithMinions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import monsters.AbstractFriendlyMonster;
import yohanemod.cards.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DemonSwapAction extends AbstractGameAction {
    private static final String[] TEXT = new String[]{"Change to whom?"};
    private AbstractPlayer p;
    private boolean selected0;
    private AbstractCard card;
    private AbstractMonster summonToChange;


    DemonSwapAction(AbstractPlayer p, boolean selected0) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.selected0 = selected0;
    }

    private boolean summonCheck(AbstractCard c) {
        return c.cardID.equals(Little_Demon_Ruby.ID) || c.cardID.equals(Little_Demon_Lily.ID)
                || c.cardID.equals(Little_Demon_Hanamaru.ID) || c.cardID.equals(Little_Demon_Chika.ID)
                || c.cardID.equals(Little_Demon_Mari.ID);
    }

    @Override
    public void update() {
        try {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
            if (this.duration == Settings.ACTION_DUR_FAST) {
                if (this.selected0) {
                    this.summonToChange = player.minions.monsters.get(0);
                    System.out.println(this.summonToChange);
                    System.out.println(this.summonToChange.id);
                } else {
                    summonToChange = player.minions.monsters.get(1);
                }
                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (player.minions.monsters.size() == 2) {
                    for (AbstractCard ca : this.p.drawPile.group) {
                        if (summonCheck(ca) && (!ca.rawDescription.contains(player.minions.monsters.get(0).id) &&
                                !ca.rawDescription.contains(player.minions.monsters.get(1).id))) {
                            tmp.addToRandomSpot(ca);
                        }
                    }

                    for (AbstractCard ca : this.p.discardPile.group) {
                        if (summonCheck(ca) && (!ca.rawDescription.contains(player.minions.monsters.get(0).id) &&
                                !ca.rawDescription.contains(player.minions.monsters.get(1).id))) {
                            tmp.addToRandomSpot(ca);
                        }
                    }

                    for (AbstractCard ca : this.p.hand.group) {
                        if (summonCheck(ca) && (!ca.rawDescription.contains(player.minions.monsters.get(0).id))) {
                            tmp.addToRandomSpot(ca);
                        }
                    }
                } else {
                    for (AbstractCard ca : this.p.drawPile.group) {
                        if (summonCheck(ca) && (!ca.rawDescription.contains(player.minions.monsters.get(0).id))) {
                            tmp.addToRandomSpot(ca);
                        }
                    }

                    for (AbstractCard ca : this.p.discardPile.group) {
                        if (summonCheck(ca) && (!ca.rawDescription.contains(player.minions.monsters.get(0).id))) {
                            tmp.addToRandomSpot(ca);
                        }
                    }

                    for (AbstractCard ca : this.p.hand.group) {
                        if (summonCheck(ca) && (!ca.rawDescription.contains(player.minions.monsters.get(0).id))) {
                            tmp.addToRandomSpot(ca);
                        }
                    }
                }

                if (tmp.size() == 0) {
                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false);
                    this.tickDuration();
                }
            } else {
                if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                    for (AbstractCard ca : AbstractDungeon.gridSelectScreen.selectedCards) {
                        this.card = ca;
                        card.unhover();
                    }
                    int upgradeCount = 0;
                    HashMap<String, String> demonMap = new HashMap<>();
                    demonMap.put(Little_Demon_Chika.ID , "yohanemod.summons.Chika.Chika");
                    demonMap.put(Little_Demon_Hanamaru.ID, "yohanemod.summons.Hanamaru.Hanamaru");
                    demonMap.put(Little_Demon_Lily.ID, "yohanemod.summons.Lily.Lily");
                    demonMap.put(Little_Demon_Mari.ID, "yohanemod.summons.Mari.Mari");
                    demonMap.put(Little_Demon_Ruby.ID, "yohanemod.summons.Ruby.Ruby");
                    String newParameter = demonMap.get(this.card.cardID);
                    System.out.println("newParameter = " + newParameter);
                    AbstractMonster Minion;
                    Class minionClass = Class.forName(newParameter);
                    System.out.println("minionClass = " + minionClass);
                    Constructor monsterConstructor = minionClass.getConstructor(new Class[]{float.class});
                    System.out.println("monsterConstructor = " + monsterConstructor);
                    System.out.println("Checking for " + summonToChange.id + "Strength");
                    if (summonToChange.hasPower(summonToChange.id + "Strength")) {
                        upgradeCount = summonToChange.getPower(summonToChange.id + "Strength").amount;
                        System.out.println("upgradeCount = " + upgradeCount + ", power = " + summonToChange.id + "Strength");
                    } else {
                        upgradeCount = 0;
                        System.out.println("minion did not have Strength");
                    }
                    summonToChange.die();
                    Field minionClassID = minionClass.getField("ID");
                    System.out.println("minionClassID = " + minionClassID);
                    System.out.println("minionClassID.get(minionClass) returns " + minionClassID.get(minionClass));
                    if (selected0) {
                    System.out.println("settings.scale = " + Settings.scale);
                        player.addMinion((AbstractFriendlyMonster) monsterConstructor.newInstance(new Object[]{-750f}));
                        Minion = player.getMinions().getMonster((String)minionClassID.get(minionClass));
                        player.minions.monsters.remove(Minion);
                        player.minions.monsters.add(0, Minion);
                    } else {
                        player.addMinion((AbstractFriendlyMonster) monsterConstructor.newInstance(new Object[]{-1150f}));
                        Minion = player.getMinions().getMonster((String)minionClassID.get(minionClass));
                    }
                    System.out.println("Minion = " + Minion);
                    Class powerClass = Class.forName("yohanemod.summons." + minionClassID.get(minionClass) + "." + minionClassID.get(minionClass) + "Strength");
                    System.out.println("powerClass = " + powerClass);
                    Constructor powerConstructor = powerClass.getConstructor(new Class[]{AbstractMonster.class, int.class});
                    System.out.println("powerConstructor = " + powerConstructor);
                    if (upgradeCount > 0) {
                        for (int i = 0; i < upgradeCount; i++) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Minion, p, (AbstractPower) powerConstructor.newInstance(new Object[]{Minion, 1}), 1));
                        }
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Minion, p, (AbstractPower) powerConstructor.newInstance(new Object[]{Minion, 0}), 0));
                    }
                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    this.isDone = true;
                }
                this.tickDuration();
            }
        } catch (IllegalAccessException a){System.out.println("Illegal Access Exception"); this.isDone = true;} catch (ClassNotFoundException a) {System.out.println("Class Not Found Exception"); this.isDone = true;} catch (InstantiationException a) {System.out.println("Instantiation Exception"); this.isDone = true;} catch (NoSuchMethodException a) {System.out.println("No Such Method Exception"); this.isDone = true;} catch (InvocationTargetException a) {System.out.println("Invocation Target Exception"); this.isDone = true;} catch (NoSuchFieldException a) {System.out.println("No Such Field Exception"); this.isDone = true;}
    }
}

/*
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
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Chika);
                                        player.minions.monsters.add(0, Chika);
                                    }
                                } else {
                                    player.addMinion(new Chika(-1150F));
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
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
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Chika);
                                        player.minions.monsters.add(0, Chika);
                                    }
                                } else {
                                    player.addMinion(new Chika(-1150F));
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
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
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Chika);
                                        player.minions.monsters.add(0, Chika);
                                    }
                                } else {
                                    player.addMinion(new Chika(-1150F));
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Chika, p, new ChikaStrength(Chika, 1), 1));
                                }
                                break;
                            case "Mari":
                                if (summonToChange.hasPower(MariStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(MariStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Chika(-750F));
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Chika);
                                        player.minions.monsters.add(0, Chika);
                                    }
                                } else {
                                    player.addMinion(new Chika(-1150F));
                                    Chika = player.getMinions().getMonster(yohanemod.summons.Chika.Chika.ID);
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
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Ruby);
                                        player.minions.monsters.add(0, Ruby);
                                    }
                                } else {
                                    player.addMinion(new Ruby(-1150F));
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
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
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Ruby);
                                        player.minions.monsters.add(0, Ruby);
                                    }
                                } else {
                                    player.addMinion(new Ruby(-1150F));
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
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
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Ruby);
                                        player.minions.monsters.add(0, Ruby);
                                    }
                                } else {
                                    player.addMinion(new Ruby(-1150F));
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Ruby, p, new RubyStrength(Ruby, 1), 1));
                                }
                                break;
                            case "Mari":
                                if (summonToChange.hasPower(MariStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(MariStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Ruby(-750F));
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Ruby);
                                        player.minions.monsters.add(0, Ruby);
                                    }
                                } else {
                                    player.addMinion(new Ruby(-1150F));
                                    Ruby = player.getMinions().getMonster(yohanemod.summons.Ruby.Ruby.ID);
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
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Lily);
                                        player.minions.monsters.add(0, Lily);
                                    }
                                } else {
                                    player.addMinion(new Lily(-1150F));
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
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
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Lily);
                                        player.minions.monsters.add(0, Lily);
                                    }
                                } else {
                                    player.addMinion(new Lily(-1150F));
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
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
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Lily);
                                        player.minions.monsters.add(0, Lily);
                                    }
                                } else {
                                    player.addMinion(new Lily(-1150F));
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Lily, p, new LilyStrength(Lily, 1), 1));
                                }
                                break;
                            case "Mari":
                                if (summonToChange.hasPower(MariStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(MariStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Lily(-750F));
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Lily);
                                        player.minions.monsters.add(0, Lily);
                                    }
                                } else {
                                    player.addMinion(new Lily(-1150F));
                                    Lily = player.getMinions().getMonster(yohanemod.summons.Lily.Lily.ID);
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
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Hanamaru);
                                        player.minions.monsters.add(0, Hanamaru);
                                    }
                                } else {
                                    player.addMinion(new Hanamaru(-1150F));
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
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
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Hanamaru);
                                        player.minions.monsters.add(0, Hanamaru);
                                    }
                                } else {
                                    player.addMinion(new Hanamaru(-1150F));
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
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
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Hanamaru);
                                        player.minions.monsters.add(0, Hanamaru);
                                    }
                                } else {
                                    player.addMinion(new Hanamaru(-1150F));
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                                }
                                break;
                            case "Mari":
                                if (summonToChange.hasPower(MariStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(MariStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Hanamaru(-750F));
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Hanamaru);
                                        player.minions.monsters.add(0, Hanamaru);
                                    }
                                } else {
                                    player.addMinion(new Hanamaru(-1150F));
                                    Hanamaru = player.getMinions().getMonster(yohanemod.summons.Hanamaru.Hanamaru.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Hanamaru, p, new HanamaruStrength(Hanamaru, 1), 1));
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case "Yohane:Little_Demon_Mari":
                        switch (summonToChange.id) {
                            case "Ruby":
                                AbstractMonster Mari;
                                if (summonToChange.hasPower(RubyStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(RubyStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Mari(-750F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Mari);
                                        player.minions.monsters.add(0, Mari);
                                    }
                                } else {
                                    player.addMinion(new Mari(-1150F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                }

                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari, p, new MariStrength(Mari, 1), 1));
                                }
                                break;
                            case "Chika":
                                if (summonToChange.hasPower(ChikaStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(ChikaStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Mari(-750F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Mari);
                                        player.minions.monsters.add(0, Mari);
                                    }
                                } else {
                                    player.addMinion(new Mari(-1150F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari, p, new MariStrength(Mari, 1), 1));
                                }
                                break;
                            case "Lily":
                                if (summonToChange.hasPower(LilyStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(LilyStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Mari(-750F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Mari);
                                        player.minions.monsters.add(0, Mari);
                                    }
                                } else {
                                    player.addMinion(new Mari(-1150F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari, p, new MariStrength(Mari, 1), 1));
                                }
                                break;
                            case "Hanamaru":
                                if (summonToChange.hasPower(HanamaruStrength.POWER_ID)) {
                                    upgradeCount = summonToChange.getPower(HanamaruStrength.POWER_ID).amount;
                                }
                                summonToChange.die();
                                if (selected0) {
                                    player.addMinion(new Mari(-750F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                    if (player.minions.monsters.size() == 2) {
                                        player.minions.monsters.remove(Mari);
                                        player.minions.monsters.add(0, Mari);
                                    }
                                } else {
                                    player.addMinion(new Mari(-1150F));
                                    Mari = player.getMinions().getMonster(yohanemod.summons.Mari.Mari.ID);
                                }
                                for (int i = 0; i < upgradeCount; i++) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(Mari, p, new MariStrength(Mari, 1), 1));
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                */