package homework;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hisPanel extends JPanel {
	private JButton flushBtn;
	private JPanel flushPanel;
	
	private JPanel hisPanel;
    private JScrollPane hisScroll;
    
    private int hlen;
	private int hwidth;
	private int hheight;
    private int bottom;

	private ArrayList<String> mexpressions;
	private ArrayList<String> mresults;
	private String mexpression;
	private String mresult;

	private boolean isBack;

	/* 提供了监听容器listenerRegister，负责历史列表的点击事件注册。 */
	ListenerRegister register;

	public hisPanel(int width, int height, int hwidth, int hheight, int bottom, String img) {
		setLayout(new BorderLayout(0,0));
		register = new ListenerRegister();

		flushBtn = new JButton();
		flushBtn.setBackground(new Color(240,240,240));
        flushBtn.setBorderPainted(false);
        flushBtn.setIcon(new ImageIcon(getClass().getResource(img)));
		flushBtn.setPreferredSize(new Dimension(bottom,bottom));
		flushBtn.addActionListener(new hisFlush());
		
		flushPanel = new JPanel();
		flushPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		flushPanel.add(flushBtn);
		
		hisPanel = new JPanel();
		hisPanel.setPreferredSize(new Dimension(width,height));
		//hisScroll = new JScrollPane(hisPanel);

		//add(hisScroll,BorderLayout.CENTER);

        //hisUpdate("2x3","6");
        
        add(hisPanel,BorderLayout.CENTER);
		add(flushBtn,BorderLayout.SOUTH);
		
		hlen = 0;
		hwidth = hwidth;
		hheight = hheight;

		mexpressions = new ArrayList<String>();
		mresults = new ArrayList<String>();
	}

    //public void hisUpdatee(String hisexpression, String hisresults)

	public void hisUpdate(String hisexpression, String hisresults) {
        hlen++;

        JPanel hisshowPanel;
        JTextField hisshowePane;
        JTextField hisshowrPane;

        hisshowPanel = new JPanel();
        hisshowPanel.setName(String.valueOf(hlen));
        hisshowPanel.setPreferredSize(new Dimension(hwidth,hheight));

        hisshowePane = new JTextField();
        hisshowePane.setEditable(false);
        hisshowePane.setText(hisexpression);
        hisshowePane.setHorizontalAlignment(JTextField.RIGHT);
		hisshowePane.setFont(new Font("Consolas",0,15));
		hisshowePane.setForeground(Color.LIGHT_GRAY);
		hisshowePane.setBackground(new Color(240,240,240));
        hisshowePane.setPreferredSize(new Dimension(hwidth * 2 / 3,hheight / 2));

        hisshowrPane = new JTextField();
        hisshowrPane.setEditable(false);
        hisshowrPane.setText(hisresults);
        hisshowrPane.setHorizontalAlignment(JTextField.RIGHT);
        hisshowrPane.setFont(new Font("Consolas",0,21));
        hisshowrPane.setForeground(Color.BLACK);
        hisshowrPane.setBackground(new Color(240,240,240));
        hisshowrPane.setPreferredSize(new Dimension(hwidth * 2 / 3,hheight / 2));

        hisshowPanel.add(hisshowePane);
        hisshowPanel.add(hisshowrPane);
        hisshowPanel.addMouseListener(new hisBack());

		hisPanel.add(hisshowPanel);
		mexpressions.add(hisexpression);
		mresults.add(hisresults);
		//hisupdateUI();
		validate();
	}

	/* 清空历史面板 */
    class hisFlush implements ActionListener {
    	@Override
		public void actionPerformed(ActionEvent e) {
			mexpressions.clear();
	        mresults.clear();
	        hisPanel.removeAll();
	        hlen = 0;
		}
    }

	class hisBack implements MouseListener {
		@Override
	    public void mouseClicked(MouseEvent e) {
	        /* 鼠标按键在组件上单击（按下并释放）时调用。 */
	        JPanel tmp = (JPanel) e.getSource();

	        for (Component t : tmp.getComponents())
	            if (t instanceof JTextField)
	                t.setBackground(Color.GRAY);

	        int i = Integer.parseInt(tmp.getName());
	        mexpression = mexpressions.get(i);
	        mresult = mresults.get(i);
	        sendMessage();        
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
	        /* 鼠标按键在组件上按下时调用。 */
            JPanel tmp = (JPanel) e.getSource();

            for (Component t : tmp.getComponents())
                if (t instanceof JTextField)
                    t.setBackground(Color.GRAY);
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
	        /* 鼠标按钮在组件上释放时调用。必须发生在 mousePressed 和 mouseDragged 之后 */
            JPanel tmp = (JPanel) e.getSource();

            for (Component t : tmp.getComponents())
                if (t instanceof JTextField)
                    t.setBackground(new Color(240,240,240));
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
	        /* 鼠标进入到组件上时调用。 */
	        JLabel tmp = (JLabel) e.getSource();
	        tmp.setBackground(Color.LIGHT_GRAY);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
	        /* 鼠标离开组件时调用。 */
            JPanel tmp = (JPanel) e.getSource();

            for (Component t : tmp.getComponents())
                if (t instanceof JTextField)
                    t.setBackground(new Color(240,240,240));
	    }
 
	}

	public boolean getisBack() {
    	return isBack;
    }

    public String getMexpression() { return mexpression; }

    public String getMresult() { return mresult; }

    /* 向控制模块控制模块 */
    public void sendMessage() {
    	isBack = true;
    	ValueChangeEvent event = new ValueChangeEvent(this, isBack);
    	fireAEvent(event);
    }

    /* 控制模块已经处理信息 */
    public void acceptMessage() {
    	isBack = false;
    }

    /* 动态添加组件 */
    public void hisupdateUI() {
        SwingUtilities.updateComponentTreeUI(this);//添加或删除组件后,更新窗口
        //JScrollBar jsb = jsp.getVerticalScrollBar();//得到垂直滚动条
        //jsb.setValue(jsb.getMaximum());//把滚动条位置设置到最下面
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
	public void fireAEvent(ValueChangeEvent event) {
		register.fireAEvent(event);
	}

}
