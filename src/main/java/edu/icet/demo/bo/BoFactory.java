package edu.icet.demo.bo;

import edu.icet.demo.bo.custom.impl.CustomerBoImpl;
import edu.icet.demo.bo.custom.impl.ItemBoImpl;
import edu.icet.demo.bo.custom.impl.OrderBoImpl;
import edu.icet.demo.bo.custom.impl.OrderDetailBoImpl;
import edu.icet.demo.util.BoType;

public class BoFactory {
    private static BoFactory instance;

    private BoFactory() {
    }

    public static BoFactory getInstance() {
        return instance != null ? instance : (instance = new BoFactory());
    }

    public <T extends SuperBo> T getBo(BoType type) {
        switch (type) {
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ORDER: return (T) new OrderBoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
            case ITEM:return (T) new ItemBoImpl();
        }
        return null;
    }
}
