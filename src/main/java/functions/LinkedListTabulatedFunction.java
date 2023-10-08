package functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable
{
    static class Node {
        public Node next, prev;
        public double x, y;

        @Override
        public String toString() {
            return "(" + x + "; " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            return (o.getClass() == this.getClass() && ((((Node)o).x == x)
                    && (((Node)o).y == y)));
        }

        @Override
        public int hashCode() {
            int hash = 16;
            int realX = (int)x;
            double floatX = (x - realX) * 1000;
            hash = hash * realX + 23 * (int)floatX;

            int realY = (int)y;
            double floatY = (y - realY) * 1000;
            hash = hash * realY + 23 * (int)floatY;
            return hash;
        }

        @Override
        public Object clone(){
            Node clone = new Node();
            clone.x = this.x; clone.y = this.y;
            clone.prev = this.prev; clone.next = this.next;
            return  clone;
        }
    }

    private Node head;

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
        for (int i = 0; i < xValues.length; i++){
            this.addNode(xValues[i],yValues[i]);
        }
    }
    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
        if(xFrom == xTo){
            double source_val = Math.round(source.apply(xFrom) * 1000.0) / 1000.0;
            while (count-- > 0) {
                this.addNode(xFrom, source_val);
            }
        }
        else{
            if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo; xTo = temp;
            }

            // Дискретизация
            int count_split_interval = count - 1;
            double split_interval = (xTo - xFrom) / count_split_interval;

            for (double curr_x = xFrom; count > 0; count--, curr_x += split_interval){
                this.addNode(curr_x, Math.round(source.apply(curr_x) * 1000.0) / 1000.0);
            }
        }
    }

    private Node getNode(int index){
        Node currNode = head;
        if(index > count / 2){
            index = count - index;
            while(index-- > 0){ currNode = currNode.prev; }
        }
        else{
            while(index-- > 0){ currNode = currNode.next; }
        }
        return currNode;
    }

    // Методы из TabulatedFunction
    public int getCount() {
        return count;
    }
    public double getX(int index){ return getNode(index).x; };
    public double getY(int index){ return getNode(index).y; };
    public void setY(int index, double value){ getNode(index).y = value; };
    public int indexOfX(double x){
        if (x < this.leftBound() || x > this.rightBound() ) return -1;
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
        if (x > this.rightBound()) return count-1;
        int index = 0;
        while (getX(index) < x){
            index++; }
        if (getX(index) == x) return index;
        else return --index;
    };


    private Node floorNodeOfX(double x){
        if (x > this.rightBound()) return head.prev;

        Node currNode = head;
        while(currNode.x < x) currNode = currNode.next;
        if (currNode == head || currNode.x == x) return currNode;
        else return currNode.prev;
    }


    protected double extrapolateLeft(double x){
        if (count == 1) return head.y;
        double newY = head.y + (((head.next).y - head.y)/((head.next).x - head.x)) * (x - head.x);
        newY = Math.round(newY * 1000.0) / 1000.0;

        this.addNode(x, newY);
        head = head.prev;
        return newY;
    };


    protected double extrapolateRight(double x){
        if (count == 1) return head.y;
        Node penultimate = (head.prev).prev;
        Node last = head.prev;
        double newY = penultimate.y + ((last.y - penultimate.y)/(last.x - penultimate.x))
                * (x - penultimate.x);
        newY = Math.round(newY * 1000.0) / 1000.0;

        this.addNode(x, newY);
        return newY;
    };


    protected double interpolate(double x, int floorIndex){

        if (count == 1) return head.y;
        double y_floor = getY(floorIndex);
        double x_floor = getX(floorIndex);
        double newY = y_floor + ((getY(floorIndex + 1) - y_floor)/
                (getX(floorIndex + 1) - x_floor)) * (x - x_floor);
        newY = Math.round(newY * 1000.0) / 1000.0;

        Node newNode = new Node();
        newNode.x = x; newNode.y = newY;
        newNode.prev = getNode(floorIndex);
        newNode.next = getNode(floorIndex + 1);
        getNode(floorIndex).next = newNode;
        getNode(floorIndex + 1).prev = newNode;
        count++;
        return newY;
    };


    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY){
        if (count == 1) return head.y;
        double newY = leftY + ((rightY - leftY)/ (rightX - leftX)) * (x - leftX);
        newY = Math.round(newY * 1000.0) / 1000.0;

        Node newNode = new Node();
        newNode.x = x; newNode.y = newY;
        newNode.prev = getNode(floorIndexOfX(x));
        newNode.next = getNode(floorIndexOfX(x) + 1);
        getNode(floorIndexOfX(x)).next = newNode;
        getNode(floorIndexOfX(x) + 1).prev = newNode;
        count++;
        return newY;
    };


    public double apply(double x) {
        double result;
        if (x < this.leftBound()) result = this.extrapolateLeft(x);
        else if(x > this.rightBound()) result = this.extrapolateRight(x);
        else {
            Node node = floorNodeOfX(x);
            if (node.x == x) result = node.y;
            else result = this.interpolate(x, node.x, (node.next).x,node.y,(node.next).y);
        }
        return result;
    }


    @Override
    public void insert(double x, double y) {
        if (count == 0) this.addNode(x,y);
        else {
            if (x < leftBound()){
                this.addNode(x,y);
                head = head.prev;
            }

            else if (x > rightBound()){
                this.addNode(x,y);
            }
            else{

                int ind = indexOfX(x);

                if(ind > 0){ this.setY(ind, y);}

                else {

                    Node newNode = new Node();

                    newNode.x = x;
                    newNode.y = y;

                    newNode.prev = floorNodeOfX(x);
                    newNode.next = floorNodeOfX(x).next;

                    floorNodeOfX(x).next = newNode;
                    (floorNodeOfX(x).next).prev = newNode;
                    count++;
                }
            }
        }
    }

    @Override
    public void remove(int index) {
        if (count > 0 ){

            if (index == 0) head = head.next;

            Node removeNode = getNode(index);
            (removeNode.prev).next = removeNode.next;
            (removeNode.next).prev = removeNode.prev;

            removeNode.prev = null;
            removeNode.next = null;

            count--;

        }
    }

    @Override
    public String toString() {
        String result = "{ ";
        Node curr = head;
        int temp = count;
        while(temp-- > 0){
            result += curr.toString() + ' ';
            curr = curr.next;
        }
        result += "}";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == LinkedListTabulatedFunction.Node.class){
            return this.equals((Node)o);
        }
        boolean result;
        if (o.getClass() == LinkedListTabulatedFunction.class){
            LinkedListTabulatedFunction obj = (LinkedListTabulatedFunction)o;
            if (count != obj.count) return false;
            int temp = count;
            Node curr = head; Node currObj = obj.head;
            while(temp-- > 0 && curr.equals(currObj)){
                curr = curr.next;
                currObj = currObj.next;
            }
            if (temp <= 0) return true; }
        else if (o.getClass() == ArrayTabulatedFunction.class){
            ArrayTabulatedFunction obj = (ArrayTabulatedFunction)o;
            if (count != obj.count) return false;
            Node curr = head;
            int temp = count;
            for(int i = 0; i < count && (curr.x == obj.getX(i) &&
                    curr.y == obj.getY(i)); i++)
            {
                curr = curr.next; temp--;
            }
            if (temp <= 0) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 24;
        Node curr = head;
        int temp = count;
        while(temp-- > 0){
            hash = hash + curr.hashCode();
        }
        return hash;
    }

    @Override
    public Object clone(){

        double[] arrayOfX = new double[count];
        double[] arrayOfY = new double[count];
        Node curr = head;
        for(int i = 0; i < count; i++){
            arrayOfX[i] = curr.x;
            arrayOfY[i] = curr.y;
            curr = curr.next;
        }

        LinkedListTabulatedFunction clone = new LinkedListTabulatedFunction(arrayOfX, arrayOfY);
        return clone;
    }
}