package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.entity.ItemEntity;

public interface ItemDao extends CrudDao<ItemEntity> {
    int count();

    ItemEntity findLast();

    void modifyItem(ItemEntity itemEntity);
}
