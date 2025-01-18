package pers.meteor.system.service.dict.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pers.meteor.common.pojo.Option;
import pers.meteor.system.convert.dict.DictDataConverter;
import pers.meteor.system.mapper.dict.DictDataMapper;
import pers.meteor.system.mode.dict.entity.DictData;
import pers.meteor.system.mode.dict.form.DictDataForm;
import pers.meteor.system.mode.dict.query.DictDataPageQuery;
import pers.meteor.system.mode.dict.vo.DictDataPageVO;
import pers.meteor.system.service.dict.DictDataService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据实现类
 *
 * @author haoxr
 * @since 2022/10/12
 */
@Service
@RequiredArgsConstructor
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    private final DictDataConverter dictDataConverter;

    /**
     * 获取字典数据分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<DictDataPageVO> getDictDataPage(DictDataPageQuery queryParams) {
        int pageNum = queryParams.getPageIndex();
        int pageSize = queryParams.getPageSize();
        Page<DictDataPageVO> page = new Page<>(pageNum, pageSize);

        return this.baseMapper.getDictDataPage(page, queryParams);
    }

    /**
     * 获取字典数据表单
     *
     * @param id 字典数据ID
     * @return
     */
    @Override
    public DictDataForm getDictDataForm(Long id) {
        DictData entity = this.getById(id);
        return dictDataConverter.toForm(entity);
    }

    /**
     * 保存字典数据
     *
     * @param formData
     * @return
     */
    @Override
    public boolean saveDictData(DictDataForm formData) {
        DictData entity = dictDataConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新字典数据
     *
     * @param formData 字典数据表单
     * @return
     */
    @Override
    public boolean updateDictData(DictDataForm formData) {
        DictData entity = dictDataConverter.toEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除字典数据
     *
     * @param ids 字典数据ID集合
     */
    @Override
    public void deleteDictDataByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        this.removeByIds(idList);
    }

    /**
     * 获取字典数据列表
     *
     * @param dictCode 字典编码
     * @return
     */
    @Override
    public List<Option<String>> getDictDataList(String dictCode) {
        return this.list(new LambdaQueryWrapper<DictData>()
                        .eq(DictData::getDictCode, dictCode)
                        .eq(DictData::getStatus, 1)
                ).stream().map(item -> new Option<>(item.getValue(), item.getLabel(),item.getTagType()))
                .collect(Collectors.toList());
    }
}




