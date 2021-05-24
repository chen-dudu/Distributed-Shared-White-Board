package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Canvas extends JPanel {

    // private List<MyObj> objs = new ArrayList<>();
    private List<MyObj> objs;
    private String shapeToDraw;
    private Color colorToUse;
    private Shape drawing = null;
    private JTextField text = null;

    public Canvas(int width, int height, Windows w, List<MyObj> canvasObjs) {
        setPreferredSize(new Dimension(width, height));

        this.objs = canvasObjs;
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                shapeToDraw = w.getSelectedShape();
                colorToUse = w.getSelectedColor();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (shapeToDraw == null) {
                    return;
                }
                MyObj toDraw = new MyObj();
                switch (shapeToDraw) {
                    case Windows.LINE:
                        drawing = new Line2D.Double(e.getPoint(), e.getPoint());
                        break;
                    case Windows.CIRCLE:
                        drawing = new Ellipse2D.Double(e.getX(), e.getY(), 0, 0);
                        break;
                    case Windows.OVAL:
                        drawing = new Ellipse2D.Double(e.getX(), e.getY(), 0, 0);
                        break;
                    case Windows.REC:
                        drawing = new Rectangle2D.Double(e.getX(), e.getY(), 0, 0);
                        break;
                    case Windows.TEXT:
                        String inp = JOptionPane.showInputDialog("Enter text to be inserted", "");
                        if (inp == null) {
                            // user clicked on cancel
                            inp = "";
                        }
                        toDraw.setTextLocation(e.getPoint());
                        toDraw.setText(inp);
                        break;
                    default:
                        System.err.println("Unsupported shape selected");
                }
                toDraw.setColor(colorToUse);
                toDraw.setShape(drawing);
                objs.add(toDraw);
                w.notify(objs);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (shapeToDraw == null) {
                    return;
                }
                switch (shapeToDraw) {
                    case Windows.LINE:
                        Line2D currentLine = (Line2D) drawing;
                        currentLine.setLine(currentLine.getP1(), e.getPoint());
                        break;
                    case Windows.CIRCLE:
                        Ellipse2D currentCircle = (Ellipse2D) drawing;
                        double startXCircle = currentCircle.getX();
                        double radius = Math.abs(startXCircle - e.getX());
                        currentCircle.setFrame(currentCircle.getX(), currentCircle.getY(), radius, radius);
                        break;
                    case Windows.OVAL:
                        Ellipse2D currentOval = (Ellipse2D) drawing;
                        double startXOval = currentOval.getX();
                        double startYOval = currentOval.getY();
                        double widthOval = Math.abs(startXOval - e.getX());
                        double heightOva = Math.abs(startYOval - e.getY());
                        currentOval.setFrame(currentOval.getX(), currentOval.getY(), widthOval, heightOva);
                        break;
                    case Windows.REC:
                        Rectangle2D currentRec = (Rectangle2D) drawing;
                        double startXRec = currentRec.getX();
                        double startYRec = currentRec.getY();
                        double widthRec = Math.abs(startXRec - e.getX());
                        double heightRec = Math.abs(startYRec - e.getY());
                        currentRec.setRect(currentRec.getX(), currentRec.getY(), widthRec, heightRec);
                        break;
                    default:
                        System.out.println("Unsupported shape");
                }
                w.notify(objs);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                drawing = null;
                repaint();
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // change the background color of the canvas
        setBackground(Color.WHITE);

        Graphics2D g2d = (Graphics2D) g;
        if (objs != null) {
            for (MyObj obj : objs) {
                // System.out.println(obj);
                String text = obj.getText();
                if (text == null) {
                    Shape shape = obj.getShape();
                    if (shape != null) {
                        g2d.setPaint(obj.getColor());
                        g2d.draw(shape);
                    }
                } else {
                    JLabel temp = new JLabel(text);
                    temp.setFont(new Font("", Font.PLAIN, 16));
                    temp.setForeground(obj.getColor());
                    Point location = obj.getTextLocation();
                    temp.setBounds(new Rectangle(new Point((int) location.getX(), (int) location.getY()), temp.getPreferredSize()));
                    this.add(temp);
                }
            }
        }
    }

    public List<MyObj> getObjs() {
        return objs;
    }

    public void setObjs(List<MyObj> objs) {
        this.objs = objs;
        repaint();
    }
}
