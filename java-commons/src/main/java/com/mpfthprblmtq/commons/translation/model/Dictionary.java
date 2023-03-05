package com.mpfthprblmtq.commons.translation.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Dictionary {
    List<String> subDictionaryPaths;
    Map<String, String> dictionaryValues;
}
