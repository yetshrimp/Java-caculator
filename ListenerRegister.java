package homework;

import java.util.Vector;

public class ListenerRegister {
	private Vector<ValueChangeListener> listeners;
	
	public ListenerRegister() {
		listeners = new Vector<ValueChangeListener>();
	}

	public synchronized void addListener(ValueChangeListener a) {
		listeners.addElement(a);
	}

	public synchronized void removeListener(ValueChangeListener a) {
		listeners.removeElement(a);
	}

	/* @SuppressWarnings 批注允许您选择性地取消特定代码段（即，类或方法）中的警告。其中的想法是当您看到警告时，您将调查它，如果您确定它不是问题，您就可以添加一个 @SuppressWarnings 批注，以使您不会再看到警告。虽然它听起来似乎会屏蔽潜在的错误，但实际上它将提高代码安全性，因为它将防止您对警告无动于衷 — 您看到的每一个警告都将值得注意。  */

	@SuppressWarnings("unchecked")
	public void fireAEvent(ValueChangeEvent e) {
		Vector <ValueChangeListener> currentlisteners = null;
		synchronized (this) {
			currentlisteners = (Vector<ValueChangeListener>) listeners.clone();
		}

		/* 事件触发，通知所有监听器处理 */
		for (int i = 0; i < currentlisteners.size(); i++) {
			ValueChangeListener listener = (ValueChangeListener) currentlisteners.elementAt(i);
			listener.valueChangePerformed(e);
		}
	}

}