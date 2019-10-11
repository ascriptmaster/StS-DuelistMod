package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.unique.BurningTakeDamageAction;

public class BurningDebuff extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("BurningDebuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("BurningDebuff.png");
	
	public BurningDebuff(AbstractCreature owner, AbstractCreature source, int startAmt) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = startAmt;
        if (this.amount >= 9999) { this.amount = 9999; }
		updateDescription(); 
	}

	@Override
	public void updateDescription()
	{
		if (this.owner == null || this.owner.isPlayer) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1]; }
	}

	@Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
        {
        	if (GameActionManager.turn % 2 == 0) 
        	{
	            this.flashWithoutSound();
	            this.addToBot(new BurningTakeDamageAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));
        	}
        	else
        	{
        		this.flashWithoutSound();
        		this.amount++;
        		updateDescription();
        	}
        }
    }
}
