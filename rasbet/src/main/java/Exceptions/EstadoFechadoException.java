package Exceptions;

public class EstadoFechadoException extends Exception{
    public EstadoFechadoException(){
        super();
    }

    public EstadoFechadoException(String s){
        super(s);
    }
}
