package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class keyPanel extends JPanel {
    private String message;
    private String mode;
    private int keysNum;

    private boolean isKeyDown;
    private boolean isEnabled;
    private JButton[] keys = null;

	/* 提供了监听容器listenerRegister，负责历史列表的点击事件注册。 */
	private ListenerRegister register;

    public keyPanel(int row, int col, int width, int height, String[] keysName, String[] keysMode) {
        /* 设置为网格布局，row行col列，组件水平、垂直间距为width、height */
        setLayout(new GridLayout(row, col, 3, 3));
        setPreferredSize(new Dimension(width,height));
        register = new ListenerRegister();

        keysNum = row * col;
        isKeyDown = false;
        isEnabled = true;
        keys = new JButton[keysNum];

        int i, j;
        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                keys[i * col + j] = new JButton();
                /* 为按钮设置名字和类型来分别 */
                keys[i * col + j].setText(keysName[i * col + j]);
                keys[i * col + j].setName(keysMode[i * col + j]);
                keys[i * col + j].setBackground(new Color(240,240,240));
                keys[i * col + j].addActionListener(new operator());
                add(keys[i * col + j]);
            }
        }

    }


    /* 内部类实现监听 */
    class operator implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton tmp = (JButton) e.getSource();
            message = tmp.getText();
            mode = tmp.getName();
            sendMessage();
        }
    }

    public boolean getisKeyDown() {
    	return isKeyDown;
    }

    public String getMessage() { return message; }

    public String getMode() { return mode; }

    /* 向控制模块控制模块 */
    public void sendMessage() {
    	isKeyDown = true;
    	/* 触发事件 */
    	ValueChangeEvent event = new ValueChangeEvent(this, isKeyDown);
    	fireAEvent(event);
    }

    /* 控制模块已经处理信息 */
    public void acceptMessage(String... mess) {
    	isKeyDown = false;

    	if (mess.length > 0 && mess[0].equals("error")) {
            for (Component t : getComponents()) {
                if (t instanceof JButton) {
                    if (!((((JButton) t).getText().equals("CE") || ((JButton) t).getText().equals("CA") || ((JButton) t).getText().equals("C")))) {
                        ((JButton) t).setEnabled(false);
                    }
                }
            }
            isEnabled = false;
        }

        else if (!isEnabled) {
            for (Component t : getComponents()) {
                if (t instanceof JButton) {
                    t.setEnabled(true);
                }
            }
            isEnabled = true;
        }
    }

    /* 添加监听器 */
    public void addListener(ValueChangeListener a) {
		register.addListener(a);
	}
 
	/* 删除监听器 */
	public void removeListener(ValueChangeListener a) {
		register.removeListener(a);
	}
 
 	/* 触发事件 */
    private void fireAEvent(ValueChangeEvent event) {
		register.fireAEvent(event);
	}
}
