package cn.d1m.pandora.controller;

import cn.d1m.pandora.domain.dto.ConsumerCardDto;
import cn.d1m.pandora.domain.enumeration.ConsumerCardStatus;
import cn.d1m.pandora.domain.enumeration.DiscountType;
import cn.d1m.pandora.domain.exception.BusinessException;
import cn.d1m.pandora.domain.web.BaseResponse;
import cn.d1m.pandora.domain.web.ErrorResponse;
import cn.d1m.pandora.entry.ConsumerCard;
import cn.d1m.pandora.entry.First;
import cn.d1m.pandora.entry.Rule;
import cn.d1m.pandora.entry.Third;
import cn.d1m.pandora.entry.output.CommonResponse;
import cn.d1m.pandora.service.ConsumerCardService;
import cn.d1m.pandora.service.ConsumerCardTKService;
import cn.d1m.pandora.service.RuleService;
import cn.d1m.pandora.utils.BusinessBaHolder;
import cn.d1m.pandora.utils.CommonConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.*;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Api(value = "优惠券操作")
@RestController
@RequestMapping(path = "/api/coupon/")
public class APICouponController {

    @Autowired
    public ConsumerCardService consumerCardService;

    @Autowired
    public RuleService ruleService;

    @Autowired
    private ConsumerCardTKService consumerCardTKService;


    @GetMapping
    @ApiOperation(value = "根据code查询卡券")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "discountType字段说明：OFF_EVERY满减有小票；RATIO百分比折扣有小票；OTHER其他形式折扣无小票")})
    public BaseResponse<CouponResp> getOne(@RequestParam(name = "code") String code) {
        final CouponResp couponResp = new CouponResp();
        final ConsumerCardDto consumerCardDto = consumerCardTKService.findCardDtoByParams(ImmutableMap.of("code", code))
                .stream().findFirst()
                .orElseThrow(() -> new BusinessException("Code not exist!"));
        if (Objects.nonNull(consumerCardDto.getRule())) {
            BeanUtils.copyProperties(consumerCardDto.getRule(), couponResp);
        }
        if (Objects.nonNull(consumerCardDto.getConsumer())) {
            BeanUtils.copyProperties(consumerCardDto.getConsumer(), couponResp);
        }
        if (Objects.nonNull(consumerCardDto.getCard())) {
            BeanUtils.copyProperties(consumerCardDto.getCard(), couponResp);
        }
        BeanUtils.copyProperties(consumerCardDto, couponResp);
        return BaseResponse.buildSuccessfully(couponResp);
    }

    @Data
    private static class CouponResp {
        @ApiModelProperty(value = "卡券名称", required = true, example = "美美神卡")
        private String name;
        @ApiModelProperty(value = "票圈有效期起始时间", required = true, example = "2018-09-30")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp beginTime;
        @ApiModelProperty(value = "票圈有效期结束时间", required = true, example = "2018-10-11")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Timestamp endTime;
        @ApiModelProperty(value = "核销时间", example = "2018-09-10 11:12:30")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Timestamp redeemTime;
        private String description;
        private String code;
        //状态redeemed,expired,normal
        @ApiModelProperty(value = "优惠券状态", notes = "normal：可用" +
                "expired：已过期" +
                "redeemed：已核销", required = true, example = "redeemed|expired|normal")
        private ConsumerCardStatus status;
        @ApiModelProperty(value = "名", required = true, example = "小霸")
        private String firstName;
        @ApiModelProperty(value = "姓氏", required = true, example = "车")
        private String lastName;
        @ApiModelProperty(value = "VIP等级", required = true, example = "VIP8")
        private String loyaltyTier;
        @ApiModelProperty(value = "优惠券优惠形式", notes = "OTHER无效票，其他都需要小票", required = true)
        private DiscountType discountType;
        @ApiModelProperty(value = "条形码图片地址", required = true, example = "http://www.baidu.com")
        private String barCodeUrl;
        @ApiModelProperty(value = "优惠类型编码", required = true, example = "124845456421")
        private String typeCode;
        @ApiModelProperty(value = "手机号码", required = true, example = "13856891572")
        private String mobilePhone;
    }


    @PutMapping
    public CommonResponse update(@Validated({First.class}) Rule cardRule) {
        return ruleService.updateCardRule(cardRule);


    }

    @PatchMapping
    @ApiOperation("更新卡券状态")
    public CommonResponse updateStatus(@Validated StatusUpdateReq req) {
        final Rule rule = new Rule();
        BeanUtils.copyProperties(req, rule);
        return ruleService.updateCardRuleStatus(rule);
    }

    @Data
    private static class StatusUpdateReq {
        @NotBlank(message = "不能为空")
        @Size(max = 32, message = "最大长度32")
        @ApiModelProperty(value = "id", required = true, example = "fdfdf-gds-dsfsdag-asdfas-g")
        private String id;// varchar(32) NOT NU
        @NotBlank(message = "不能为空")
        @Size(max = 8, message = "最大长度为8")
        @ApiModelProperty(value = "状态更新为", required = true, example = "wewewe")
        private String status;//` varchar(8) DEF
        @NotBlank(message = "不能为空")
        @Size(max = 32)
        @ApiModelProperty(value = "修改者", required = true, example = "root")
        private String createdBy;//` varchar(32
    }

    @DeleteMapping(path = "/{id}")
    public CommonResponse delete(@PathVariable String id) {
        final Rule rule = new Rule();
        rule.setId(id);
        rule.setIsdelete("true");
        return ruleService.updateCardRuleDelete(rule);
    }


    @PostMapping
    @ApiOperation(value = "新增优惠券种类")
    public CommonResponse insert(@Validated({Third.class}) Rule cardRule) {

        if (cardRule.getBeginTimestamp() == null && cardRule.getEndTimestamp() == null) {
            if (StringUtils.isBlank(cardRule.getSendedBeginTimestamp()) || StringUtils.isBlank(cardRule.getSendedEndTimestamp())) {
                return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_FAIL, "Sended_begin_timestamp or  end is empty!");
            }
        }
        return ruleService.insertCardRule(cardRule);
    }


    @GetMapping(path = "/rule")
    @ApiOperation(value = "优惠券种类列表")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "未授权客户机访问数据", response = ErrorResponse.class)
    })
    public CommonResponse<PageInfo<Rule>> selectList(@RequestParam(required = false) String code,
                                                     @RequestParam(required = false) String rule,
                                                     @RequestParam(required = false) String name,
                                                     @RequestParam(required = false) Integer quantity,
                                                     @ApiParam(value = "开始时间", example = "2018-10-20", name = "beginTimestamp")
                                                     @RequestParam(required = false, name = "beginTimestamp") @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp beginTimestamp,
                                                     @ApiParam(value = "结束时间", example = "2018-10-28", name = "endTimestamp")
                                                     @RequestParam(required = false, name = "endTimestamp") @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp endTimestamp,
                                                     @RequestParam(required = false) String sendedBeginTimestamp,
                                                     @RequestParam(required = false) String sendedEndTimestamp,
                                                     @RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String property,
                                                     @RequestParam(required = false) String status,
                                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        final Rule query = new Rule();
        query.setCode(code);
        query.setRule(rule);
        query.setName(name);
        query.setQuantity(quantity);
        query.setBeginTimestamp(beginTimestamp);
        query.setEndTimestamp(endTimestamp);
        query.setSendedBeginTimestamp(sendedBeginTimestamp);
        query.setSendedEndTimestamp(sendedEndTimestamp);
        query.setTitle(title);
        query.setProperty(property);
        query.setStatus(status);
        PageHelper.startPage(pageNum, pageSize);
        final List<Rule> rules = ruleService.selectList(query);
        final PageInfo<Rule> rulePageInfo = new PageInfo<>(rules);

        return new CommonResponse(CommonConstants.HTTP_STATUS_CODE_SUCCESS, rulePageInfo);
    }

    //    @GetMapping
    public CommonResponse selectListCoupon(@RequestParam(required = false) String code,
                                           @RequestParam(required = false) String rule,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) Integer quantity,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp beginTimestamp,
                                           @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp endTimestamp,
                                           @RequestParam(required = false) String sendedBeginTimestamp,
                                           @RequestParam(required = false) String sendedEndTimestamp,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String property,
                                           @RequestParam(required = false) String status) {
        final Rule query = new Rule();
        query.setCode(code);
        query.setRule(rule);
        query.setName(name);
        query.setQuantity(quantity);
        query.setBeginTimestamp(beginTimestamp);
        query.setEndTimestamp(endTimestamp);
        query.setSendedBeginTimestamp(sendedBeginTimestamp);
        query.setSendedEndTimestamp(sendedEndTimestamp);
        query.setTitle(title);
        query.setProperty(property);
        query.setStatus(status);
        return consumerCardService.selectListCoupon(query);
    }

    @RequestMapping(path = "/consumer", method = RequestMethod.GET)
    public CommonResponse selectlistConsumerCoupon(@RequestParam(required = false) String id,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp beginTime,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp endTime,
                                                   @RequestParam(required = false) String ruleId,
                                                   @RequestParam(required = false) String openid,
                                                   @RequestParam(required = false) String status,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp receiveTime,
                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Timestamp redeemTime,
                                                   @RequestParam(required = false) String businessPosBranchId,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String property,
                                                   @RequestParam(required = false) String posBranchName) {
        final ConsumerCard consumerCard = new ConsumerCard();
        consumerCard.setId(id);
        consumerCard.setBeginTime(beginTime);
        consumerCard.setEndTime(endTime);
        consumerCard.setRuleId(ruleId);
        consumerCard.setOpenid(openid);
        consumerCard.setStatus(status);
        consumerCard.setReceiveTime(receiveTime);
        consumerCard.setRedeemTime(redeemTime);
        consumerCard.setBusinessPosBranchId(businessPosBranchId);
        consumerCard.setName(name);
        consumerCard.setProperty(property);
        consumerCard.setPosBranchName(posBranchName);
        return consumerCardService.selectlistConsumerCoupon(consumerCard);
    }

    @PutMapping(path = "/redeemed")
    @ApiOperation(value = "卡券核销接口")
    public CommonResponse redeemed(@RequestBody @Validated RedeemedCardReq req) {

        final String email = BusinessBaHolder.getBusinessBa().getEmail();

        final cn.d1m.pandora.domain.entity.ConsumerCard card = new cn.d1m.pandora.domain.entity.ConsumerCard();

        final ConsumerCardDto cardDto = consumerCardTKService
                .findCardDtoByParams(ImmutableMap
                        .of("code", req.getCode(),
                                "status", ConsumerCardStatus.NORMAL))
                .stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("code不存在或已核销！"));

        if (cardDto.getCard().getDiscountType() != DiscountType.OTHER &&
                StringUtils.isEmpty(req.getPosTicketNo())) {
            throw new BusinessException("交易票号不能为空！");
        }

        card.setId(cardDto.getId());
        card.setStatus(ConsumerCardStatus.REDEEMED);
        card.setBusinessPosBranchId(email);
        card.setPosTicketNo(req.getPosTicketNo());

        card.setRedeemTime(Timestamp.valueOf(LocalDateTime.now()));

        consumerCardTKService.updateByPrimaryKeySelective(card);

        return CommonResponse.ok().status(CommonConstants.HTTP_STATUS_CODE_SUCCESS).build();
    }

    @Data
    private static class RedeemedCardReq {
        @NotBlank(message = "卡券编号不能为空")
        @Length(max = 32, message = "卡券code最长32位")
        @ApiModelProperty(value = "卡券code", required = true)
        private String code;
        @Length(max = 32, message = "pos交易票号最长32位")
        @ApiModelProperty(value = "pos交易票号", required = true)
        private String posTicketNo;
    }

}
