package duelistmod.monsters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.powers.enemyPowers.EnemyMiraclePower;

public class KaibaA1 extends DuelistMonster
{
	
	public static final String ID = DuelistMod.makeID("KaibaA1"); // Monster ID (remember the prefix - yourModID:DefaultMonster)
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grab the string

	public static final String NAME = monsterstrings.NAME; // The name of the monster
	public static final String[] MOVES = monsterstrings.MOVES; // The names of the moves
	public static final String[] DIALOG = monsterstrings.DIALOG; // The dialogue (if any)
	
	private static final float HB_X = 0.0F;     
	private static final float HB_Y = -25.0F; 
	private static final float HB_W = 200.0F;   
	private static final float HB_H = 330.0F;   
	
	private static final int HP_MIN = 85; 
	private static final int HP_MAX = 115; 
	private static final int A7_HP_MIN = 95; 
	private static final int A7_HP_MAX = 140;
	
	private DuelistCard sparks;
	private DuelistCard lesser;	
	private DuelistCard hinotama;	
	private DuelistCard bew;	
	private DuelistCard beserk;
	private DuelistCard spiral;
	private DuelistCard redmed;
	private DuelistCard redeyes;	
	private DuelistCard totem;
	private DuelistCard miracle;
	private DuelistCard arma;
	private DuelistCard yamata;
	private DuelistCard mirage;
	private DuelistCard dragonwing;
	
	public KaibaA1() 
	{
		super(NAME, ID, 50, HB_X, HB_Y, HB_W, HB_H, "KaibaModel2", HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
		this.setHands(initHands());
		ArrayList<String> dialog = new ArrayList<String>();
		for (String s : DIALOG) { dialog.add(s); }
		this.setupDialog(dialog);
		this.type = EnemyType.ELITE;
		this.cardDialogMin = 3;
		this.cardDialogMax = 8;
		this.startingToken = "Dragon Token";
		this.overflowDialog = this.dialog.get(2);
		this.lowWeightHandIndex = 7;
		this.sparks = new Sparks();		
		this.lesser = new LesserDragon();	
		this.hinotama = new Hinotama();		
		this.bew = new BlueEyes();		
		this.beserk = new BerserkerCrush();	
		this.spiral = new SpiralSpearStrike();
		this.redmed = new RedMedicine();
		this.redeyes = new RedEyes();		
		this.totem = new TotemDragon();
		this.miracle = new MiraculousDescentEnemy();
		this.arma = new ArmageddonDragonEmp();
		this.yamata = new YamataDragon();
		this.mirage = new MirageDragon();
		this.dragonwing = new ExploderDragonwing();
		this.miracle.upgrade();
		this.totem.upgrade();
		this.redmed.upgrade();
		//this.deathSound = "coolKaibaSound";
	}
	
	@Override
	protected ArrayList<AbstractCard> getHandMove()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		//boolean hasLambs = hasLambs();
		int moveRoll = AbstractDungeon.aiRng.random(1, 5);
		switch (this.handIndex)
		{
		case 0:
			if (this.hasPower(EnemyMiraclePower.POWER_ID)) { moveRoll = AbstractDungeon.aiRng.random(4, 5); }
			switch (moveRoll)
			{
				case 1:
					AbstractCard mir = new MiraculousDescentEnemy();
					mir.upgrade();
					moveCards.add(mir);
					moveCards.add(new LesserDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage);
					break;
				case 2:
					AbstractCard mirB = new MiraculousDescentEnemy();
					mirB.upgrade();
					moveCards.add(mirB);
					moveCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
					break;
				case 3:
					AbstractCard mirC = new MiraculousDescentEnemy();
					mirC.upgrade();
					moveCards.add(mirC);
					moveCards.add(new CastleWalls());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND_BUFF);					
					break;
				case 4:
					moveCards.add(new LesserDragon());
					moveCards.add(new CastleWalls());
					moveCards.add(new Hinotama());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage + (this.hinotama.damage * this.hinotama.magicNumber));
					break;
				case 5:
					moveCards.add(new LesserDragon());
					moveCards.add(new PreventRat());
					moveCards.add(new CastleWalls());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage);
					break;				
			}			
			break;
		case 1:
			if (summons > 2)
			{
				moveCards.add(new MillenniumShield());
				moveCards.add(new PreventRat());
				moveCards.add(new CastleWalls());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			else if (summons > 1)
			{
				moveCards.add(new PreventRat());
				moveCards.add(new MillenniumShield());
				moveCards.add(new CastleWalls());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			else if (summons > 0)
			{
				moveCards.add(new YamataDragon());
				moveCards.add(new Sparks());
				moveCards.add(new CastleWalls());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage + this.sparks.damage);
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new PreventRat());
					moveCards.add(new YamataDragon());
					moveCards.add(new CastleWalls());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage);
				}
				else
				{
					moveCards.add(new Sparks());
					moveCards.add(new PreventRat());
					moveCards.add(new CastleWalls());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
				}
			}
				
			break;
		case 2:						
			if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 4);
				if (roll == 1)
				{
					moveCards.add(new BlueEyes());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.bew.damage);
				}
				else if (roll == 2)
				{
					moveCards.add(new BlueEyes());
					moveCards.add(new Kuriboh());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.bew.damage);
				}
				else if (roll == 3)
				{
					moveCards.add(new BoosterDragon());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else 
				{
					moveCards.add(new BoosterDragon());
					moveCards.add(new Kuriboh());
					this.setMove((byte)2, Intent.BUFF);
				}
			}
			
			else if (summons > 0)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.bew.damage);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BoosterDragon());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			
			else 
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new CastleWalls());
					moveCards.add(new Kuriboh());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					moveCards.add(new Kuriboh());
					moveCards.add(new CaveDragon());
					moveCards.add(new CastleWalls());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			break;
		case 3:
			if (!this.destructUsed && this.summonsThisCombat > 2)
			{
				moveCards.add(new LesserDragon());
				moveCards.add(new DestructPotion());
				moveCards.add(new GoldenApples());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage);
			}
			else
			{
				if (this.destructUsed)
				{
					int roll = AbstractDungeon.aiRng.random(1, 5);
					if (roll == 1)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.beserk.damage);
					}
					else if (roll == 2)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new MirageDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.beserk.damage);
					}
					else if (roll == 3)
					{
						moveCards.add(new LesserDragon());
						moveCards.add(new MirageDragon());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.lesser.damage);
					}
					else if (roll == 4)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new LesserDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.lesser.damage + this.beserk.damage);
					}
					else
					{
						moveCards.add(new LesserDragon());
						moveCards.add(new CaveDragon());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage);
					}				
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 4);
					if (roll == 1)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.beserk.damage);
					}
					else if (roll == 2)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new MirageDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.beserk.damage);
					}
					else if (roll == 3)
					{
						moveCards.add(new LesserDragon());
						moveCards.add(new MirageDragon());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.lesser.damage);
					}
					else
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new LesserDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.lesser.damage + this.beserk.damage);
					}
				}
			}
			break;
		case 4:	
			int rand = AbstractDungeon.aiRng.random(1, 5);
			if (rand == 1)
			{
				moveCards.add(new ExploderDragonwing());
				moveCards.add(new CaveDragon());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.dragonwing.damage);
			}
			else if (rand == 2)
			{
				moveCards.add(new ExploderDragonwing());
				moveCards.add(new Sparks());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK, this.dragonwing.damage + this.sparks.damage);
			}
			else if (rand == 3)
			{
				moveCards.add(new BackgroundDragon());
				moveCards.add(new CaveDragon());
				this.setMove((byte)2, Intent.DEFEND);
			}
			else if (rand == 4)
			{
				moveCards.add(new Kuriboh());
				moveCards.add(new CaveDragon());
				moveCards.add(new Sparks());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage);
			}
			else
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new Kuriboh());					
				moveCards.add(new Sparks());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage);
			}
			break;
		case 5:		
			if (summons > 0)
			{				
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new YamataDragon());
				this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.yamata.damage + this.spiral.damage);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 4);
				if (roll == 1)
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
				}
				else if (roll == 2)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new GoldenApples());					
					moveCards.add(new YamataDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage);
				}
				else if (roll == 3)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new Sparks());					
					moveCards.add(new YamataDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage + this.sparks.damage);
				}
				else
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new Sparks());
					this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.sparks.damage);
				}	
			}
			break;
		case 6:			
			if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new RedEyes());
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.redeyes.damage);
				}
				else
				{
					moveCards.add(new RedEyes());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
				}	
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 4);
				if (roll == 1)
				{
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					AbstractCard tot = new TotemDragon();
					tot.upgrade();
					moveCards.add(tot);
					this.setMove((byte)2, Intent.BUFF);
				}
				else if (roll == 2)
				{
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					moveCards.add(new CastleWalls());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else if (roll == 3)
				{
					AbstractCard tot = new TotemDragon();
					tot.upgrade();
					moveCards.add(tot);
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					AbstractCard tot = new TotemDragon();
					tot.upgrade();
					moveCards.add(tot);
					moveCards.add(new CastleWalls());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			break;
		case 7:
			if (summons >= this.armageddon)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new ArmageddonDragonEmp());					
					moveCards.add(new GoldenApples());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.arma.damage);
				}
				else
				{
					moveCards.add(new ArmageddonDragonEmp());
					moveCards.add(new CaveDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.arma.damage);
				}	
			}
			else if (summons >= this.armageddon - 1)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new ArmageddonDragonEmp());	
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.arma.damage);
			}
			else if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new RedEyes());					
					moveCards.add(new GoldenApples());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
				}
				else
				{
					moveCards.add(new RedEyes());
					moveCards.add(new CaveDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
				}	
			}
			else if (summons > 0)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new RedEyes());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new PreventRat());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else
				{
					moveCards.add(new PreventRat());
					moveCards.add(new CaveDragon());				
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND);
				}	
			}
			break;
		case 8:		
			int seed = AbstractDungeon.aiRng.random(1, 4);
			if (seed == 1)
			{
				moveCards.add(new Reinforcements());
				moveCards.add(new LesserDragon());
				moveCards.add(new Sparks());	
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage + this.lesser.damage);
			}
			else if (seed == 2)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new LesserDragon());
				moveCards.add(new Sparks());	
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage + this.lesser.damage);
			}
			else if (seed == 3)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new LesserDragon());
				moveCards.add(new LesserDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage * 2);
			}
			else if (seed == 4)
			{
				moveCards.add(new Reinforcements());
				moveCards.add(new LesserDragon());
				moveCards.add(new LesserDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage * 2);
			}
			break;
		case 9:
			int seedy = AbstractDungeon.aiRng.random(1, 5);
			if (seedy == 1)
			{
				moveCards.add(new Sparks());
				moveCards.add(new CastleWalls());
				moveCards.add(new GoldenApples());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
			}
			else if (seedy == 2)
			{
				moveCards.add(new PreventRat());
				moveCards.add(new Sparks());
				moveCards.add(new GoldenApples());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
			}
			else if (seedy == 3)
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new GoldenApples());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
			}
			else if (seedy == 4)
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new Sparks());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.sparks.damage);
			}
			else
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
			}
			break;
		case 10:
			switch (moveRoll)
			{
				case 1:
					moveCards.add(new BerserkerCrush());
					moveCards.add(new Hinotama());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK, this.beserk.damage + (this.hinotama.damage * this.hinotama.magicNumber));
					break;
				case 2:
					moveCards.add(new BerserkerCrush());
					moveCards.add(new Reinforcements());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.beserk.damage);
					break;
				case 3:
					moveCards.add(new Reinforcements());
					moveCards.add(new LesserDragon());
					moveCards.add(new Hinotama());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage + (this.hinotama.damage * this.hinotama.magicNumber));
					break;
				case 4:
					moveCards.add(new BackgroundDragon());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.hinotama.damage, this.hinotama.magicNumber, true);
					break;
				case 5:
					moveCards.add(new BackgroundDragon());
					moveCards.add(new Reinforcements());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
					break;
			}
			break;
		case 11:
			if (summons > 0)
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new YamataDragon());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.yamata.damage);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new LesserDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.lesser.damage);
				}
				else if (roll == 2)
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new YamataDragon());
					moveCards.add(new LesserDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage + this.yamata.damage);
				}
			}
			break;
		default:
			switch (moveRoll)
			{
				case 1:
					moveCards.add(new GoldenApples());
					moveCards.add(new GoldenApples());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.MAGIC);
					break;
				case 2:
					moveCards.add(new GoldenApples());
					moveCards.add(new GoldenApples());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.MAGIC);
					break;
				case 3:
					moveCards.add(new GoldenApples());
					moveCards.add(new GoldenApples());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.MAGIC);
					break;
				case 4:
					moveCards.add(new GoldenApples());
					moveCards.add(new GoldenApples());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.MAGIC);
					break;
				case 5:
					moveCards.add(new GoldenApples());
					moveCards.add(new GoldenApples());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.MAGIC);
					break;
			}
			break;
		}
		return moveCards;
	}
	
	private ArrayList<ArrayList<String>> initHands()
	{
		ArrayList<ArrayList<String>> newHands = new ArrayList<ArrayList<String>>();
		ArrayList<String> hand0 = new ArrayList<String>();
		ArrayList<String> hand1 = new ArrayList<String>();
		ArrayList<String> hand2 = new ArrayList<String>();
		ArrayList<String> hand3 = new ArrayList<String>();
		ArrayList<String> hand4 = new ArrayList<String>();
		ArrayList<String> hand5 = new ArrayList<String>();
		ArrayList<String> hand6 = new ArrayList<String>();
		ArrayList<String> hand7 = new ArrayList<String>();
		ArrayList<String> hand8 = new ArrayList<String>();
		ArrayList<String> hand9 = new ArrayList<String>();
		ArrayList<String> hand10 = new ArrayList<String>();
		ArrayList<String> hand11 = new ArrayList<String>();
		hand0.add("Miraculous Descent+");
		hand0.add("Lesser Dragon");
		hand0.add("Hinotama");
		hand0.add("Castle Walls");
		hand0.add("Prevent Rat");    	

		hand1.add("Sparks");
		hand1.add("Prevent Rat");
		hand1.add("Castle Walls");
		hand1.add("Yamata Dragon"); 
		hand1.add("Millennium Shield");

		hand2.add("Blue Eyes"); 
		hand2.add("Cave Dragon");
		hand2.add("Castle Walls");
		hand2.add("Booster Dragon");
		hand2.add("Kuriboh");

		hand3.add("Berserker Crush");
		hand3.add("Lesser Dragon");
		hand3.add("Destruct Potion");
		hand3.add("Mirage Dragon"); 
		hand3.add("Golden Apples");

		hand4.add("Exploder Dragonwing");    
		hand4.add("Kuriboh");
		hand4.add("Cave Dragon");
		hand4.add("Background Dragon");
		hand4.add("Sparks"); 

		hand5.add("Golden Apples");
		hand5.add("Sparks");
		hand5.add("Cave Dragon");
		hand5.add("Spiral Spear Strike");    
		hand5.add("Yamata Dragon"); 

		hand6.add("Red Medicine+");
		hand6.add("Red Eyes");
		hand6.add("Totem Dragon+"); 
		hand6.add("Castle Walls");
		hand6.add("Golden Apples");

		hand7.add("Armageddon Dragon");
		hand7.add("Golden Apples");    	
		hand7.add("Cave Dragon");
		hand7.add("Red Eyes"); 
		hand7.add("Prevent Rat");
		
		hand8.add("Sparks");
		hand8.add("Reinforcements");    	
		hand8.add("Lesser Dragon");
		hand8.add("Cave Dragon"); 
		hand8.add("Lesser Dragon");
				
		hand9.add("Sparks");
		hand9.add("Castle Walls");    	
		hand9.add("Prevent Rat");
		hand9.add("Spiral Spear Strike"); 
		hand9.add("Golden Apples");
				
		hand10.add("Berserker Crush");
		hand10.add("Hinotama");    	
		hand10.add("Background Dragon");
		hand10.add("Lesser Dragon"); 
		hand10.add("Reinforcements");
				
		hand11.add("Lesser Dragon");
		hand11.add("Yamata Dragon");    	
		hand11.add("Cave Dragon");
		hand11.add("Spiral Spear Strike"); 
		hand11.add("Prevent Rat");
		
		
		newHands.add(hand0);
		newHands.add(hand1);
		newHands.add(hand2);
		newHands.add(hand3);
		newHands.add(hand4);
		newHands.add(hand5);
		newHands.add(hand6);
		newHands.add(hand7);
		newHands.add(hand8);
		newHands.add(hand9);
		newHands.add(hand10);
		newHands.add(hand11);
		return newHands;
	}

	@Override
	public void onUseDestructPotion() 
	{
		this.possibleHands.get(3).clear();
		this.possibleHands.get(3).add("Beserker Crush");
		this.possibleHands.get(3).add("Lesser Dragon");
		this.possibleHands.get(3).add("Cave Dragon");
		this.possibleHands.get(3).add("Labyrinth Wall");
		this.possibleHands.get(3).add("Golden Apples");
	}

}