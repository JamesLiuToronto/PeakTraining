package com.peak.training.common.utility;

import java.util.List;

public class CollectionUtility {

    public static boolean checkNullOrEmpty(List<?> list){
        if (list == null) return true ;
        if (list.size() == 0) return true ;
        return false ;
    }
}
