package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.model.Item;

import java.sql.ResultSet;

public interface ItemBo extends SuperBo {
    ResultSet getItem(String id);

    ResultSet getAllItems();

    ResultSet getTableRowCount();

    ResultSet getTableLastId();

    boolean addItem(Item item);

    boolean updateItem(Item item);

    boolean deleteItem(Item item);
}
