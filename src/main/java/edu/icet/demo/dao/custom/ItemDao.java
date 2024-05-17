package edu.icet.demo.dao.custom;

import edu.icet.demo.dao.CrudDao;
import edu.icet.demo.entity.ItemEntity;

import java.sql.ResultSet;

public interface ItemDao extends CrudDao<ItemEntity> {
    ResultSet count();

    ResultSet findLast();
}
