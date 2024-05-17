package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.ItemDao;
import edu.icet.demo.util.DaoType;

import java.sql.ResultSet;

public class ItemBoImpl implements ItemBo {

    private final ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public ResultSet getItem(String id) {
        return itemDao.findById(id);
    }

    @Override
    public ResultSet getAllItems() {
        return itemDao.findAll();
    }
}
