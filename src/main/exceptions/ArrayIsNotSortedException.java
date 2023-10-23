package exceptions;


import functions.ArrayTabulatedFunction;

public class ArrayIsNotSortedException extends RuntimeException {
    public ArrayIsNotSortedException(){}
    public ArrayIsNotSortedException(String message){
        super(message);
    }
}
