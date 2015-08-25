package contaweb.modelo.bean;

import java.util.HashMap;

import org.apache.ibatis.type.Alias;
@Alias("mapTest")
public class MapTest<K,V> extends HashMap<K, V>{

}
