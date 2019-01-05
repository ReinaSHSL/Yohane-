package yohanemod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import yohanemod.Yohane;

public class KobtingPlease {

    @SpirePatch(
            clz = MinionMove.class,
            method = "onHover"
    )
    public static class NoMoreHover {
        public static SpireReturn Prefix(MinionMove __instance) {
            if (AbstractDungeon.player instanceof Yohane) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractFriendlyMonster.class,
            method = "render"
    )
    public static class NoMoreRender {
        @SpireInsertPatch(
                rloc = 1
        )
        public static SpireReturn Insert(AbstractFriendlyMonster __instance, SpriteBatch sb) {
            if (__instance.hasTakenTurn() && AbstractDungeon.player instanceof Yohane) {
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }

    @SpirePatch(
            clz = AbstractFriendlyMonster.class,
            method = "update"
    )
    public static class NoMoreUpdate {
        @SpireInsertPatch(
                rloc = 1
        )
        public static SpireReturn Insert(AbstractFriendlyMonster __instance) {
            if (__instance.hasTakenTurn() && AbstractDungeon.player instanceof Yohane) {
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
