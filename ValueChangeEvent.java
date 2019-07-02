package homework;

import java.util.EventObject;
 
public class ValueChangeEvent extends EventObject {
 	private static final long serialVersionUID = 767352958358520268L;
	private boolean value;
 
	public ValueChangeEvent(Object source) {
		this(source, false);
	}
 
	public ValueChangeEvent(Object source, boolean newValue) {
		super(source);
		value = newValue;
	}
 
	public boolean getValue() {
		return value;
	}
}

