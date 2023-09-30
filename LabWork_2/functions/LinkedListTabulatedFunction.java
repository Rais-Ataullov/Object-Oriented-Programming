package functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction {

    class Node {

        public Node next;
        public Node prev;

        public double x;
        public double y;
    }

    protected int count = 0;
    private Node head = null;

    private void addNode(double x, double y){
        Node newNode = new Node();
        newNode.x = x;
        newNode.y = y;

        if (head == null){
            head = newNode;
            newNode.prev = newNode;
            newNode.next = newNode;
        }
        else {
            Node last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        count++;
    }
    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        for (int i = 0; i < xValues.length;i++){
            this.addNode(xValues[i],yValues[i]);
        }
    }
    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
        if(xFrom == xTo){
            while (count > 0) {
                this.addNode(xFrom, source.apply(xFrom));
                count--;
            }
        }
        else{
            if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
            }

            // Дискретизация
            int count_split_interval = count - 1;
            double split_interval = (xTo - xFrom) / count_split_interval;
            for (double curr_x = xFrom; count_split_interval > 0; count_split_interval--){
                this.addNode(curr_x, source.apply(curr_x));
                curr_x += split_interval;
            }
        }
    }
    private Node getNode(int index){
        Node currNode;
        if (index > count / 2){
            index = count - index;
            currNode = head.prev;
            while(index-- > 0){ currNode = currNode.prev; }
        }
        else{
            currNode = head;
            while(index-- > 0){ currNode = currNode.next;}
        }
        return currNode;
    }

    // Методы из TabulatedFunction
    public int getCount(){ return count; }
    public double getX(int index){ return getNode(index).x; };
    public double getY(int index){ return getNode(index).y; };
    public void setY(int index, double value){ getNode(index).y = value; };
    public int indexOfX(double x){
        int index = 0;
        while (index != count && getX(index) != x){
            index++;
        }
        if(index != count) return index;
        else return -1;
    };
    public int indexOfY(double y){
        int index = 0;
        while (index != count && getY(index) != y){
            index++;
        }
        if(index != count) return index;
        else return -1;
    };
    public double leftBound(){ return head.x; };
    public double rightBound(){ return (head.prev).x; };


    // Методы из AbstractTabulatedFunction
    protected int floorIndexOfX(double x){
        int index = 0;
        while (index != count && getX(index) != x){
            index++;
        }
        if(index != count) return index;
        else{
           int index_max = 0;
           while(index_max != count && getX(index_max) < x){ index_max++; }
           if (index_max == 0 || index_max == count) return index_max;
           return --index_max;
        }
    };

    protected Node floorNodeOfX(double x){
        int index = 0;
        while (index != count && getX(index) != x){
            index++;
        }
        if(index != count) return getNode(index);
        else{
            int index_max = 0;
            while(index_max != count && getX(index_max) < x){ index_max++; }
            if (index_max == 0) return head;
            if (index_max == count) return head.prev;
            return getNode(--index_max);
        }
    }

    protected double extrapolateLeft(double x){
        if (count == 1) return head.y;
        double newY = head.y + (((head.next).y - head.y)/((head.next).x - head.x))*(x - head.x);

        this.addNode(x, newY);
        head = head.prev;
        return newY;
    };

    protected double extrapolateRight(double x){
        if (count == 1) return head.y;
        double newY = head.y + (((head.next).y - head.y)/((head.next).x - head.x))*(x - head.x);

        this.addNode(x, newY);
        return newY;
    };

    protected double interpolate(double x, int floorIndex){

        if (count == 1) return head.y;
        double y_floor = getY(floorIndex);
        double x_floor = getX(floorIndex);
        double newY = y_floor + ((getY(floorIndex + 1) - y_floor)/
                (getX(floorIndex + 1) - x_floor))*(x - x_floor);

        Node newNode = new Node();
        newNode.x = x; newNode.y = newY;

        newNode.prev = getNode(floorIndex);
        newNode.next = getNode(floorIndex + 1);

        getNode(floorIndex).next = newNode;
        getNode(floorIndex + 1).prev = newNode;

        return newY;
    };

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY){
        if (count == 1) return head.y;
        double newY = leftY + ((rightY - leftY)/ (rightX - leftX)) * (x - leftX);

        Node newNode = new Node();
        newNode.x = x; newNode.y = newY;

        newNode.prev = getNode(floorIndexOfX(x));
        newNode.next = getNode(floorIndexOfX(x) + 1);

        getNode(floorIndexOfX(x)).next = newNode;
        getNode(floorIndexOfX(x) + 1).prev = newNode;

        return newY;
    };
    @Override
    public double apply(double x) {
        double result;
        if (x<head.x) result = this.extrapolateLeft(x);
        else if(x > (head.prev).x) result = this.extrapolateRight(x);
        else {
            Node node = floorNodeOfX(x);
            //int index = floorIndexOfX(x);
            if (node.x == x) result = node.y;
            //else result = this.interpolate(x, index);
            else result = this.interpolate(x, node.x, (node.next).x,node.y,(node.next).y);
        }
        return result;
    }
}
