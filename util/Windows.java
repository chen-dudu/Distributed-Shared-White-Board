package util;

import client.Client;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Windows extends JFrame {

    public static final String LINE = "line";
    public static final String CIRCLE = "circle";
    public static final String OVAL = "oval";
    public static final String REC = "rectangle";
    public static final String TEXT = "text";

    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    public static final int OPTION_BAR_HEIGHT = 40;
    public static final int SPACE = 100;

    private String shapeSelected = null;
    private Color colorSelected = Color.black;

    private Canvas g = null;
    private UserList userList = null;
    private ChatBox msgList = null;
    private Client c;

    public Windows(List<MyObj> canvasObjs, Client c, String position) {
        super("White Board");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (!c.getServerStatus()) {
            JOptionPane.showMessageDialog(null, "Server is not available at the moment, please try again later");
            System.exit(0);
        }

        if (position.equals("guest")) {
            if (!c.getBoardStatus()) {
                JOptionPane.showMessageDialog(null, "White board is not ready for sharing, please try again later");
                System.exit(0);
            }
        }

        while(true) {
            String name = JOptionPane.showInputDialog("Please enter your user name for the white board", "");
            if (name != null) {
                boolean successful = false;
                switch (position) {
                    case "manager":
                        successful = c.create(name);
                        break;
                    case "guest":
                        successful = c.join(name);
                        break;
                    default:
                        System.err.println("Unknown error occurred");
                }
                if (successful) {
                    break;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please use another name and try again");
                }
            }
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (c.isKickedOut()) {
                    JOptionPane.showMessageDialog(null, "You have been kicked out by the whiteboard manager.");
                    System.exit(0);
                }
            }
        }, 0, 500);

        this.c = c;
        // option bar on the top of the canvas
        JPanel optionbar = new JPanel();
        optionbar.setPreferredSize(new Dimension(Windows.WIDTH + 3 * Windows.SPACE, Windows.OPTION_BAR_HEIGHT));

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        // change the text font size
        file.setFont(new Font("", Font.PLAIN, 18));

        JMenuItem itemNew = new JMenuItem("New");
        // change the text font size
        itemNew.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        itemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MyObj> temp = new ArrayList<>();
                g.setObjs(temp);
                c.notifyNewDraw(temp);
            }
        });
        file.add(itemNew);

        JMenuItem itemOpen = new JMenuItem("Open");
        // change the text font size
        itemOpen.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        itemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter the name of the file to be opened", "");
                try {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
                    List<MyObj> objs = (List<MyObj>) is.readObject();
                    is.close();
                    g.setObjs(objs);
                    c.notifyNewDraw(objs);
                }
                catch (FileNotFoundException fex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Did not find file with name " + fileName, "Alert", JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Failed to open the file " + fileName, "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        file.add(itemOpen);

        JMenuItem itemSave = new JMenuItem("Save");
        // change the text font size
        itemSave.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = "out.txt";
                try {
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
                    os.writeObject(g.getObjs());
                    os.flush();
                    os.close();
                    JOptionPane.showMessageDialog(null, "Saved successfully");
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Failed to save white board to disk", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        file.add(itemSave);

        JMenuItem itemSaveAs = new JMenuItem("Save As...");
        // change the text font size
        itemSaveAs.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        itemSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog("Enter the name of the file to be saved", "");
                try {
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
                    os.writeObject(g.getObjs());
                    os.flush();
                    os.close();
                    JOptionPane.showMessageDialog(null, "Saved successfully");
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Failed to save white board to disk", "Alert", JOptionPane.WARNING_MESSAGE);

                }
            }
        });
        file.add(itemSaveAs);

        JMenuItem itemClose = new JMenuItem("Close");
        // change the text font size
        itemClose.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        itemClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.notifyClose();
                System.exit(0);
            }
        });
        file.add(itemClose);

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

                String inputtedRGB = JOptionPane.showInputDialog("Enter RGB value of your favourite color, separated by space", "");
                if (inputtedRGB != null) {
                    String[] vals = inputtedRGB.split(" ");
                    if (vals.length == 3) {
                        boolean invalidInput = false;
                        String rValueString = vals[0];
                        int rValue;
                        try {
                            rValue = Integer.parseInt(rValueString);
                        } catch (NumberFormatException re) {
                            rValue = -1;
                            invalidInput = true;
                        }
                        if (rValueString.length() == 0 || rValue < 0 || rValue > 255) {
                            invalidInput = true;
                        }
                        String gValueString = vals[1];
                        int gValue;
                        try {
                            gValue = Integer.parseInt(gValueString);
                        } catch (NumberFormatException ge) {
                            gValue = -1;
                            invalidInput = true;
                        }
                        if (gValueString.length() == 0 || gValue < 0 || gValue > 255) {
                            invalidInput = true;
                        }
                        String bValueString = vals[2];
                        int bValue;
                        try {
                            bValue = Integer.parseInt(bValueString);
                        } catch (NumberFormatException be) {
                            bValue = -1;
                            invalidInput = true;
                        }
                        if (bValueString.length() == 0 || bValue < 0 || bValue > 255) {
                            invalidInput = true;
                        }
                        if (!invalidInput) {
                            colorSelected = new Color(rValue, gValue, bValue);
                        } else {
                            JOptionPane.showMessageDialog(getContentPane(), "Invalid RGB value provided", "Alert", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "Invalid RGB value provided", "Alert", JOptionPane.WARNING_MESSAGE);
                    }
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
                shapeSelected = Windows.TEXT;
            }
        });
        insert.add(text);

        JMenu manage = new JMenu("Manage");
        // change the text font size
        manage.setFont(new Font("", Font.PLAIN, 18));

        JMenuItem chat = new JMenuItem("Chat");
        // change the text font size
        chat.setFont(new Font("", Font.PLAIN, 18));
        // click listener
        chat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = JOptionPane.showInputDialog("Enter your chat message", "");
                if (msg != null && msg.length() > 0) {
                    c.send(msg);
                }
            }
        });
        manage.add(chat);

        if (position.equals("guest")) {
            JMenuItem leave = new JMenuItem("Leave");
            // change the text font size
            leave.setFont(new Font("", Font.PLAIN, 18));
            // click listener
            leave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.notifyLeave();
                    System.exit(0);
                }
            });
            manage.add(leave);
        }

        if (position.equals("manager")) {
            JMenuItem kick = new JMenuItem("Kick out");
            // change the text font size
            kick.setFont(new Font("", Font.PLAIN, 18));
            // click listener
            kick.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = JOptionPane.showInputDialog("Enter the name of the user to be kicked out", "");
                    if (name != null && name.length() > 0) {
                        String result;
                        if (c.kickOut(name)) {
                            result = "User " + name + " has been successfully kicked out";
                        }
                        else {
                            result = "Kick-out process failed, please try again";
                        }
                        JOptionPane.showMessageDialog(null, result);
                    }
                }
            });
            manage.add(kick);
        }

        if (position.equals("manager")) {
            menuBar.add(file);
        }
        menuBar.add(colors);
        menuBar.add(shapes);
        menuBar.add(insert);
        menuBar.add(manage);

        optionbar.add(menuBar);

        add(optionbar);

        // separator
        JSeparator s = new JSeparator(SwingConstants.HORIZONTAL);
        s.setPreferredSize(new Dimension(Windows.WIDTH + 3 * Windows.SPACE, 1));
        add(s);

        // content section
        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(Windows.WIDTH + 3 * Windows.SPACE, Windows.HEIGHT));

        // canvas
        g = new Canvas(Windows.WIDTH, Windows.HEIGHT, this, canvasObjs);
        content.add(g);

        JSeparator s1 = new JSeparator(SwingConstants.VERTICAL);
        s1.setPreferredSize(new Dimension(1, Windows.HEIGHT));
        content.add(s1);

        userList = new UserList(Windows.SPACE, Windows.HEIGHT);
        content.add(userList);

        JSeparator s2 = new JSeparator(SwingConstants.VERTICAL);
        s2.setPreferredSize(new Dimension(1, Windows.HEIGHT));
        content.add(s2);

        msgList = new ChatBox(Windows.SPACE, Windows.HEIGHT);
        content.add(msgList);

        add(content);

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

    public void notify(List<MyObj> objs) {
        this.c.notifyNewDraw(objs);
    }

    public void updateCanvas(List<MyObj> objs) {
        if (g != null) {
            g.setObjs(objs);
        }
    }

    public void updateUsers(List<String> users) {
        if (userList != null) {
            userList.setUsers(users);
        }
    }

    public void updateMsg(List<String> msgs) {
        if (msgList != null) {
            msgList.setMsg(msgs);
        }
    }
}