package lessons.recursion.hanoi;

import plm.core.model.lesson.Lecture;
import plm.core.model.lesson.Lesson;

public class Main extends Lesson {
	@Override
	protected void loadExercises() {
		addExercise(new HanoiBoard(this));
		
		Lecture interleaved = addExercise(new InterleavedHanoi(this));
		addExercise(new SplitHanoi1(this),interleaved);
		addExercise(new SplitHanoi2(this),interleaved);
		addExercise(new SplitHanoi3(this),interleaved);
		
		Lecture linear = addExercise(new LinearHanoi(this));
		addExercise(new LinearTwinHanoi(this),linear);
		
		Lecture tricolor = addExercise(new TricolorHanoi1(this));
		addExercise(new TricolorHanoi2(this),tricolor);
		addExercise(new TricolorHanoi3(this),tricolor);
		
		addExercise(new CyclicHanoi(this));
		
		addExercise(new IterativeHanoi(this));
	}
}
