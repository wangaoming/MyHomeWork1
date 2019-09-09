package com.example.myhomework1.dao;

import com.example.myhomework1.entity.Insert;


import java.util.List;

public interface InsertDao {
    List <Insert> selectAll();
    void insert(Insert insert);
    void update(Insert insert);
    void delete(String name);
}
