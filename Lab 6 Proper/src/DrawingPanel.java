import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawingPanel extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener, colorChosenListener {

    ArrayList<Figure> figures = new ArrayList<>();
    ArrayList<Integer> polygonPointsX = new ArrayList<>();
    ArrayList<Integer> polygonPointsY = new ArrayList<>();
    private Point tempPoint, tempPoint2, tempPoint3, tempPoint4;
    private Color tempColor = Color.BLACK;
    private Figure bestFigure = null;
    private int mode = 0, counter = 0;

    myColorsMenu myColors = new myColorsMenu();

    DrawingPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        myColors.addColorListener(this);

        add(myColors);
    }

    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        switch (mode) {
            case 1:
                if (counter == 1 && tempPoint != null) {
                    g.setColor(Color.GREEN);
                    int temp = (int) (2 * tempPoint.distance(tempPoint2));
                    g.drawOval(tempPoint.x - temp / 2, tempPoint.y - temp / 2, temp, temp);
                } else if (counter == 0 && tempPoint != null) {
                    mode = 0;
                    figures.add(new Figure(tempPoint, 2 * tempPoint.distance(tempPoint3)));
                }

                break;
            case 2:
                int tempx, tempy;
                if (counter == 1 && tempPoint != null) {
                    g.setColor(Color.GREEN);
                    tempx = Math.abs(tempPoint.x - tempPoint2.x);
                    tempy = Math.abs(tempPoint.y - tempPoint2.y);
                    g.drawRect(Math.min(tempPoint.x, tempPoint2.x), Math.min(tempPoint.y, tempPoint2.y), tempx, tempy);
                } else if (counter == 0 && tempPoint != null) {
                    tempx = Math.abs(tempPoint.x - tempPoint3.x);
                    tempy = Math.abs(tempPoint.y - tempPoint3.y);
                    mode = 0;
                    figures.add(new Figure(new Point(Math.min(tempPoint.x, tempPoint3.x),
                            Math.min(tempPoint.y, tempPoint3.y)), tempx, tempy));
                }

                break;
            case 3:
                if (counter == 1 && tempPoint != null) {
                    g.setColor(Color.GREEN);
                    if (!(polygonPointsX.contains(tempPoint.x) && polygonPointsY.contains(tempPoint.y))) {
                        polygonPointsX.add(tempPoint.x);
                        polygonPointsY.add(tempPoint.y);
                    }
                    int[] pX = convertIntegers(polygonPointsX);
                    int[] pY = convertIntegers(polygonPointsY);
                    g.drawPolyline(pX, pY, pX.length);
                    g.drawLine(tempPoint.x, tempPoint.y, tempPoint2.x, tempPoint2.y);
                } else if (counter == 0 && tempPoint != null) {
                    mode = 0;
                    figures.add(new Figure(polygonPointsX, polygonPointsY));
                    polygonPointsX = new ArrayList<>();
                    polygonPointsY = new ArrayList<>();
                }
                break;
        }

        for (Figure i : figures) {
            if (i.type == 1) {
                int rad = (int) i.radius;
                if (i.fill) {
                    g.setColor(i.fillColor);
                    g.fillOval(i.mainPoint.x - (rad / 2), i.mainPoint.y - (rad / 2), rad, rad);
                }
                g.setColor(i.color);
                g.drawOval(i.mainPoint.x - rad / 2, i.mainPoint.y - rad / 2, rad, rad);
            } else if (i.type == 2) {
                g.setColor(i.color);
                g.drawRect(i.mainPoint.x, i.mainPoint.y, (int) i.distX, (int) i.distY);
                if (i.fill) {
                    g.setColor(i.fillColor);
                    g.fillRect(i.mainPoint.x + 1, i.mainPoint.y + 1, (int) i.distX - 1, (int) i.distY - 1);
                }
            } else if (i.type == 3) {
                int[] pX = convertIntegers(i.pointsX);
                int[] pY = convertIntegers(i.pointsY);

                if (i.fill) {
                    g.setColor(i.fillColor);
                    g.fillPolygon(pX,pY,pX.length);
                }
                g.setColor(i.color);
                g.drawPolygon(pX,pY,pX.length);

            }
        }
        myColors.repaint();
    }

    public void colorWasChosen(colorChosenEvent evt) {
        if (myColors.getClr() == null) {
            bestFigure.fill = false;
            myColors.setVisible(false);
        } else {
            bestFigure.fill = true;
            bestFigure.fillColor = myColors.getClr();
            myColors.setVisible(false);
            myColors.setClr(null);
        }
        repaint();
    }

    public void mouseReleased(MouseEvent me) {
        if (counter == 0 && me.getButton() == 1 && mode != 0) {
            tempPoint = me.getPoint();
            tempPoint2 = me.getPoint();
            tempPoint4 = me.getPoint();
            counter = 1;
        } else if (counter == 1 && me.getButton() == 1 && mode != 0) {
            tempPoint3 = me.getPoint();
            counter = 0;
            if (mode == 3 && tempPoint4.distance(tempPoint3) > 5) {
                counter = 1;
                tempPoint = me.getPoint();
            }
        } else if (me.getButton() == 3 && mode != 0) {
            cancel();
        } else if (me.getButton() == 3 && mode == 0 && bestFigure != null && !myColors.isVisible()) {
            myColors.setLocation(me.getPoint());
            myColors.setVisible(true);
        }
        repaint();
    }

    public void mouseMoved(MouseEvent me) {
        if (counter == 1) {
            tempPoint2 = me.getPoint();
            repaint();
        }
    }

    public void mousePressed(MouseEvent me) {
        if (me.getButton() == 1 && mode == 0) {
            tempPoint = me.getPoint();
            if (bestFigure == null) {
                figureChoosing();
            } else {
                bestFigure.color = tempColor;
                bestFigure = null;
                figureChoosing();
            }
        }
    }

    public void mouseDragged(MouseEvent me) {
        if (mode == 0 && bestFigure != null) {
            if (bestFigure.type == 1 || bestFigure.type == 2) {
                tempPoint2 = me.getPoint();
                bestFigure.mainPoint.x += tempPoint2.x - tempPoint.x;
                bestFigure.mainPoint.y += tempPoint2.y - tempPoint.y;
                tempPoint = me.getPoint();
            } else if (bestFigure.type == 3){
                tempPoint2 = me.getPoint();
                for (int i = 0; i<bestFigure.pointsX.size(); i++){
                    bestFigure.pointsX.set(i, bestFigure.pointsX.get(i) + tempPoint2.x - tempPoint.x);
                    bestFigure.pointsY.set(i, bestFigure.pointsY.get(i) + tempPoint2.y - tempPoint.y);
                }
                tempPoint = me.getPoint();
            }
            repaint();
        }
    }

    public void mouseWheelMoved(MouseWheelEvent we) {
        double ratioX = 0;
        double ratioY = 0;
        double totalDist = 0;
        if (bestFigure.type == 1) {
            bestFigure.radius -= 4 * we.getUnitsToScroll();
            if (bestFigure.radius < 4) {
                bestFigure.radius = 4;
            }
        } else if (bestFigure.type == 2) {
            totalDist = bestFigure.distX + bestFigure.distY;
            ratioX = bestFigure.distX / totalDist;
            ratioY = bestFigure.distY / totalDist;
            bestFigure.distX -= 6 * ratioX * we.getUnitsToScroll();
            bestFigure.distY -= 6 * ratioY * we.getUnitsToScroll();

            double tempx = 3 * ratioX * we.getUnitsToScroll();
            double tempy = 3 * ratioY * we.getUnitsToScroll();
            if (bestFigure.distY < 4) {
                tempx = 0;
                tempy = 0;
                bestFigure.distX = 4 * ratioX / ratioY;
                bestFigure.distY = 4;
            } else if (bestFigure.distX < 4) {
                tempx = 0;
                tempy = 0;
                bestFigure.distY = 4 * ratioY / ratioX;
                bestFigure.distX = 4;
            }
            bestFigure.mainPoint.x+=tempx;
            bestFigure.mainPoint.y+=tempy;
        } else if (bestFigure.type == 3) {
            int x1 = bestFigure.massCentre.x;
            int y1 = bestFigure.massCentre.y;
            for (int i = 0; i<bestFigure.pointsX.size(); i++){
                int x = bestFigure.pointsX.get(i);
                int y = bestFigure.pointsY.get(i);
                int deltaX = x - x1;
                int deltaY = y - y1;
                int sign = 1;
                if (we.getUnitsToScroll() > 0) {
                    bestFigure.pointsX.set(i, (int) (x1 + 1.02 * sign * deltaX));
                    bestFigure.pointsY.set(i, (int) (y1 + 1.02 * sign * deltaY));
                }else{
                    bestFigure.pointsX.set(i, (int) (x1 + 0.98 * sign * deltaX));
                    bestFigure.pointsY.set(i, (int) (y1 + 0.98 * sign * deltaY));
                }
            }
            bestFigure.setMassCentre();
        }
        repaint();
    }

    private void figureChoosing() {
        bestFigure = null;
        double bestDistance = 0;
        double tempDist = 1000;
        for (int k = 0; k<figures.size(); k++) {
            Figure i = figures.get(k);
            if (i.type == 1) {
                tempDist = Math.abs(tempPoint.distance(i.mainPoint) - i.radius / 2);
            } else if (i.type == 2) {
                double distX1=7;
                double distX2=7;
                double distY1=7;
                double distY2=7;
                if (tempPoint.y<i.mainPoint.y+i.distY+3 && tempPoint.y>i.mainPoint.y-3) {
                    distX1 = Math.abs(tempPoint.x - i.mainPoint.x);
                    distX2 = Math.abs(tempPoint.x - (i.mainPoint.x + i.distX));
                }
                if (tempPoint.x<i.mainPoint.x+i.distX+3 && tempPoint.x>i.mainPoint.x-3) {
                    distY1 = Math.abs(tempPoint.y - i.mainPoint.y);
                    distY2 = Math.abs(tempPoint.y - (i.mainPoint.y + i.distY));
                }
                tempDist = Math.min(Math.min(distX1, distX2), Math.min(distY1, distY2));
            } else if (i.type == 3) {
                for (int j = 0; j<i.pointsY.size(); j++){
                    double a, b, slope, rest, tempDist2=7;
                    if (j == i.pointsY.size()-1){
                        a = i.pointsY.get(0)-i.pointsY.get(j);
                        b = i.pointsX.get(0)-i.pointsX.get(j);
                        slope = a/b;
                        rest = (-1)*slope*i.pointsX.get(j)+i.pointsY.get(j);
                        if (tempPoint.y > Math.min(i.pointsY.get(0), i.pointsY.get(j))-3 &&
                                tempPoint.y < Math.max(i.pointsY.get(0), i.pointsY.get(j))+3 &&
                                tempPoint.x > Math.min(i.pointsX.get(0), i.pointsX.get(j))-3 &&
                                tempPoint.x < Math.max(i.pointsX.get(0), i.pointsX.get(j))+3) {
                            tempDist2 = Math.abs((-1) * slope * tempPoint.x + tempPoint.y - rest) / Math.sqrt(1 + Math.pow(slope, 2));
                        }
                    } else {
                        a = i.pointsY.get(j + 1) - i.pointsY.get(j);
                        b = i.pointsX.get(j + 1) - i.pointsX.get(j);
                        slope = a / b;
                        rest = (-1) * slope * i.pointsX.get(j) + i.pointsY.get(j);
                        if (tempPoint.y > Math.min(i.pointsY.get(j+1), i.pointsY.get(j))-3 &&
                                tempPoint.y < Math.max(i.pointsY.get(j+1), i.pointsY.get(j))+3 &&
                                tempPoint.x > Math.min(i.pointsX.get(j+1), i.pointsX.get(j))-3 &&
                                tempPoint.x < Math.max(i.pointsX.get(j+1), i.pointsX.get(j))+3) {
                            tempDist2 = Math.abs((-1) * slope * tempPoint.x + tempPoint.y - rest) / Math.sqrt(1 + Math.pow(slope, 2));
                        }
                    }
                    if(tempDist2<tempDist){
                        tempDist = tempDist2;
                    }
                }
            }
            if (tempDist<5) {
                if (bestFigure == null) {
                    bestFigure = i;
                    bestDistance = tempDist;
                    tempColor = bestFigure.color;
                    bestFigure.color = Color.RED;

                    figures.remove(bestFigure);
                    figures.add(bestFigure);

                } else if (tempDist < bestDistance) {
                    bestFigure = i;
                    tempColor = bestFigure.color;
                    bestFigure.color = Color.RED;
                    bestDistance = tempDist;

                    figures.remove(bestFigure);
                    figures.add(bestFigure);
                }
            }
        }
        repaint();
    }



    public void mouseClicked(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    void setMode(int a) {
        mode = a;
    }

    void reset() {counter = 0;
        mode = 0;
        tempPoint = null;
        tempPoint2 = null;
        tempPoint3 = null;
        tempPoint4 = null;
        bestFigure = null;
        polygonPointsX = new ArrayList<>();
        polygonPointsY = new ArrayList<>();
        figures = new ArrayList<>();
        repaint();
    }

    void cancel() {
        counter = 0;
        mode = 0;
        tempPoint = null;
        tempPoint2 = null;
        tempPoint3 = null;
        tempPoint4 = null;
        polygonPointsX = new ArrayList<>();
        polygonPointsY = new ArrayList<>();
        myColors.setVisible(false);
        if (bestFigure != null) {
            bestFigure.color = tempColor;
        }
        bestFigure = null;
        repaint();
    }

    private int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }

}
