package pers.meteor.pay.interfaces.channel.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.core.pojo.response.MultiResponse;
import pers.meteor.common.core.pojo.response.SingleResponse;
import pers.meteor.pay.application.channel.PayChannelAppService;
import pers.meteor.pay.application.channel.PayChannelQueryService;
import pers.meteor.pay.interfaces.channel.web.vo.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author meteor
 */
@RequestMapping("/channel")
@RestController
@RequiredArgsConstructor
public class PayChannelController {
    private final PayChannelQueryService payChannelQueryService;
    private final PayChannelAppService payChannelAppService;

    @PostMapping("/create")
    public SingleResponse<Long> createPayChannel(@Valid @RequestBody PayChannelCreateReqVO createReqVo) {
        Long payChannelId = payChannelAppService.createPayChannel(createReqVo);
        return SingleResponse.success(payChannelId);
    }

    @PutMapping("/update")
    public SingleResponse<Boolean> updatePayChannel(@Valid @RequestBody PayChannelUpdateReqVO updateReqVo) {
        payChannelAppService.updatePayChannel(updateReqVo);
        return SingleResponse.success(true);
    }

    @PostMapping("/config/add")
    public SingleResponse<Long> addPayChannelConfig(@Valid @RequestBody PayChannelConfigReqVO configReqVo) {
        Long channelConfigId = payChannelAppService.addPayChannelConfig(configReqVo);
        return SingleResponse.success(channelConfigId);
    }

    @PutMapping("/config/update")
    public SingleResponse<Boolean> updatePayChannelConfig(@Valid @RequestBody PayChannelConfigReqVO configReqVo) {
        payChannelAppService.updatePayChannelConfig(configReqVo);
        return SingleResponse.success(true);
    }

    @PutMapping("/client/update")
    public SingleResponse<Boolean> updatePayClientConfig(@Valid @RequestBody PayClientConfigVO configReqVo) {
        payChannelAppService.updatePayClientConfig(configReqVo);
        return SingleResponse.success(true);
    }

    @PutMapping("/rate/update")
    public SingleResponse<Boolean> updatePayChannelRate(@Valid @RequestBody PayChannelRateReqVO rateReqVo) {
        payChannelAppService.updatePayChannelRate(rateReqVo);
        return SingleResponse.success(true);
    }

    @DeleteMapping("/delete")
    public SingleResponse<Boolean> deletePayChannel(@RequestParam("id") Long id) {
        payChannelAppService.deletePayChannel(id);
        return SingleResponse.success(true);
    }

    @DeleteMapping("/config/delete")
    public SingleResponse<Boolean> deletePayChannelConfig(@RequestParam("id") Long id) {
        payChannelAppService.deletePayChannelConfig(id);
        return SingleResponse.success(true);
    }

    @GetMapping("/get")
    public SingleResponse<PayChannelRespVO> getPayChannel(@RequestParam("id") Long id) {
        PayChannelRespVO payChannel = payChannelQueryService.getPayChannel(id);
        return SingleResponse.success(payChannel);
    }

    @GetMapping("/list")
    public MultiResponse<PayChannelSimpleRespVO> getPayChannelList() {
        List<PayChannelSimpleRespVO> payChannels = payChannelQueryService.listPayChannel();
        return MultiResponse.success(payChannels);
    }
}
