package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Windows extends JFrame {

    public static final String LINE = "line";
    public static final String CIRCLE = "circle";
    public static final String OVAL = "oval";
    public static final String REC = "rectangle";
    public static final String TEXT = "text";

    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    public static final int OPTION_BAR_HEIGHT = 40;

    private String shapeSelected = null;
    private Color colorSelected = Color.black;

    private Canvas g;

    public Windows() {
        super("White Board");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // option bar on the top of the canvas
        JPanel optionbar = new JPanel();
        optionbar.setPreferredSize(new Dimension(Windows.WIDTH, Windows.OPTION_BAR_HEIGHT));

        JMenuBar menuBar = new JMenuBar();

        JMenu colors = new JMenu("Colours");
        // change the text font size
        colors.setFont(new Font("", Font.PLAIN, 18));

        JMenuItem black = new JMenuItem("Black");
        // change the text font size
        black.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.black;
            }
        });
        colors.add(black);

        JMenuItem blue = new JMenuItem("Blue");
        // change the text font size
        blue.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.blue;
            }
        });
        colors.add(blue);

        JMenuItem cyan = new JMenuItem("Cyan");
        // change the text font size
        cyan.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        cyan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.cyan;
            }
        });
        colors.add(cyan);

        JMenuItem darkGray = new JMenuItem("Dark Gray");
        // change the text font size
        darkGray.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        darkGray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.darkGray;
            }
        });
        colors.add(darkGray);

        JMenuItem gray = new JMenuItem("Gray");
        // change the text font size
        gray.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        gray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.gray;
            }
        });
        colors.add(gray);

        JMenuItem green = new JMenuItem("Green");
        // change the text font size
        green.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.green;
            }
        });
        colors.add(green);

        JMenuItem lightGray = new JMenuItem("Light Gray");
        // change the text font size
        lightGray.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        lightGray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.lightGray;
            }
        });
        colors.add(lightGray);

        JMenuItem magenta = new JMenuItem("Magenta");
        // change the text font size
        magenta.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        magenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.magenta;
            }
        });
        colors.add(magenta);

        JMenuItem orange = new JMenuItem("Orange");
        // change the text font size
        orange.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        orange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.orange;
            }
        });
        colors.add(orange);

        JMenuItem pink = new JMenuItem("Pink");
        // change the text font size
        pink.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        pink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.pink;
            }
        });
        colors.add(pink);

        JMenuItem red = new JMenuItem("Red");
        // change the text font size
        red.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.red;
            }
        });
        colors.add(red);

        JMenuItem white = new JMenuItem("White");
        // change the text font size
        white.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.white;
            }
        });
        colors.add(white);

        JMenuItem yellow = new JMenuItem("Yellow");
        // change the text font size
        yellow.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        yellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                colorSelected = Color.yellow;
            }
        });
        colors.add(yellow);

        JMenuItem more = new JMenuItem("More...");
        // change the text font size
        more.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        more.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel colorPopup = new JPanel();
                colorPopup.setLayout(new BoxLayout(colorPopup, BoxLayout.PAGE_AXIS));

                JLabel des = new JLabel("Enter RGB value for your favourite color");
                // change text font size
                des.setFont(new Font("", Font.PLAIN, 18));
                colorPopup.add(des);

                JPanel inputField = new JPanel();
                JTextField rField = new JTextField("R");
                rField.setPreferredSize(new Dimension(70, 30));
                inputField.add(rField);

                JTextField gField = new JTextField("G");
                gField.setPreferredSize(new Dimension(70, 30));
                inputField.add(gField);

                JTextField bField = new JTextField("B");
                bField.setPreferredSize(new Dimension(70, 30));
                inputField.add(bField);

                colorPopup.add(inputField);

                JOptionPane.showMessageDialog(null, colorPopup);
                boolean invalidInput = false;
                String rValueString = rField.getText();
                int rValue;
                try {
                    rValue = Integer.parseInt(rValueString);
                }
                catch (NumberFormatException re) {
                    rValue = -1;
                    invalidInput = true;
                }
                if (rValueString.length() == 0 || rValue < 0 || rValue > 255) {
                    invalidInput = true;
                }
                String gValueString = gField.getText();
                int gValue;
                try {
                    gValue = Integer.parseInt(gValueString);
                }
                catch (NumberFormatException ge) {
                    gValue = -1;
                    invalidInput = true;
                }
                if (gValueString.length() == 0 || gValue < 0 || gValue > 255) {
                    invalidInput = true;
                }
                String bValueString = bField.getText();
                int bValue;
                try {
                    bValue = Integer.parseInt(bField.getText());
                }
                catch (NumberFormatException be) {
                    bValue = -1;
                    invalidInput = true;
                }
                if (bValueString.length() == 0 || bValue < 0 || bValue > 255) {
                    invalidInput = true;
                }
                if (!invalidInput) {
                    colorSelected = new Color(rValue, gValue, bValue);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid RGB value provided");
                }
            }
        });
        colors.add(more);

        JMenu shapes = new JMenu("Shapes");
        // change the text font size
        shapes.setFont(new Font("", Font.PLAIN, 18));


        JMenuItem line = new JMenuItem("Line");
        // change the text font size
        line.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeSelected = Windows.LINE;
            }
        });
        shapes.add(line);

        JMenuItem circle = new JMenuItem("Circle");
        // change the text font size
        circle.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeSelected = Windows.CIRCLE;
            }
        });
        shapes.add(circle);

        JMenuItem oval = new JMenuItem("Oval");
        // change the text font size
        oval.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeSelected = Windows.OVAL;
            }
        });
        shapes.add(oval);

        JMenuItem rec = new JMenuItem("Rectangle");
        // change the text font size
        rec.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeSelected = Windows.REC;
            }
        });
        shapes.add(rec);


        JMenu insert = new JMenu("Insert");
        // change the text font size
        insert.setFont(new Font("", Font.PLAIN, 18));

        JMenuItem text = new JMenuItem("Text");
        // change the text font size
        text.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO implemen text insert
                System.out.println("insert text");
                shapeSelected = Windows.TEXT;
            }
        });
        insert.add(text);

        menuBar.add(colors);
        menuBar.add(shapes);
        menuBar.add(insert);

        optionbar.add(menuBar);

        add(optionbar);

        // separator
        JSeparator s = new JSeparator(SwingConstants.HORIZONTAL);
        s.setPreferredSize(new Dimension(Windows.WIDTH, 1));
        add(s);

        // canvas
        g = new Canvas(Windows.WIDTH, Windows.HEIGHT, this);
        add(g);

        pack();
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    public String getSelectedShape() {
        return this.shapeSelected;
    }

    public Color getSelectedColor() {
        return this.colorSelected;
    }
}