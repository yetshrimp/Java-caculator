package homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalFrame extends JFrame {
    private final int width = 520;
    private final int head = 40;
    private final int nheight = 40;
    private final int sheight = 120;
    private final int kheight = 280;
    private final int hheight = 100;
    private final int bottom = 40;

    private final String menuImg = "menu.png";
    private final String switchImg = "history.png";
    private final String flushImg = "flush.png";

    /* 菜单栏 */
    private JMenuBar modeChoise;
    /* 菜单 */
    private JMenu modes;
    /* 菜单项 */
    private JMenuItem mode1;
    private JMenuItem mode2;

    private CardLayout card;
    private JPanel cardPanel;
    private ScientificPanel scientificPanel;
    private StandardPanel standardPanel;

    private MenuListener menuListener;

    public CalFrame() {
        setSize(width,head + nheight + sheight + kheight + bottom);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,0));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        modeChoise = new JMenuBar();
        modeChoise.setBorderPainted(false);
        modeChoise.setBackground(new Color(240,240,240));

        modes = new JMenu();
        modes.setIcon(new ImageIcon(getClass().getResource(menuImg)));
        mode1 = new JMenuItem("Standard");
        mode2 = new JMenuItem("Scientific");

        menuListener = new MenuListener();
        mode1.addActionListener(menuListener);
        mode2.addActionListener(menuListener);

        modes.add(mode1);
        modes.add(mode2);
        modeChoise.add(modes);

        add(modeChoise,BorderLayout.NORTH);

        //setJMenuBar(modeChoise);

        card = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(card);
        cardPanel.setPreferredSize(new Dimension(width,nheight + sheight + kheight + bottom));

        scientificPanel = new ScientificPanel(width,nheight,sheight,kheight,hheight,bottom,switchImg,flushImg);
        standardPanel = new StandardPanel(width,nheight,sheight,kheight,hheight,bottom,switchImg,flushImg);

        cardPanel.add("scientificPanel",scientificPanel);
        cardPanel.add("standardPanel",standardPanel);

        add(cardPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == mode1) {
                card.show(cardPanel,"standardPanel");
            }

            else if (e.getSource() == mode2) {
                card.show(cardPanel,"scientificPanel");
            }
        }
    }

}
