package pers.meteor.system.mapper.notice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.meteor.system.model.notice.bo.NoticeBO;
import pers.meteor.system.model.notice.entity.Notice;
import pers.meteor.system.model.notice.query.NoticePageQuery;
import pers.meteor.system.model.notice.vo.NoticePageVO;

/**
 * 通知公告Mapper接口
 *
 * @author youlaitech
 * @since 2024-08-27 10:31
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 获取通知公告分页数据
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return 通知公告分页数据
     */
    Page<NoticeBO> getNoticePage(Page<NoticePageVO> page, NoticePageQuery queryParams);

    /**
     * 获取阅读时通知公告详情
     *
     * @param id 通知公告ID
     * @return 通知公告详情
     */
    NoticeBO getNoticeDetail(@Param("id") Long id);
}
