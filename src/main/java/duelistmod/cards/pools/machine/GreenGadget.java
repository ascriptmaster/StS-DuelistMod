package duelistmod.cards.pools.machine;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class GreenGadget extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("GreenGadget");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("GreenGadget.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public GreenGadget() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.MACHINE_DECK);
        this.tags.add(Tags.EXODIA_DECK);
		this.exodiaDeckCopies = 1;
		this.machineDeckCopies = 1;		
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.baseBlock = this.block = 6;
		this.magicNumber = this.baseMagicNumber = 2;
		this.setupStartingCopies();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		block(this.block);
		ArrayList<DuelistCard> tokens = DuelistCardLibrary.getTokensForCombat();
		ArrayList<AbstractCard> abTokens = new ArrayList<AbstractCard>();
		int iterations = this.magicNumber;
		abTokens.addAll(tokens);
		if (!(iterations >= tokens.size())) { for (int i = 0; i < tokens.size() - iterations; i++) { abTokens.remove(AbstractDungeon.cardRandomRng.random(abTokens.size() - 1)); }}
		AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(false, false, 1, abTokens));
		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new GreenGadget();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			//this.upgradeMagicNumber(1);
			this.upgradeBlock(3);
			exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION); 
		}
	}


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons)
	{

	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
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
