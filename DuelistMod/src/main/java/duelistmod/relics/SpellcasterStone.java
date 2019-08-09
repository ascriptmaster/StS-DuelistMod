package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.powers.incomplete.ManaPower;

public class SpellcasterStone extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("SpellcasterStone");
	public static final String IMG = DuelistMod.makeRelicPath("SpellcasterRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("SpellcasterRelic.png");
	
	public SpellcasterStone() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Spellcaster Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void atBattleStart() 
	{
		DuelistCard.applyPowerToSelf(new ManaPower(AbstractDungeon.player, AbstractDungeon.player, 5));
		DuelistCard.applyPowerToSelf(new WeakPower(AbstractDungeon.player, 2, false));
	}
	
	@Override
	public void onEquip()
	{
		setDescription();
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip("Mana", DESCRIPTIONS[1]));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new SpellcasterStone();
	}
}