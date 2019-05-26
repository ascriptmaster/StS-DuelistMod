package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.interfaces.DuelistCard;


public class DarknessNeospherePower extends TwoAmountPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DarknessNeospherePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("DarknessNeospherePower.png");
    public int turnDmg = 3;

    public DarknessNeospherePower(AbstractCreature owner, AbstractCreature source, int strLoss, int tribInc) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.source = source;
        this.owner = owner;
        this.amount = strLoss;
        this.amount2 = tribInc;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (isPlayer)
    	{
	    	for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
			{
	    		if (!m.isDeadOrEscaped() && !m.isDying)
	    		{
	    			DuelistCard.applyPower(new StrengthPower(m, -2), m);
	    		}
			}
	    	
	    	for (AbstractCard c : AbstractDungeon.player.drawPile.group)
	    	{
	    		if (c instanceof DuelistCard)
	    		{
	    			DuelistCard dC = (DuelistCard)c;
	    			if (dC.baseTributes > 0)
	    			{
	    				AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(dC, this.amount2, true));
	    			}
	    		}
	    	}
    	}
    
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount2 == 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3];
    	}
    }
}