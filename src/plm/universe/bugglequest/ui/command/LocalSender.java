package plm.universe.bugglequest.ui.command;

import java.util.List;

import plm.universe.Command;
import plm.universe.ISender;
import plm.universe.ui.CommandWorld;

public class LocalSender implements ISender {

	/*
	private JFrame frame;
	private JSlider slider;
	*/
	private BuggleCommandWorld bcw;
	
	public LocalSender(CommandWorld bcw) {
		this.bcw = (BuggleCommandWorld) bcw;
		/*
		frame = new JFrame();
		
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		
		
		slider = new JSlider(-1, -1);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int state = slider.getValue();
				bcw.setState(state);
			}
			
		});
		
		pane.add(slider, BorderLayout.NORTH);
		pane.add(new BuggleCommandWorldView(bcw), BorderLayout.CENTER);
		
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
		
		*/
	}
	
	@Override
	public void send(List<Command> commands) {
		bcw.receiveCmds(commands);
		//slider.setMaximum(slider.getMaximum()+1);
		//slider.setValue(slider.getMaximum());
	}

	@Override
	public Object convert(Command command) {
		// No need to convert it
		// since the execution is locale
		return command;
	}
	
	@Override
	public void reset() {
		bcw.reset();
		//slider.setValue(-1);
		//slider.setMaximum(-1);
	}

	@Override
	public void dispose() {
		bcw.dispose();
		//frame.dispose();
	}

}
