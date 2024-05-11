package edu.icet.demo.boFactory;

public class BoFactory {
    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){
        if(instance==null){
            instance = new BoFactory();
        }
        return instance;
    }
}
