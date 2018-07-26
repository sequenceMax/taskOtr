package com.otr.testtask.task2.Repository;

import com.otr.testtask.task2.Model.Phones;

import java.util.List;

public interface UserRepository {

    List<Phones> getPhonesByFullName(String name);

}
