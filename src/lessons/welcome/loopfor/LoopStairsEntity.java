package lessons.welcome.loopfor;

import java.awt.Color;

import plm.core.model.Game;

public class LoopStairsEntity extends plm.universe.bugglequest.SimpleBuggle {
	@Override
	public void forward(int i)  { 
		throw new RuntimeException(Game.i18n.tr("Sorry Dave, I cannot let you use forward with an argument in this exercise. Use a loop instead."));
	}
	@Override
	public void backward(int i) {
		throw new RuntimeException(Game.i18n.tr("Sorry Dave, I cannot let you use backward with an argument in this exercise. Use a loop instead."));
	}

	Color[] colors = new Color[] {
			Color.blue,    Color.cyan, Color.green,  Color.yellow,
			Color.orange,  Color.red,  Color.magenta,Color.pink,};
	
	int step = -3;
	@Override
	public void forward()  {
		Color color = null;
		
		super.forward();
		if (step<0 || step%2 == 1 || (step/2)>=colors.length) {
			if (step < 0)
				color = Color.lightGray;
			else if ((step/2)>=colors.length)
				color = Color.pink;
			else
				color = colors[(step/2)%colors.length];
			
			Color oldColor = getCell().getColor();
			getWorld().getBridge().addCommand("changeCellColor", getX(), getY(), oldColor, color);
			setBrushColor(color);
			brushDown();
			brushUp();
		}
		step++;
	}

	@Override
	/* BEGIN TEMPLATE */
	public void run() { 
		/* BEGIN SOLUTION */
		forward();
		forward();
		forward();
		left();
		for (int i = 0; i<8;i++) { 
			forward();
			right();
			forward();
			left();
		}
		right();
		forward();
		forward();
		forward();
		/* END SOLUTION */
	}
	/* END TEMPLATE */
}
