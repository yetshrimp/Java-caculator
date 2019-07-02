package homework;
import java.util.EventListener;

public interface ValueChangeListener extends EventListener {
	public abstract void valueChangePerformed(ValueChangeEvent e);
}