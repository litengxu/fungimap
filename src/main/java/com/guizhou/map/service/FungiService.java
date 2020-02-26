package com.guizhou.map.service;

import com.guizhou.map.domain.Fungi;

import java.util.*;

public interface FungiService {
    List<Fungi> getAllFungi();
    List<Fungi> selectByname(String name);
}
