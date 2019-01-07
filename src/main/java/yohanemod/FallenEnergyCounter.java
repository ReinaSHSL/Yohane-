package yohanemod;

import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import yohanemod.powers.FallenEnergy;
import yohanemod.tools.TextureLoader;

public class FallenEnergyCounter extends ClickableUIElement {
    private static float x = 0f * Settings.scale;
    private static float y = 128f * Settings.scale;
    private static float textX = 64f * Settings.scale;
    private static float textY = 190f * Settings.scale;
    private static float hb_w = 128f;
    private static float hb_h = 128f;
    private static Texture image = new Texture("FallenEnergyCounter.png");
    private String fallenText;
    private int fallenNumber;

    public FallenEnergyCounter() {
        super(image, x, y, hb_w, hb_h);
    }

    @Override
    protected void onHover() {

    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {

    }

    @Override
    public void update() {
        if (CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.player.hasPower(FallenEnergy.POWER_ID)) {
            super.update();
            this.fallenNumber = AbstractDungeon.player.getPower(FallenEnergy.POWER_ID).amount;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, Integer.toString(fallenNumber), textX, textY, Color.WHITE);
    }
}
