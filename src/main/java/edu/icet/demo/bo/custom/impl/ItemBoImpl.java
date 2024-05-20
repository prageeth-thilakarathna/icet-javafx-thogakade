package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.ItemDao;
import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.model.Item;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;

import java.sql.ResultSet;

public class ItemBoImpl implements ItemBo {

    private final ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public ResultSet getItem(String id) {
        return null;
    }

    @Override
    public ResultSet getAllItems() {
        return null;
    }

    @Override
    public ResultSet getTableRowCount() {
        return itemDao.count();
    }

    @Override
    public ResultSet getTableLastId() {
        return itemDao.findLast();
    }

    @Override
    public boolean addItem(Item item) {
        return itemDao.save(new ModelMapper().map(item, ItemEntity.class));
    }

    @Override
    public boolean updateItem(Item item) {
        return itemDao.update(new ModelMapper().map(item, ItemEntity.class));
    }

    @Override
    public boolean deleteItem(Item item) {
        return true;
    }
}
