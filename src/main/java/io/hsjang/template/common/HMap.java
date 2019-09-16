package io.hsjang.template.common;

import java.util.HashMap;

public class HMap extends HashMap<String,Object> {

    private static final long serialVersionUID = 1L;
    
    public HMap(){
        super();
    }

    public HMap(String k, Object v){
        super();
        put(k,v);
        
    }

    public static HMap of(String k, Object v){
        return new HMap(k,v);
    }
}