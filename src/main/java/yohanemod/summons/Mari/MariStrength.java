package yohanemod.summons.Mari;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public class MariStrength extends AbstractPower {
    public static final String POWER_ID = "Yohane:MariStrength";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public MariStrength(AbstractMonster m, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        updateDescription();
        this.img = getMariStrengthTexture();
    }

    @Override
    public void updateDescription()
    {
        int damage = (MariNumbers.MariAttackDamage + (this.amount * 4));
        int charge = (MariNumbers.MariHealthLoss);
        this.description = (DESCRIPTIONS[0] + damage + DESCRIPTIONS[1] + charge + DESCRIPTIONS[2]);
    }

    @Override
    public void stackPower(int stackAmount)
    {
        this.owner.increaseMaxHp(3, true);
        if (this.owner.maxHealth > 5) {
            int healthLoss = (MariNumbers.MariHealthLoss);
            AbstractFriendlyMonster mari = (AbstractFriendlyMonster) this.owner;
            String intangibleDesc = String.format("Mari gains 1 Intangible. NL Lose %d Max HP. NL Cannot be used under %d max HP. Does not scale with Evolve.", healthLoss, healthLoss);
            mari.addMove(new MinionMove("Intangible", (AbstractFriendlyMonster) this.owner, new Texture("summons/bubbles/intangible_bubble.png")
                    , intangibleDesc, () -> {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new IntangiblePlayerPower(this.owner, 1), 1));
                this.owner.decreaseMaxHealth(healthLoss);
                if (this.owner.maxHealth <= 5) {
                    mari.removeMove("Intangible");
                }
            }));
        }
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    private static Texture getMariStrengthTexture() {
        return new Texture("powers/MariStrength.png");
    }
}
