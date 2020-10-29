package ssm.wjx.service;

import ssm.wjx.entity.Area;

import java.util.List;

public interface AreaService {
    String AREAS_KEY = "areas";
    int expire = 18000;
    List<Area> getAreaList();
}
