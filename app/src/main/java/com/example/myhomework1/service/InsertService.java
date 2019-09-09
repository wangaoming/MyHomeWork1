package com.example.myhomework1.service;



import com.example.myhomework1.entity.Insert;

import java.util.List;

public interface InsertService {
    List<Insert> getAllInserts();
    public void insert(Insert insert);
    void delete(String name);
    void modify(Insert insert);


    void modify(int parseInt);
}
