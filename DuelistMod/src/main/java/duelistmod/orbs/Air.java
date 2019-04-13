package duelistmod.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.powers.*;
import duelistmod.relics.AeroRelic;

@SuppressWarnings("unused")
public class Air extends DuelistOrb
{
	public static final String ID = DuelistMod.makeID("Air");
	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
	public static final String[] DESC = orbString.DESCRIPTION;
	private float vfxIntervalMin = 0.15F; 
	private float vfxIntervalMax = 0.8F;
	private float vfxTimer = 0.5F; 	
	protected static final float VFX_INTERVAL_TIME = 0.25F;
	private static final float PI_DIV_16 = 0.19634955F;
	private static final float ORB_WAVY_DIST = 0.05F;
	private static final float PI_4 = 12.566371F;
	private static final float ORB_BORDER_SCALE = 1.2F;
	
	public Air()
	{
		this.img = ImageMaster.loadImage(DuelistMod.makePath("orbs/Air.png"));
		this.name = orbString.NAME;
		this.baseEvokeAmount = this.evokeAmount = 1;
		this.basePassiveAmount = this.passiveAmount = 1;
		this.updateDescription();
		this.angle = MathUtils.random(360.0F);
		this.channelAnimTimer = 0.5F;
		originalEvoke = this.baseEvokeAmount;
		originalPassive = this.basePassiveAmount;
		checkFocus();
	}
	
	@Override
	public void updateDescription()
	{
		applyFocus();
		if (this.evokeAmount < 2) { this.description = DESC[0] + DESC[1] + this.evokeAmount + DESC[2]; }
		else { this.description = DESC[0] + DESC[1] + this.evokeAmount + DESC[3]; }
	}

	@Override
	public void onEvoke()
	{
		applyFocus();
		//AbstractDungeon.player.increaseMaxOrbSlots(this.evokeAmount, true);
		AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(this.evokeAmount));
		if (DuelistMod.debug) { System.out.println("air orb evoked, gained orb slot. orb slots: " + AbstractDungeon.player.maxOrbs); }
	}
	
	@Override
	public void onEndOfTurn()
	{
		checkFocus();
	}

	@Override
	public void onStartOfTurn()
	{
		if (AbstractDungeon.player.hasPower(AerodynamicsPower.POWER_ID) || AbstractDungeon.player.hasRelic(AeroRelic.ID))
		{
			this.triggerPassiveEffect();
		}
		else
		{
			applyFocus();
			int roll = AbstractDungeon.cardRandomRng.random(1, 10);
			int rollCheck = AbstractDungeon.cardRandomRng.random(1, 3);
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				if (instance.isOnlyTypeSummoned(Tags.DRAGON))
				{
					rollCheck += 4;
				}
			}
			if (roll < rollCheck)
			{
				this.triggerPassiveEffect();
			}
		}
	}

	private void triggerPassiveEffect()
	{
		DuelistCard.channelRandom();
	}

	@Override
	//Taken from frost orb and modified a bit. Works to draw the basic orb image.
	public void render(SpriteBatch sb) 
	{
		sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
		sb.setBlendFunction(770, 1);
		sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + 
		MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + 
		MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, -this.angle, 0, 0, 96, 96, false, false);
		sb.setBlendFunction(770, 771);
		sb.setColor(this.c);
		sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
		renderText(sb);
		this.hb.render(sb);
	}
	
	@Override
	public void updateAnimation()
	{
		applyFocus();
		super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) 
		{
			AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = 0.25F;
		}	
	}

	@Override
	public void playChannelSFX()
	{
		
	}

	@Override
	public AbstractOrb makeCopy()
	{
		return new Air();
	}
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		// Render evoke amount text
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET - 4.0F * Settings.scale, new Color(0.2F, 1.0F, 1.0F, this.c.a), this.fontScale);
	}
	
	@Override
	public void checkFocus()
	{
		applyFocus();
		updateDescription();
	}
	
	@Override
	public void applyFocus() 
	{
		this.passiveAmount = this.basePassiveAmount;
		this.evokeAmount = this.baseEvokeAmount;
	}
}

