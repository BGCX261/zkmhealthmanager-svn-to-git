package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import com.framework.res.MapToList;

public interface ListMapDao {
    public List getLisMaps(MapToList mapToList, Map<String,Object> params);
}
