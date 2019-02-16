package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;


public class EnergyTreasurePower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = defaultmod.DefaultMod.makeID("EnergyTreasurePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.ENERGY_TREASURE_POWER);

    public EnergyTreasurePower(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;       
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = 15 + newAmount;
        this.updateDescription();
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
    	if (this.amount > 0) { this.amount -= Math.floor(this.amount/3); if (this.amount < 1) { DuelistCard.removePower(this, AbstractDungeon.player); } }
    	updateDescription();
    } 
    
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
    	if (DuelistCard.getSummons(AbstractDungeon.player) == DuelistCard.getMaxSummons(AbstractDungeon.player)) { DuelistCard.gainGold(this.amount, AbstractDungeon.player, true); }
    }
}
