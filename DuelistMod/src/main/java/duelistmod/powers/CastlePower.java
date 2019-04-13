package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.cards.CastleDarkIllusions;
import duelistmod.interfaces.IShufflePower;

// 

public class CastlePower extends AbstractPower implements IShufflePower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("CastlePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.CASTLE_POWER);
    
    public static boolean UPGRADE = false;
    public static int SUMMONS = 5;
    public static int INC_SUMMONS = 5;

    public CastlePower(final AbstractCreature owner, final AbstractCreature source, boolean upgrade) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        if (upgrade) { UPGRADE = true; }
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount = 0; }
	}
    
    @Override
    public void onShuffle() 
    {
    	AbstractPlayer p = AbstractDungeon.player;
        if (UPGRADE) 
        { 
        	CastleDarkIllusions.incMaxSummons(p, INC_SUMMONS);
        	//CastleDarkIllusions.powerSummon(p, SUMMONS, "Castle Token", false);
        	if (DuelistMod.debug)
        	{
        		System.out.println("theDuelist:CastleDarkPower:onShuffle() ---> ran upgrade block");
        	}
        }
        else 
        {
        	CastleDarkIllusions.powerSummon(p, SUMMONS, "Castle Token", false);
        	if (DuelistMod.debug)
        	{
        		System.out.println("theDuelist:CastleDarkPower:onShuffle() ---> ran non-upgrade block");
        	}
        }
    }

    @Override
	public void updateDescription() 
    {
    	if (UPGRADE) { this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + INC_SUMMONS + DESCRIPTIONS[3]; }
    	else { this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + SUMMONS + DESCRIPTIONS[3];} 
    }
}