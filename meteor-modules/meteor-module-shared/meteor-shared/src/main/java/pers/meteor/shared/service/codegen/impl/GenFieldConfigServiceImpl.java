package pers.meteor.shared.service.codegen.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.shared.mapper.codegen.GenFieldConfigMapper;
import pers.meteor.shared.model.entity.codegen.GenFieldConfig;
import pers.meteor.shared.service.codegen.GenFieldConfigService;

/**
 * 代码生成字段配置服务实现类
 *
 * @author Ray
 * @since 2.10.0
 */
@Service
@RequiredArgsConstructor
public class GenFieldConfigServiceImpl extends ServiceImpl<GenFieldConfigMapper, GenFieldConfig> implements GenFieldConfigService {

}
