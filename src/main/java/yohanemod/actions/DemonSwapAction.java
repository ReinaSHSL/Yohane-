package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import yohanemod.cards.*;
import yohanemod.summons.Chika.Chika;
import yohanemod.summons.Hanamaru.Hanamaru;
import yohanemod.summons.Lily.Lily;
import yohanemod.summons.Mari.Mari;
import yohanemod.summons.Ruby.Ruby;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DemonSwapAction extends AbstractGameAction {
    private static final String[] TEXT = new String[]{"Change to whom?"};
    private AbstractPlayer p;
    private AbstractCard demonToChange;
    private AbstractCard card;
    private AbstractMonster summonToChange;
    private static HashMap<AbstractCard, String> cardToDemonMap = new HashMap<>();


    DemonSwapAction(AbstractPlayer p, AbstractCard demonToChange) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.demonToChange = demonToChange;
        cardToDemonMap.put(new Little_Demon_Chika(), Chika.ID);
        cardToDemonMap.put(new Little_Demon_Lily(), Lily.ID);
        cardToDemonMap.put(new Little_Demon_Hanamaru(), Hanamaru.ID);
        cardToDemonMap.put(new Little_Demon_Mari(), Mari.ID);
        cardToDemonMap.put(new Little_Demon_Ruby(), Ruby.ID);
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
                this.summonToChange = player.minions.getMonster(cardToDemonMap.get(this.demonToChange));
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
                    AbstractMonster Minion = player.minions.monsters.get(0);
                    Class minionClass = Class.forName(newParameter);
                    Constructor monsterConstructor = minionClass.getConstructor(new Class[]{float.class});
                    if (summonToChange.hasPower(summonToChange + "Strength")) {
                        upgradeCount = summonToChange.getPower(summonToChange + "Strength").amount;
                    }
                    summonToChange.die();
                    Field minionClassID = minionClass.getField("ID");
                    if (player.minions.monsters.size() == 0) {
                        player.addMinion((AbstractFriendlyMonster) monsterConstructor.newInstance(new Object[]{-750f}));
                        Minion = player.getMinions().getMonster((String)minionClassID.get(minionClass));
                    } else if ((player.minions.monsters.size() == 1 && player.minions.monsters.get(0).drawX == -750F * Settings.scale) ||
                            player.minions.monsters.get(1).drawX == -750F * Settings.scale) {
                        player.addMinion((AbstractFriendlyMonster) monsterConstructor.newInstance(new Object[]{-1150f}));
                        Minion = player.getMinions().getMonster((String)minionClassID.get(minionClass));
                    }
                    Class powerClass = Class.forName("yohanemod.summons." + minionClassID.get(minionClass) + "." + minionClassID.get(minionClass) + "Strength");
                    Constructor powerConstructor = powerClass.getConstructor(new Class[]{AbstractMonster.class, int.class});
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
