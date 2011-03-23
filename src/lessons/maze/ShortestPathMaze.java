package lessons.maze;

import java.awt.Color;

import jlm.core.Game;
import jlm.lesson.ExerciseTemplated;
import jlm.lesson.Lesson;
import jlm.universe.Direction;
import jlm.universe.Entity;
import jlm.universe.World;
import universe.bugglequest.AbstractBuggle;
import universe.bugglequest.Buggle;
import universe.bugglequest.BuggleWorld;
import universe.bugglequest.exception.AlreadyHaveBaggleException;
import universe.bugglequest.exception.NoBaggleUnderBuggleException;

public class ShortestPathMaze extends ExerciseTemplated {

	public ShortestPathMaze(Lesson lesson) {
		super(lesson);
		tabName = "JediEscaper";
				
		/* Create initial situation */
		BuggleWorld myWorlds[] = new BuggleWorld[2];
		
		myWorlds[0] = new BuggleWorld("Labyrinth", 1, 1); 	
		loadMap(myWorlds[0],"lessons/maze/WallFollowerMaze");
        new Buggle(myWorlds[0], "Thésée", 7, 10, Direction.NORTH, Color.black, Color.lightGray);
        
		myWorlds[1] = new BuggleWorld("Labyrinth2", 4, 4); 
		loadMap(myWorlds[1],"lessons/maze/PledgeMaze");
		new Buggle(myWorlds[1], "Luke", 12, 14, Direction.NORTH, Color.black, Color.lightGray);
		
		//newSourceAliased("lessons.maze.Main","lessons.maze.WallFollowerMaze","Escaper");
		setup(myWorlds);
	}

	// to shorten loading time	
	
	@Override
	protected void computeAnswer(){
		AbstractBuggle b = (AbstractBuggle)answerWorld[0].entities().next();
		b.setPos(11, 5);
		b.setDirection(Direction.EAST);

		AbstractBuggle b2 = (AbstractBuggle)answerWorld[1].entities().next();
		b2.setPos(19, 19);
		b2.setDirection(Direction.EAST);

		try {
			b.pickUpBaggle();
			//b2.pickUpBaggle();
		} catch (NoBaggleUnderBuggleException e) {
			e.printStackTrace();
		} catch (AlreadyHaveBaggleException e) {
			e.printStackTrace();
		}		
	}
	
	
	@Override
	public boolean check() {
		for (World w: Game.getInstance().getCurrentLesson().getCurrentExercise().getCurrentWorld())
			for (Entity e:w.getEntities()) {
				if (!((AbstractBuggle) e).isCarryingBaggle())
					return false;
			}
		return true;
	}

}