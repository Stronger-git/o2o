package ssm.wjx.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ssm.wjx.BaseTest;
import ssm.wjx.entity.Headline;

/**
 * @author WuChangJian
 * @date 2020/3/13 14:51
 */
public class HeadlineTest extends BaseTest {
    @Autowired
    private HeadlineService headlineService;

    @Test
    public void testSelect() {
        Headline headline = new Headline();
        headline.setEnableStatus(1);
        headlineService.getHeadlineList(headline);
    }

}
