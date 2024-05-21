package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;
import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.model.Item;

import java.util.List;

public interface ItemBo extends SuperBo {
    Item getItem(String id);

    List<Item> getAllItems();

    int getTableRowCount();

    ItemEntity getTableLastId();

    void addItem(Item item);

    void updateItem(Item item);

    void deleteItem(Item item);
}
