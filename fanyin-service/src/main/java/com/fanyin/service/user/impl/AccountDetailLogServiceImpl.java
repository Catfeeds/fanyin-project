package com.fanyin.service.user.impl;

import com.fanyin.dto.account.PlatformAward;
import com.fanyin.enums.AccountLogType;
import com.fanyin.mapper.user.AccountDetailLogMapper;
import com.fanyin.model.project.ProjectTender;
import com.fanyin.model.recharge.RechargeLog;
import com.fanyin.model.user.Account;
import com.fanyin.model.user.AccountDetailLog;
import com.fanyin.model.user.AccountLog;
import com.fanyin.service.user.AccountDetailLogService;
import com.fanyin.service.user.AccountLogService;
import com.fanyin.service.user.AccountService;
import com.fanyin.utils.BeanCopyUtil;
import com.fanyin.utils.BigDecimalUtils;
import com.fanyin.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 投资人资金详细信息
 * @author 二哥很猛
 * @date 2018/11/19 16:48
 */
@Service("accountDetailLogService")
public class AccountDetailLogServiceImpl implements AccountDetailLogService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountLogService accountLogService;

    @Autowired
    private AccountDetailLogMapper accountDetailLogMapper;

    @Override
    public void fullAuditUnfreeze(ProjectTender tender) {


        AccountDetailLog log = new AccountDetailLog();
        log.setUserId(tender.getUserId());
        log.setType(AccountLogType.FULL_AUDIT.getType());
        log.setWaitCapital(tender.getAccount());
        //总利息
        BigDecimal interest = tender.getBaseInterest().add(tender.getCouponInterest()).add(tender.getPlatformInterest());
        log.setWaitInterest(interest);
        //解冻金额=投标金额-抵扣券金额
        BigDecimal unFreeze = tender.getAccount().subtract(tender.getVoucherInterest());
        log.setTenderFreeze(BigDecimalUtils.negation(unFreeze));
        BigDecimal amount = tender.getAccount().add(interest);
        log.setAmount(amount);
        log.setTotal(amount);
        log.setAddTime(DateUtil.getNow());
        this.capitalOperation(log);
    }

    @Override
    public void capitalOperation(AccountDetailLog detailLog) {

        Account account = accountService.getByUserId(detailLog.getUserId());
        //资金信息变更由传入参数决定
        account.setAccumulatedIncome(account.getAccumulatedIncome().add(detailLog.getAccumulatedIncome()));
        account.setAvailableBalance(account.getAvailableBalance().add(detailLog.getAvailableBalance()));
        account.setRecharge(account.getRecharge().add(detailLog.getRecharge()));
        account.setTenderFreeze(account.getTenderFreeze().add(detailLog.getTenderFreeze()));
        account.setTotal(account.getTotal().add(detailLog.getTotal()));
        account.setWaitCapital(account.getWaitCapital().add(detailLog.getWaitCapital()));
        account.setWaitInterest(account.getWaitInterest().add(detailLog.getWaitInterest()));
        account.setWithdrawFreeze(account.getWithdrawFreeze().add(detailLog.getWithdrawFreeze()));
        accountService.updateAccount(account);
        //写入详细资金日志,记录此时的账户资金金额
        AccountDetailLog log = BeanCopyUtil.copy(account, AccountDetailLog.class,"id");
        log.setType(detailLog.getType());
        log.setAddTime(detailLog.getAddTime());
        accountDetailLogMapper.insertSelective(log);
    }

    @Override
    public void tenderFreeze(ProjectTender tender) {
        AccountDetailLog log = new AccountDetailLog();
        Date now = DateUtil.getNow();
        log.setUserId(tender.getUserId());
        log.setType(AccountLogType.TENDER.getType());
        BigDecimal freeze = tender.getAccount().subtract(tender.getVoucherInterest());
        log.setTenderFreeze(freeze);
        log.setAmount(freeze);
        log.setAvailableBalance(BigDecimalUtils.negation(freeze));
        log.setTotal(BigDecimalUtils.negation(freeze));
        log.setAddTime(now);
        this.capitalOperation(log);

        AccountLog accountLog = new AccountLog();
        accountLog.setTenderId(tender.getId());
        accountLog.setUserId(tender.getUserId());
        accountLog.setAmount(freeze);
        accountLog.setAddTime(log.getAddTime());
        accountLog.setType(log.getType());
        accountLog.setAddTime(now);
        accountLogService.insertAccountLog(accountLog);
    }

    @Override
    public void rechargeSuccess(RechargeLog rechargeLog) {
        AccountDetailLog log = new AccountDetailLog();
        Date now = DateUtil.getNow();
        log.setUserId(rechargeLog.getUserId());
        log.setType(AccountLogType.RECHARGE.getType());
        BigDecimal recharge = rechargeLog.getRealAmount();
        log.setAvailableBalance(recharge);
        log.setRecharge(recharge);
        log.setTotal(recharge);
        this.capitalOperation(log);

        AccountLog accountLog = new AccountLog();
        accountLog.setType(log.getType());
        accountLog.setAddTime(now);
        accountLog.setAmount(recharge);
        accountLog.setUserId(log.getUserId());
        accountLogService.insertAccountLog(accountLog);
    }

    @Override
    public void platformAward(PlatformAward award) {
        AccountDetailLog log = new AccountDetailLog();
        Date now = DateUtil.getNow();
        log.setUserId(award.getUserId());
        log.setType(award.getAccountLogType().getType());
        BigDecimal awardAmount = BigDecimal.valueOf(award.getAmount());
        log.setAvailableBalance(awardAmount);
        log.setRecharge(awardAmount);
        log.setTotal(awardAmount);
        log.setRemark(award.getRemark());
        this.capitalOperation(log);

        AccountLog accountLog = new AccountLog();
        accountLog.setType(log.getType());
        accountLog.setAddTime(now);
        accountLog.setAmount(awardAmount);
        accountLog.setUserId(log.getUserId());
        accountLog.setRemark(log.getRemark());
        accountLogService.insertAccountLog(accountLog);
    }


}
