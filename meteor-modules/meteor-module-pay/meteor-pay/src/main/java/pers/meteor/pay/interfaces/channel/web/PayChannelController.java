package pers.meteor.pay.interfaces.channel.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.response.MultiResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.pay.application.channel.PayChannelAppService;
import pers.meteor.pay.application.channel.PayChannelQueryService;
import pers.meteor.pay.interfaces.channel.web.cmd.ChannelRateUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppCreateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayAppUpdateCmd;
import pers.meteor.pay.interfaces.channel.web.cmd.PayChannelCreateCmd;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayAppSimpleRespVO;
import pers.meteor.pay.interfaces.channel.web.vo.PayClientRespVO;

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
    public SingleResponse<Long> createPayChannel(@Valid @RequestBody PayAppCreateCmd createReqVo) {
        Long payChannelId = payChannelAppService.createPayApp(createReqVo);
        return SingleResponse.success(payChannelId);
    }

    @PutMapping("/update")
    public SingleResponse<Boolean> updatePayChannel(@Valid @RequestBody PayAppUpdateCmd updateReqVo) {
        payChannelAppService.updatePayApp(updateReqVo);
        return SingleResponse.success(true);
    }

    @PostMapping("/config/add")
    public SingleResponse<Long> addPayChannelConfig(@Valid @RequestBody PayChannelCreateCmd configReqVo) {
        Long channelConfigId = payChannelAppService.addPayChannelConfig(configReqVo);
        return SingleResponse.success(channelConfigId);
    }

    @PutMapping("/config/update")
    public SingleResponse<Boolean> updatePayChannelConfig(@Valid @RequestBody PayChannelCreateCmd configReqVo) {
        payChannelAppService.updatePayChannelConfig(configReqVo);
        return SingleResponse.success(true);
    }

    @PutMapping("/client/update")
    public SingleResponse<Boolean> updatePayClientConfig(@Valid @RequestBody PayClientRespVO configReqVo) {
        payChannelAppService.updatePayClientConfig(configReqVo);
        return SingleResponse.success(true);
    }

    @PutMapping("/rate/update")
    public SingleResponse<Boolean> updatePayChannelRate(@Valid @RequestBody ChannelRateUpdateCmd rateReqVo) {
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
    public SingleResponse<PayAppRespVO> getPayChannel(@RequestParam("id") Long id) {
        PayAppRespVO payChannel = payChannelQueryService.getPayApp(id);
        return SingleResponse.success(payChannel);
    }

    @GetMapping("/list")
    public MultiResponse<PayAppSimpleRespVO> getPayChannelList() {
        List<PayAppSimpleRespVO> payChannels = payChannelQueryService.listPayApp();
        return MultiResponse.success(payChannels);
    }
}
