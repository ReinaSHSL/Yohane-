package yohanemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import yohanemod.YohaneMod;
import yohanemod.cards.*;
import yohanemod.summons.Chika.Chika;
import yohanemod.summons.Hanamaru.Hanamaru;
import yohanemod.summons.Lily.Lily;
import yohanemod.summons.Mari.Mari;
import yohanemod.summons.Ruby.Ruby;

import java.util.ArrayList;
import java.util.HashMap;

public class LittleDemonChangeAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private AbstractCard demonToChange;
    private AbstractPlayer p;
    private ArrayList<AbstractCard> list = new ArrayList<>();
    private AbstractCard card;
    private HashMap<String, AbstractCard> demonAndCardMap = new HashMap<>();

    public LittleDemonChangeAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = p;
    }



    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            demonAndCardMap.put(Chika.ID, new Little_Demon_Chika());
            demonAndCardMap.put(Lily.ID, new Little_Demon_Lily());
            demonAndCardMap.put(Hanamaru.ID, new Little_Demon_Hanamaru());
            demonAndCardMap.put(Mari.ID, new Little_Demon_Mari());
            demonAndCardMap.put(Ruby.ID, new Little_Demon_Ruby());
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
            if (player.minions.monsters.size() > 1) {
                for (AbstractMonster mo : player.minions.monsters) {
                    AbstractCard c = demonAndCardMap.get(mo.id);
                    list.add(c);
                }
                YohaneMod.lds.open(this.list, null, "Pick Little Demon to change.");
                tickDuration();
                return;
            } else {
                AbstractDungeon.actionManager.addToBottom(new DemonSwapAction(this.p, YohaneMod.lds.selected0));
                this.isDone = true;
                return;
            }
        }
        this.demonToChange = YohaneMod.lds.selected0;
        AbstractDungeon.actionManager.addToBottom(new DemonSwapAction(this.p, this.demonToChange));
        this.isDone = true;
    }
}