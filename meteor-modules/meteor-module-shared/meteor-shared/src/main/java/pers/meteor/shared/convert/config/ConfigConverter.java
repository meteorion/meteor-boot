package pers.meteor.shared.convert.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import pers.meteor.shared.model.config.entity.Config;
import pers.meteor.shared.model.config.form.ConfigForm;
import pers.meteor.shared.model.config.vo.ConfigVO;

/**
 * 系统配置对象转换器
 *
 * @author Theo
 * @since 2024-7-29 11:42:49
 */
@Mapper(componentModel = "spring")
public interface ConfigConverter {

    Page<ConfigVO> toPageVo(Page<Config> page);

    Config toEntity(ConfigForm configForm);

    ConfigForm toForm(Config entity);
}
