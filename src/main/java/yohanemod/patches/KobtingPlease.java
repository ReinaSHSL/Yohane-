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
import yohanemod.summons.AbstractYohaneMinion;

public class KobtingPlease {

    @SpirePatch(
            clz = MinionMove.class,
            method = "onHover"
    )
    public static class NoMoreHover {
        public static SpireReturn Prefix(MinionMove __instance) {
            AbstractFriendlyMonster owner = (AbstractFriendlyMonster) ReflectionHacks.getPrivate(__instance, MinionMove.class, "owner");
            if (owner instanceof AbstractYohaneMinion) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
