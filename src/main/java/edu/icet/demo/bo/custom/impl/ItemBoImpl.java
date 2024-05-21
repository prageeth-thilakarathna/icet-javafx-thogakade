package edu.icet.demo.bo.custom.impl;

import edu.icet.demo.bo.custom.ItemBo;
import edu.icet.demo.dao.DaoFactory;
import edu.icet.demo.dao.custom.ItemDao;
import edu.icet.demo.entity.ItemEntity;
import edu.icet.demo.model.Item;
import edu.icet.demo.util.DaoType;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class ItemBoImpl implements ItemBo {

    private final ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public Item getItem(String id) {
        return new ModelMapper().map(itemDao.get(id), Item.class);
    }

    @Override
    public List<Item> getAllItems() {
        return new ModelMapper().map(itemDao.getAll(), new TypeToken<List<Item>>() {}.getType());
    }

    @Override
    public int getTableRowCount() {
        return itemDao.count();
    }

    @Override
    public ItemEntity getTableLastId() {
        return itemDao.findLast();
    }

    @Override
    public void addItem(Item item) {
        itemDao.save(new ModelMapper().map(item, ItemEntity.class));
    }

    @Override
    public void updateItem(Item item) {
        itemDao.update(new ModelMapper().map(item, ItemEntity.class));
    }

    @Override
    public void deleteItem(Item item) {
        itemDao.delete(new ModelMapper().map(item, ItemEntity.class));
    }
}
