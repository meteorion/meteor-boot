package pers.meteor.shared.mapper.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import pers.meteor.shared.model.config.entity.Config;

/**
 * 系统配置 访问层
 *
 * @author Theo
 * @since 2024-7-29 11:41:04
 */
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}
