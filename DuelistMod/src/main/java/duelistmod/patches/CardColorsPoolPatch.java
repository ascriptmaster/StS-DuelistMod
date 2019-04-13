package duelistmod.patches;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import basemod.abstracts.CustomPlayer;
import duelistmod.*;


@SpirePatch(
		clz = CustomPlayer.class,
		method = "getCardPool"
		)

public class CardColorsPoolPatch 
{
	@SpireInsertPatch(rloc=0)
	public static void insert(CustomPlayer __instance, @ByRef ArrayList<AbstractCard> tmpPool) 
	{
		if (__instance.name.equals("the Duelist"))
		{
			if (DuelistMod.shouldFill)
			{ 
				PoolHelpers.fillColoredCards(); 
				DuelistMod.shouldFill = false;
			}
			else { PoolHelpers.coloredCardsHadCards(); }
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				if (!c.rarity.equals(CardRarity.SPECIAL))
				{
					tmpPool.add(c);
				}				
			}
		}
	}
}
