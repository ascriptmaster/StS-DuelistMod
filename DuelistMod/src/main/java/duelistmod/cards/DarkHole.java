package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class DarkHole extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkHole");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.DARK_HOLE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    private static int blockTotal = 0;
    // /STAT DECLARATION/

    public DarkHole() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.LIMITED);
        this.originalName = this.name;
        this.baseBlock = this.block = 0;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {    	
		// Steal all enemy block
		blockTotal = 0;
		for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters)
		{    			
			if (mon.currentBlock > 0) 
			{
				blockTotal += mon.currentBlock;
				AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(mon, mon));
			}
		}
		block(blockTotal);
		
		// Remove random debuff from yourself
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		for (AbstractPower pow : p.powers)
		{
			if (pow.type.equals(PowerType.DEBUFF))
			{
				debuffs.add(pow);
			}
		}
		
		if (debuffs.size() > 0) 
		{
			AbstractPower pow = debuffs.get(AbstractDungeon.cardRandomRng.random(debuffs.size() - 1));
			AbstractCreature own = pow.owner;
			removePower(pow, own);
		}	    
    }
    
    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkHole();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}