package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificPanel extends JPanel {
    /* 按键以该顺序排列 */
    private static final String[] keysName = {"10^x","log10","x^2","1/x","√","exp","log","arcsin","arccos","arctan","x^y","y√x","sin","cos","tan","dms","deg","toradians","todegrees","%","mod","CE","CA","C","/","pi","7","8","9","x","factorial","4","5","6","-","+/-","1","2","3","+","(",")","0",".","="};

    /* 0：数字，1：一元运算符，2：二元运算符，-1：特殊处理，3：括号 */
    private static final String[] keysMode = {"1","1","1","1","1","1","1","1","1","1","2","2","1","1","1","1","1","1","1","2","2","-1","-1","-1","2","0","0","0","0","2","1","0","0","0","2","1","0","0","0","2","3","3","0","2","2"};

    /* 键盘排列 */
    private static final int row = 9;
    private static final int col = 5;

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

    private JPanel scicardPanel;
    private keyPanel scikeyPanel;
    private hisPanel scihisPanel;

    private JPanel scishowPanel;
    private JTextField scishowePane;
    private JTextField scishowrPane;

    private GridBagLayout gridbag;
    private GridBagConstraints gridbagcon;

    private JPanel switchPanel;
    private JLabel switchLabel;
    private JButton switchTab;

    public ScientificPanel(int width, int nheight, int sheight, int kheight, int hheight, int bottom, String switchImg, String flushImg) {
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

        name = "Scientific";

        cont = new Cont();

        setLayout(new BorderLayout());

        card = new CardLayout();

        scicardPanel = new JPanel();
    	scicardPanel.setLayout(card);
    	scicardPanel.setPreferredSize(new Dimension(kwidth,kheight));

        scikeyPanel = new keyPanel(row,col,kwidth,kheight,keysName,keysMode);
        scihisPanel = new hisPanel(kwidth,kheight - bottom,hwidth,hheight,bottom,flushImg);

        scikeyPanel.addListener(new keyListener());
        scihisPanel.addListener(new hisListener());

        scicardPanel.add(scikeyPanel);
        scicardPanel.add(scihisPanel);

        scishowPanel = new JPanel();
        scishowPanel.setLayout(new GridLayout(2,1,0,0));

        scishowePane = new JTextField();
        /* 不允许直接编辑 */
        scishowePane.setEditable(false);
        scishowePane.setHorizontalAlignment(JTextField.RIGHT);
        /* "Consolas"代表字体，1代表样式(1是粗体，0是平常的）18是字号 */
        scishowePane.setFont(new Font("Consolas",0,18));
        scishowePane.setForeground(Color.LIGHT_GRAY);
        scishowePane.setBackground(new Color(240,240,240));
        scishowePane.setPreferredSize(new Dimension(swidth * 2 / 3, sheight));

        scishowrPane = new JTextField();
        /* 不允许直接编辑 */
        scishowrPane.setEditable(false);
        scishowrPane.setHorizontalAlignment(JTextField.RIGHT);
        scishowrPane.setFont(new Font("Consolas",1,24));
        scishowrPane.setForeground(Color.BLACK);
        scishowrPane.setBackground(new Color(240,240,240));
        scishowrPane.setPreferredSize(new Dimension(swidth * 2 / 3, sheight));

        scishowPanel.add(scishowePane);
        scishowPanel.add(scishowrPane);

        gridbag = new GridBagLayout();

        switchPanel = new JPanel(gridbag);

        switchLabel = new JLabel(name);
        switchLabel.setFont(new Font("Alias",1,24));
        switchLabel.setForeground(Color.BLACK);
        switchLabel.setBackground(new Color(240,240,240));

        // switchLabel 显示区域占两列，组件填充显示区域
        gridbagcon = new GridBagConstraints();
        gridbagcon.gridwidth = 3;
        gridbagcon.fill = GridBagConstraints.BOTH;
        gridbag.addLayoutComponent(switchLabel, gridbagcon);

        switchTab = new JButton();
        switchTab.setBackground(new Color(240,240,240));
        switchTab.setBorderPainted(false);
        switchTab.setIcon(new ImageIcon(getClass().getResource(switchImg)));
        switchTab.addActionListener(new swiListener());

        // switchTab 显示区域占一列，组件填充显示区域
        gridbagcon = new GridBagConstraints();
        gridbagcon.gridwidth = GridBagConstraints.REMAINDER;;
        gridbag.addLayoutComponent(switchTab, gridbagcon);

        switchPanel.add(switchLabel);
        switchPanel.add(switchTab);

        add(switchPanel,BorderLayout.NORTH);
        add(scishowPanel,BorderLayout.CENTER);
        add(scicardPanel,BorderLayout.SOUTH);
    }

    private void dispNew() {
        expression = cont.getHisexpression();
        result = cont.getShowNumber();
        scishowrPane.setText(result);

        if (!cont.getisEnd())
            scishowePane.setText(expression);
        else {
            scishowePane.setText("");
            cont.expflush();
            scihisPanel.hisUpdate(expression,result);
        }
    }

    private void keyDown() {
        if (mode.equals("0"))
            cont.obtainNumber(message);

        else if (mode.equals("1"))
            cont.obtainOperator(message);

        else if (mode.equals("2"))
            cont.obtainToperator(message);

        else if (mode.equals("3"))
            cont.obtainBracket(message);

        else if (mode.equals("-1")) {
            cont.obtainSpecialOrder(message);
        }

        cont.hisUpdate();
    }

    private void hisBack() {
        cont.hisback(expression,result);
    }

    class keyListener implements ValueChangeListener {
        @Override
        public void valueChangePerformed(ValueChangeEvent e) {
            message = scikeyPanel.getMessage();
            mode = scikeyPanel.getMode();
            keyDown();
            dispNew();

            if (cont.getisErr())
                scikeyPanel.acceptMessage("error");

            else if (cont.getisEnd())
                scihisPanel.hisUpdate(expression,result);

            else
                scikeyPanel.acceptMessage();
        }
    }

    class hisListener implements ValueChangeListener {
        @Override
        public void valueChangePerformed(ValueChangeEvent e) {
            expression = scihisPanel.getMexpression();
            result = scihisPanel.getMresult();
            hisBack();
            dispNew();
            scikeyPanel.acceptMessage();
        }
    }

    class swiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            card.next(scicardPanel);
        }
    }
}

