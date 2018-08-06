package yohanemod.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import yohanemod.cards.Defend_Grey;
import yohanemod.cards.Strike_Grey;

@SpirePatch(cls = "com.megacrit.cardcrawl.events.thecity.BackToBasics", method = "buttonEffect")
public class BackToBasicsPatch {

    @SpireInsertPatch(rloc = 15)
    public static void Insert(Object __obj_instance, int buttonPressed) {
        for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof Strike_Grey || c instanceof Defend_Grey && c.canUpgrade()) {
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1f, 0.9f) * Settings.WIDTH, MathUtils.random(0.2f, 0.8f) * Settings.HEIGHT));
            }
        }
    }
}
