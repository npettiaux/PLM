package lessons.recursion;

import jlm.universe.pancake.raw.InvalidPancakeNumber;
import jlm.universe.pancake.raw.PancakeEntity;
import jlm.universe.pancake.raw.PancakeWorld;

/**
 * @author Julien BASTIAN & Geoffrey HUMBERT
 * @version 1.2
 */
public class RawPancakePlateEntity extends PancakeEntity {

	public void run() throws InvalidPancakeNumber {
		try
		{
		this.solve();
		}
		catch( InvalidPancakeNumber pne)
		{
			System.out.println(this.world.getName() +" :"+pne.getMessage());
		}
	}
	
	/* BEGIN TEMPLATE */
	public void solve() throws InvalidPancakeNumber {
		/* BEGIN SOLUTION */
		PancakeWorld myWorld = (PancakeWorld) this.world;
		int stackSize = this.getStackSize();
		boolean currentPancakeAlreadySorted;
		for ( int pancakesToSort = stackSize-1 ; pancakesToSort != -1 && !myWorld.isSorted() ; pancakesToSort-- )
		{
			currentPancakeAlreadySorted= (this.getPancakeSize(pancakesToSort)==pancakesToSort+1 ) ;
			if ( !currentPancakeAlreadySorted)
			{
				int indexBigPancake =-1;
				boolean found = false;
				for ( int currentPancake = 0 ; currentPancake < pancakesToSort+1 && !found; currentPancake++)
				{
					if ( this.getPancakeSize(currentPancake) == pancakesToSort+1)
					{
						indexBigPancake = currentPancake;	// gotcha !
						found = true;
					}
				}
				if ( indexBigPancake != 0 )
				{
					this.flip(indexBigPancake+1);	// putting the pancake at the top
				}	
				if ( ! ( pancakesToSort == 0) )
				{
					this.flip(this.getPancakeSize(0));	// hit the bottom !
				}
			}	
		}
		/* END SOLUTION */
	}
	/* END TEMPLATE */
	
}
