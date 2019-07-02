package homework;

import javafx.geometry.Pos;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StandardPanel extends JPanel {
    /* 按键以该顺序排列 */
    private static final String[] keysName = {"%","√","x^2","1/x","CE","CA","C","/","7","8","9","x","4","5","6","-","1","2","3","+","+/-","0",".","="};
    /* 0：数字， 1：一元运算符，2：二元运算符，-1：特殊处理 */
    private static final String[] keysMode = {"2","1","1","1","-1","-1","-1","2","0","0","0","2","0","0","0","2","0","0","0","2","1","0","2","2"};
    private static final int row = 6;
    private static final int col = 4;

    private int nwidth;
    private int nheight;
    private int swidth;
    private int sheight;
    private int kwidth;
    private int kheight;
    private int hwidth;
    private int hheight;
    private int bottom;

    private String flushImg;
    private String switchImg;

    private String expression;
    private String result;
    private String message;
    private String mode;

    private String name;

    private Cont cont;

    private CardLayout card;

    private JPanel stacardPanel;
    private keyPanel stakeyPanel;
    private hisPanel stahisPanel;

    private JPanel stashowPanel;
    private JTextField stashowePane;
    private JTextField stashowrPane;
    
    private GridBagLayout gridbag;
    private GridBagConstraints gridbagcon;

    private JPanel switchPanel;
    private JLabel switchLabel;
    private JButton switchTab;

    public StandardPanel(int width, int nheight, int sheight, int kheight, int hheight, int bottom, String switchImg, String flushImg) {
        this.nwidth = width;
        this.nheight = nheight;
        this.swidth = width;
        this.sheight = sheight;
        this.kwidth = width;
        this.kheight = kheight;
        this.hwidth = width;
        this.hheight = hheight;
        this.bottom = bottom;

        this.flushImg = flushImg;
        this.switchImg = switchImg;

        expression = null;
        result = null;
        message = null;
        mode = null;

        name = "Standard";

        cont = new Cont();

        setLayout(new BorderLayout());

        card = new CardLayout();

        stacardPanel = new JPanel();
        stacardPanel.setLayout(card);
        stacardPanel.setPreferredSize(new Dimension(kwidth,kheight));

        stakeyPanel = new keyPanel(row,col,kwidth,kheight,keysName,keysMode);
        stahisPanel = new hisPanel(kwidth,kheight - bottom,hwidth,hheight,bottom,flushImg);

        stakeyPanel.addListener(new stakeyListener());
        stahisPanel.addListener(new stahisListener());

        stacardPanel.add(stakeyPanel);
        stacardPanel.add(stahisPanel);

        stashowPanel = new JPanel();
        stashowPanel.setLayout(new GridLayout(2,1,0,0));

        stashowePane = new JTextField();
        /* 不允许直接编辑 */
        stashowePane.setEditable(false);
        stashowePane.setHorizontalAlignment(JTextField.RIGHT);
        /* "Consolas"代表字体，1代表样式(1是粗体，0是平常的）18是字号 */
        stashowePane.setFont(new Font("Consolas",0,18));
        stashowePane.setForeground(Color.LIGHT_GRAY);
        stashowePane.setBackground(new Color(240,240,240));

        stashowrPane = new JTextField();
        /* 不允许直接编辑 */
        stashowrPane.setEditable(false);
        stashowrPane.setHorizontalAlignment(JTextField.RIGHT);
        stashowrPane.setFont(new Font("Consolas",1,24));
        stashowrPane.setForeground(Color.BLACK);
        stashowrPane.setBackground(new Color(240,240,240));

        stashowPanel.add(stashowePane);
        stashowPanel.add(stashowrPane);

        gridbag = new GridBagLayout();

        switchPanel = new JPanel(gridbag);

        switchLabel = new JLabel(name);
        switchLabel.setFont(new Font("Alias",1,24));
        switchLabel.setForeground(Color.BLACK);
        switchLabel.setBackground(new Color(240,240,240));

        // switchLabel 显示区域占两列，组件填充显示区域
        gridbagcon = new GridBagConstraints();
        gridbagcon.gridwidth = 2;
        gridbagcon.fill = GridBagConstraints.BOTH;
        gridbag.addLayoutComponent(switchLabel, gridbagcon);

        switchTab = new JButton();
        switchTab.setBackground(new Color(240,240,240));
        switchTab.setBorderPainted(false);
        switchTab.setIcon(new ImageIcon(getClass().getResource(switchImg)));
        switchTab.addActionListener(new staswiListener());

        // switchTab 显示区域占一列，组件填充显示区域
        gridbagcon = new GridBagConstraints();
        gridbagcon.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.addLayoutComponent(switchTab, gridbagcon);

        switchPanel.add(switchLabel);
        switchPanel.add(switchTab);

        add(switchPanel,BorderLayout.NORTH);
        add(stashowPanel,BorderLayout.CENTER);
        add(stacardPanel,BorderLayout.SOUTH);
    }

    private void dispNew() {
        expression = cont.getHisexpression();
        result = cont.getShowNumber();
        if (!cont.getisEnd())
            stashowePane.setText(expression);
        else {
            stashowePane.setText("");
            cont.expflush();
        }
        stashowrPane.setText(result);

    }

    private void stakeyDown() {
        if (mode.equals("0"))
            cont.obtainNumber(message);

        else if (mode.equals("1"))
            cont.obtainOperator(message);

        else if (mode.equals("2"))
            cont.obtainToperator(message);

        else if (mode.equals("-1")) {
            cont.obtainSpecialOrder(message);
        }

        cont.hisUpdate();
    }

    private void stahisBack() {
        cont.hisback(expression,result);
    }

    class stakeyListener implements ValueChangeListener {
        @Override
        public void valueChangePerformed(ValueChangeEvent e) {
            message = stakeyPanel.getMessage();
            mode = stakeyPanel.getMode();
            stakeyDown();
            dispNew();

            if (cont.getisErr())
                stakeyPanel.acceptMessage("error");

            else
                stakeyPanel.acceptMessage();
        }
    }

    class stahisListener implements ValueChangeListener {
        @Override
        public void valueChangePerformed(ValueChangeEvent e) {
            expression = stahisPanel.getMexpression();
            result = stahisPanel.getMresult();
            stahisBack();
            dispNew();
            stakeyPanel.acceptMessage();
        }
    }

    class staswiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            card.next(stacardPanel);
        }
    }
}

