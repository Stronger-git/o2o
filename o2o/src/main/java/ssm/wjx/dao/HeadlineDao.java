package ssm.wjx.dao;

import ssm.wjx.entity.Headline;

import java.util.List;

public interface HeadlineDao {
    List<Headline> selectByHeadline(Headline headline);
}
