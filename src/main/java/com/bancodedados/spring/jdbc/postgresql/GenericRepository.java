package com.bancodedados.spring.jdbc.postgresql;

import java.util.List;

public interface GenericRepository<T> {
  int save(T book);

  int update(T book);

  T findById(Long id);

  int deleteById(Long id);

  List<T> findAll();

  int deleteAll();
}
