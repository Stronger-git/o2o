package ssm.wjx.service;

import ssm.wjx.entity.Headline;

import java.util.List;

public interface HeadlineService {
    public static final String HEADLINE_KEY = "headline";
    List<Headline> getHeadlineList(Headline headline);
}
