package util;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserList extends JPanel {

    private List<String> users;

    private int width;
    private int height;

    private int interval = 20;
    private int startLocationX;
    private int startLocationY;

    private JPanel list;

    public UserList(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
        JLabel title = new JLabel("Users");
        title.setFont(new Font("", Font.PLAIN, 18));
        add(title);

        list = new JPanel();
        add(list);

        this.startLocationX = (int) title.getLocation().getX();
        this.startLocationY = (int) title.getLocation().getY();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.users != null) {
            list.removeAll();
            int count = 1;
            for (String user: users) {
                JLabel temp = new JLabel(user);
                temp.setFont(new Font("", Font.PLAIN, 18));
                temp.setBounds(this.startLocationX, this.startLocationY + count * interval, this.width, this.interval);
                list.add(temp);
                count++;
            }
        }
    }

    public void setUsers(List<String> users) {
        this.users = users;
        repaint();
    }
}
