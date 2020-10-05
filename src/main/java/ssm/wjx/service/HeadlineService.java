package ssm.wjx.service;

import ssm.wjx.entity.Headline;

import java.util.List;

public interface HeadlineService {
    String HEADLINE_KEY = "headline";
    int expire = 18000;
    List<Headline> getHeadlineList(Headline headline);
}
