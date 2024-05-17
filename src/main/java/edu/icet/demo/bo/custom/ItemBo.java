package edu.icet.demo.bo.custom;

import edu.icet.demo.bo.SuperBo;

import java.sql.ResultSet;

public interface ItemBo extends SuperBo {
    ResultSet getItem(String id);

    ResultSet getAllItems();
}
