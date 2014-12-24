package com.idap.dataprocess.definition.dao;

import java.util.List;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;

public interface DataMatchAttrDao {
    public List<DataDefinitionAttr> findMatchAttr();
}
