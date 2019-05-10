package duelistmod.cards.tokens;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.*;
import duelistmod.actions.common.ModifyTributeAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.relics.DragonRelicB;

public class DragonToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DragonToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.BABY_DRAGON);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/a

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public DragonToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.DRAGON);
    	this.purgeOnUse = true;
    }
    public DragonToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.DRAGON);
    	this.purgeOnUse = true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, 1, this);
    	ArrayList<AbstractCard> handDrags = new ArrayList<AbstractCard>();
    	for (AbstractCard c : player().hand.group)
    	{
    		if (!c.uuid.equals(this.uuid) && c instanceof DuelistCard)
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0) { handDrags.add(c); }
    		}
    	}
    	
    	if (handDrags.size() > 0)
    	{
    		DuelistCard card = (DuelistCard) handDrags.get(AbstractDungeon.cardRandomRng.random(handDrags.size() - 1));
    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(card, -1, false));
    	}
    }
    @Override public AbstractCard makeCopy() { return new DragonToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		if (tributingCard.hasTag(Tags.DRAGON) && !AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID)) 
		{ 
			if (!AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, DuelistMod.dragonStr)); }
			else { applyPowerToSelf(new StrengthPower(AbstractDungeon.player, DuelistMod.dragonStr + 1)); }
		}
		
		if (tributingCard.hasTag(Tags.DRAGON) && AbstractDungeon.player.hasRelic(DragonRelicB.ID))
		{
			if (DuelistMod.dragonRelicBFlipper) { drawRare(1, CardRarity.RARE); }
			DuelistMod.dragonRelicBFlipper = !DuelistMod.dragonRelicBFlipper;
			if (AbstractDungeon.player.getRelic(DragonRelicB.ID).counter == 1) { AbstractDungeon.player.getRelic(DragonRelicB.ID).counter = 0; }
			else { AbstractDungeon.player.getRelic(DragonRelicB.ID).counter = 1; }
		}
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	@Override public void upgrade() 
	{
		if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
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