package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.OfficeEmployee;
import com.norm.timemall.app.base.mo.OfficePayroll;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.*;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamOfficePayrollService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeamOfficePayrollServiceImpl implements TeamOfficePayrollService {
    @Autowired
    private TeamOfficePayrollMapper teamOfficePayrollMapper;
    @Autowired
    private TeamOfficeDashboardMapper teamOfficeDashboardMapper;

    @Autowired
    private TeamOfficeEmployeeMapper teamOfficeEmployeeMapper;

    @Autowired
    private TeamOfficeEmployeeBenefitMapper teamOfficeEmployeeBenefitMapper;

    @Autowired
    private TeamOfficeEmployeeCompensationMapper teamOfficeEmployeeCompensationMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;
    @Override
    public IPage<TeamOfficeQueryAdminPayrollPageRO> findPayrolls(TeamOfficeQueryAdminPayrollPageDTO dto) {
        validateUserIsAdmin(dto.getOasisId());
        IPage<TeamOfficeQueryAdminPayrollPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        if(CharSequenceUtil.isNotBlank(dto.getQ())){
            dto.setEncryptedQ(mybatisMateEncryptor.defaultEncrypt(dto.getQ()));
        }
        return teamOfficePayrollMapper.selectPageByQ(page,dto);
    }

    @Override
    public TeamOfficeFetchDashboardRO findDashboard(String oasisId) {
        return teamOfficeDashboardMapper.selectDashboardByOasisId(oasisId);
    }

    @Override
    public void delOnePayroll(String id) {

        OfficePayroll payroll = teamOfficePayrollMapper.selectById(id);
        if(payroll==null){
           throw new QuickMessageException("未找到相关底单数据");
        }
        if(!OfficePayrollStatusEnum.PENDING.getMark().equals(payroll.getStatus())){
            throw new QuickMessageException("底单状态校验不通过");
        }
        validateUserIsAdmin(payroll.getOasisId());

        teamOfficePayrollMapper.deleteById(id);

    }

    @Override
    public void doGenerateGivePerkPayroll(TeamOfficeGivePerkDTO dto) {
        // if status is resigned, return
        if(OfficeEmployeeStatusEnum.RESIGNED.getMark().equals(dto.getEmployeeStatus())){
            throw new QuickMessageException("暂不支持给已离职员工发放");
        }

        Oasis oasis =teamOasisMapper.selectById(dto.getOasisId());
        BigDecimal netPay = validateOasisAndCalNetPay(dto, oasis);

        List<OfficeEmployeeRO> officeEmployees = teamOfficeEmployeeMapper.selectAllEmployeeByOasisIdAndStatus(dto.getOasisId(),dto.getEmployeeStatus());
        List<OfficePayroll> payrollList = new ArrayList<>();
        officeEmployees.forEach(e->{
            OfficePayroll payroll = new OfficePayroll();
            payroll.setId(IdUtil.simpleUUID())
                    .setOasisId(dto.getOasisId())
                    .setEmployeeId(e.getId())
                    .setEmployeeCompany(oasis.getTitle())
                    .setEmployeeDepartment(e.getDepartment())
                    .setEmployeeRole(e.getRole())
                    .setEmployeeStatus(e.getStatus())
                    .setEmployeeGenre(e.getGenre())
                    .setTitle(dto.getTitle())
                    .setGrossPay(dto.getGrossAmount())
                    .setDeductions(dto.getDeduction())
                    .setWithholdAndRemitTax(dto.getWithholdAndRemitTax())
                    .setNetPay(netPay)
                    .setStatus(OfficePayrollStatusEnum.PENDING.getMark())
                    .setCategory(OfficePayrollCategoryEnum.PERK.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date())
            ;
            payrollList.add(payroll);
        });

        List<List<OfficePayroll>> payrollPartitions = ListUtil.partition(payrollList, 24);
        payrollPartitions.forEach(e-> teamOfficePayrollMapper.insertBatchSomeColumn(e));


    }

    @Override
    public void doBatchGenerateEmployeesSalaryPayroll(TeamOfficePayEmployeesSalaryDTO dto) {
        // if status is resigned, return
        if(OfficeEmployeeStatusEnum.RESIGNED.getMark().equals(dto.getEmployeeStatus())){
            throw new QuickMessageException("暂不支持给已离职员工发放");
        }
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // role check,only admin can continue
        Oasis oasis = teamOasisMapper.selectById(dto.getOasisId());
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        Gson gson = new Gson();

        List<OfficeEmployeeAndCompensationRO> officeEmployees = teamOfficeEmployeeMapper.selectEmployeeAndCompensationByOasisIdAndStatus(dto.getOasisId(),dto.getEmployeeStatus());
        List<OfficePayroll> payrollList = new ArrayList<>();
        officeEmployees.forEach(e->{
            OfficeEmployeeCompensationRO  salaryItem=new OfficeEmployeeCompensationRO();
            salaryItem.setTitle("税前工资");
            salaryItem.setAmount(e.getSalary());
            salaryItem.setDirection(TransDirectionEnum.CREDIT.getMark());
            e.getCompensation().add(salaryItem);
            OfficePayroll payroll = new OfficePayroll();

            BigDecimal deductions = e.getCompensation().stream().filter(x->x.getDirection()==-1).map(OfficeEmployeeCompensationRO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal grossPay = e.getCompensation().stream().filter(x->x.getDirection()==1).map(OfficeEmployeeCompensationRO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            payroll.setId(IdUtil.simpleUUID())
                    .setOasisId(dto.getOasisId())
                    .setEmployeeId(e.getId())
                    .setEmployeeCompany(oasis.getTitle())
                    .setEmployeeRole(e.getRole())
                    .setEmployeeStatus(e.getStatus())
                    .setEmployeeGenre(e.getGenre())
                    .setTitle(dto.getTitle())
                    .setGrossPay(grossPay)
                    .setDeductions(deductions)
                    .setCompensations(gson.toJson(e.getCompensation()))
                    .setStatus(OfficePayrollStatusEnum.PENDING.getMark())
                    .setCategory(OfficePayrollCategoryEnum.SALARY.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date())
            ;
            payrollList.add(payroll);
        });

        List<List<OfficePayroll>> payrollPartitions = ListUtil.partition(payrollList, 24);
        payrollPartitions.forEach(e-> teamOfficePayrollMapper.insertBatchSomeColumn(e));

        // update benefit info
        teamOfficePayrollMapper.updateBenefitUsingEmployeeBenefit(dto);
        // update department info
        teamOfficePayrollMapper.updateDepartmentByOasisId(dto.getOasisId());
        // update netPay info
        teamOfficePayrollMapper.updateNetPayByOasisId(dto.getOasisId());


    }

    @Override
    public void doGenerateOneEmployeeSalaryPayroll(TeamOfficePayOneEmployeeSalaryDTO dto) {
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        OfficeEmployeeRO employee=teamOfficeEmployeeMapper.selectOneEmployeeById(dto.getEmployeeId());

        if(employee==null){
            throw new QuickMessageException("未找到相关员工数据");
        }

        // role check,only admin can continue
        Oasis oasis = teamOasisMapper.selectById(employee.getOasisId());
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // find benefit info
        OfficeEmployeeBenefitRO benefit = teamOfficeEmployeeBenefitMapper.selectOneBenefitByEmployeeId(dto.getEmployeeId());
        if(benefit==null){
            throw new QuickMessageException("未找到员工五险一金数据");
        }

        BigDecimal withholdAndRemitTax = calEmployeeBenefit(benefit);

        ArrayList<OfficeEmployeeCompensationRO> compensationArr=teamOfficeEmployeeCompensationMapper.selectListByEmployeeId(dto.getEmployeeId());

        if(compensationArr==null){
            compensationArr=new ArrayList<>();
        }

        OfficeEmployeeCompensationRO  salaryItem=new OfficeEmployeeCompensationRO();
        salaryItem.setTitle("税前工资");
        salaryItem.setAmount(employee.getSalary());
        salaryItem.setDirection(TransDirectionEnum.CREDIT.getMark());
        compensationArr.add(salaryItem);


        BigDecimal deductions = compensationArr.stream().filter(x->x.getDirection()==-1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossPay = compensationArr.stream().filter(x->x.getDirection()==1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Gson gson = new Gson();

        OfficePayroll payroll = new OfficePayroll();
        payroll.setId(IdUtil.simpleUUID())
                .setOasisId(employee.getOasisId())
                .setEmployeeId(employee.getId())
                .setEmployeeCompany(oasis.getTitle())
                .setEmployeeDepartment(employee.getDepartment())
                .setEmployeeRole(employee.getRole())
                .setEmployeeStatus(employee.getStatus())
                .setEmployeeGenre(employee.getGenre())
                .setTitle(dto.getTitle())
                .setGrossPay(grossPay)
                .setDeductions(deductions)
                .setWithholdAndRemitTax(withholdAndRemitTax)
                .setNetPay(grossPay.subtract(deductions).subtract(withholdAndRemitTax))
                .setBenefits(gson.toJson(benefit))
                .setCompensations(gson.toJson(compensationArr))
                .setStatus(OfficePayrollStatusEnum.PENDING.getMark())
                .setCategory(OfficePayrollCategoryEnum.SALARY_SP.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOfficePayrollMapper.insert(payroll);


    }

    @Override
    public IPage<TeamOfficeQueryEmployeePayrollPageRO> findEmployeePayrolls(TeamOfficeQueryEmployeePayrollPageDTO dto) {
        IPage<TeamOfficeQueryEmployeePayrollPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String employeeBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return teamOfficePayrollMapper.selectEmployeePayrollPageByQ(page,dto,employeeBrandId);
    }

    @Override
    public TeamOfficeFetchPayrollInfoRO findPayrollInfo(String id) {

        TeamOfficeFetchPayrollInfoRO ro = teamOfficePayrollMapper.selectOnePayrollById(id);

        if(ro==null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        String authentication = matchUserPayrollRole(ro);
        if(authentication.equals("illegal_user")){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        ro.setAuthentication(authentication);

        return ro;
    }

    @Override
    public void doEditPayrollBenefit(TeamOfficeEditPayrollBenefitDTO dto) {
        OfficePayroll payroll = teamOfficePayrollMapper.selectById(dto.getPayrollId());
        if(payroll==null){
            throw new QuickMessageException("未找到相关底单数据");
        }
        if(!OfficePayrollStatusEnum.PENDING.getMark().equals(payroll.getStatus())){
            throw new QuickMessageException("底单状态校验不通过");
        }
        if(OfficePayrollCategoryEnum.PERK.getMark().equals(payroll.getCategory())){
            throw new QuickMessageException("底单类型校验不通过");
        }
        validateUserIsAdmin(payroll.getOasisId());

        OfficeEmployeeBenefitRO benefit=new OfficeEmployeeBenefitRO();
        benefit.setPensionInsuranceBase(dto.getPensionInsuranceBase())
                .setPensionInsuranceCompanyRate(dto.getPensionInsuranceCompanyRate())
                .setPensionInsuranceEmployeeRate(dto.getPensionInsuranceEmployeeRate())
                .setMedicalInsuranceBase(dto.getMedicalInsuranceBase())
                .setMedicalInsuranceCompanyRate(dto.getMedicalInsuranceCompanyRate())
                .setMedicalInsuranceEmployeeRate(dto.getMedicalInsuranceEmployeeRate())
                .setUnemploymentInsuranceBase(dto.getUnemploymentInsuranceBase())
                .setUnemploymentInsuranceCompanyRate(dto.getUnemploymentInsuranceCompanyRate())
                .setUnemploymentInsuranceEmployeeRate(dto.getUnemploymentInsuranceEmployeeRate())
                .setOccupationalInjuryInsuranceBase(dto.getOccupationalInjuryInsuranceBase())
                .setOccupationalInjuryInsuranceCompanyRate(dto.getOccupationalInjuryInsuranceCompanyRate())
                .setOccupationalInjuryInsuranceEmployeeRate(dto.getOccupationalInjuryInsuranceEmployeeRate())
                .setBirthInsuranceBase(dto.getBirthInsuranceBase())
                .setBirthInsuranceCompanyRate(dto.getBirthInsuranceCompanyRate())
                .setBirthInsuranceEmployeeRate(dto.getBirthInsuranceEmployeeRate())
                .setHousingProvidentFundsBase(dto.getHousingProvidentFundsBase())
                .setHousingProvidentFundsCompanyRate(dto.getHousingProvidentFundsCompanyRate())
                .setHousingProvidentFundsEmployeeRate(dto.getHousingProvidentFundsEmployeeRate());

        BigDecimal withholdAndRemitTax = calEmployeeBenefit(benefit);
        BigDecimal netPay=payroll.getGrossPay().subtract(withholdAndRemitTax).subtract(payroll.getDeductions());

        payroll.setBenefits(new Gson().toJson(benefit));
        payroll.setNetPay(netPay);
        payroll.setWithholdAndRemitTax(withholdAndRemitTax);
        payroll.setModifiedAt(new Date());
        teamOfficePayrollMapper.updateById(payroll);

    }

    @Override
    public void doAddPayrollCompensation(TeamOfficeAddPayrollCompensationDTO dto) {
        OfficePayroll payroll = teamOfficePayrollMapper.selectById(dto.getPayrollId());
        if(payroll==null){
            throw new QuickMessageException("未找到相关底单数据");
        }
        if(!OfficePayrollStatusEnum.PENDING.getMark().equals(payroll.getStatus())){
            throw new QuickMessageException("底单状态校验不通过");
        }
        if(OfficePayrollCategoryEnum.PERK.getMark().equals(payroll.getCategory())){
            throw new QuickMessageException("底单类型校验不通过");
        }
        validateUserIsAdmin(payroll.getOasisId());
        Type listType = new TypeToken<ArrayList<OfficeEmployeeCompensationRO>>(){}.getType();

        ArrayList<OfficeEmployeeCompensationRO> compensationArr= new Gson().fromJson(payroll.getCompensations().toString(),listType);
        OfficeEmployeeCompensationRO  salaryItem=new OfficeEmployeeCompensationRO();
        salaryItem.setTitle(dto.getTitle());
        salaryItem.setAmount(dto.getAmount());
        salaryItem.setDirection(dto.getDirection());
        compensationArr.addFirst(salaryItem);

        // refresh money data
        BigDecimal deductions = compensationArr.stream().filter(x->x.getDirection()==-1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossPay = compensationArr.stream().filter(x->x.getDirection()==1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal netPay= grossPay.subtract(deductions).subtract(payroll.getWithholdAndRemitTax());

        payroll.setNetPay(netPay);
        payroll.setDeductions(deductions);
        payroll.setGrossPay(grossPay);
        payroll.setCompensations(new Gson().toJson(compensationArr));
        payroll.setModifiedAt(new Date());
        teamOfficePayrollMapper.updateById(payroll);

    }

    @Override
    public void doEditPayrollCompensation(TeamOfficeEditPayrollCompensationDTO dto) {
        // validate  json  arr
        try {
            new JSONArray(dto.getCompensationJson());
        } catch (JSONException ne) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        Type listType = new TypeToken<ArrayList<OfficeEmployeeCompensationRO>>(){}.getType();
        ArrayList<OfficeEmployeeCompensationRO> compensationArr= new Gson().fromJson(dto.getCompensationJson(),listType);

        if(compensationArr.isEmpty()){
            throw new QuickMessageException("暂不支持清空薪资项");
        }
        // validate amount
        boolean amountIsValid = compensationArr.stream().allMatch(e -> e.getAmount() != null && e.getAmount().compareTo(BigDecimal.ZERO) >= 0);

        // validate direction
        boolean directionIsValid = compensationArr.stream().allMatch(e -> e.getDirection() != null && (e.getDirection() == 1 || e.getDirection() == -1));
        if(!amountIsValid || !directionIsValid){
            throw new QuickMessageException("薪资项检验不通过");
        }
        OfficePayroll payroll = teamOfficePayrollMapper.selectById(dto.getPayrollId());
        if(payroll==null){
            throw new QuickMessageException("未找到相关底单数据");
        }
        if(!OfficePayrollStatusEnum.PENDING.getMark().equals(payroll.getStatus())){
            throw new QuickMessageException("底单状态校验不通过");
        }
        if(OfficePayrollCategoryEnum.PERK.getMark().equals(payroll.getCategory())){
            throw new QuickMessageException("底单类型校验不通过");
        }
        validateUserIsAdmin(payroll.getOasisId());

        // refresh money data
        BigDecimal deductions = compensationArr.stream().filter(x->x.getDirection()==-1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossPay = compensationArr.stream().filter(x->x.getDirection()==1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

       BigDecimal netPay= grossPay.subtract(deductions).subtract(payroll.getWithholdAndRemitTax());

        payroll.setNetPay(netPay);
        payroll.setDeductions(deductions);
        payroll.setGrossPay(grossPay);
        payroll.setCompensations(new Gson().toJson(compensationArr));
        payroll.setModifiedAt(new Date());
        teamOfficePayrollMapper.updateById(payroll);


    }

    @Override
    public void doPayPayroll(String id) {
        OfficePayroll payroll = teamOfficePayrollMapper.selectById(id);
        if(payroll==null){
            throw new QuickMessageException("未找到相关底单数据");
        }
        if(!OfficePayrollStatusEnum.PENDING.getMark().equals(payroll.getStatus())){
            throw new QuickMessageException("底单状态校验不通过");
        }

        validateUserIsAdmin(payroll.getOasisId());

        OfficeEmployee employee = teamOfficeEmployeeMapper.selectById(payroll.getEmployeeId());
        if(employee==null){
            throw new QuickMessageException("员工数据配置错误");
        }

        if(OfficePayrollCategoryEnum.PERK.getMark().equals(payroll.getCategory())){
            validatePayPerkPayroll(payroll);
        }
        if(OfficePayrollCategoryEnum.SALARY.getMark().equals(payroll.getCategory())
                || OfficePayrollCategoryEnum.SALARY_SP.getMark().equals(payroll.getCategory())){
            validatePaySalaryPayroll(payroll);
        }

        TransferBO bo = generateTransferBOForPayroll(payroll.getNetPay(), payroll.getId(), payroll.getOasisId(), employee.getEmployeeBrandId());

        String paymentId = defaultPayService.transfer(new Gson().toJson(bo));
        payroll.setPaymentId(paymentId);
        payroll.setStatus(OfficePayrollStatusEnum.PAID.getMark());
        payroll.setPaymentDate(new Date());
        payroll.setModifiedAt(new Date());

        teamOfficePayrollMapper.updateById(payroll);




    }

    private TransferBO generateTransferBOForPayroll(BigDecimal amount, String outNo, String payer, String payee){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payee)
                .setPayerAccount(payer)
                .setPayerType(FidTypeEnum.OASIS.getMark())
                .setTransType(TransTypeEnum.OASIS_ADMIN_WITHDRAW.getMark());
        return  bo;
    }

    private void validatePayPerkPayroll(OfficePayroll payroll){
        BigDecimal writeDown = payroll.getDeductions().add(payroll.getWithholdAndRemitTax());
        if(payroll.getGrossPay().compareTo(writeDown)<=0){
            throw new QuickMessageException("实发薪资为0或负值");
        }
        BigDecimal netPay=payroll.getGrossPay().subtract(writeDown);

        if(payroll.getNetPay().compareTo(netPay)!=0){
            throw new QuickMessageException("金额校验不通过");
        }
    }

    private void validatePaySalaryPayroll(OfficePayroll payroll){

        Type listType = new TypeToken<ArrayList<OfficeEmployeeCompensationRO>>(){}.getType();

        ArrayList<OfficeEmployeeCompensationRO> compensationArr= new Gson().fromJson(payroll.getCompensations().toString(),listType);
        OfficeEmployeeBenefitRO benefit = new Gson().fromJson(payroll.getBenefits().toString(),OfficeEmployeeBenefitRO.class);

        BigDecimal withholdAndRemitTax = calEmployeeBenefit(benefit);
        BigDecimal deductions = compensationArr.stream().filter(x->x.getDirection()==-1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal grossPay = compensationArr.stream().filter(x->x.getDirection()==1).map(OfficeEmployeeCompensationRO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal writeDown =withholdAndRemitTax.add(deductions);

        if(grossPay.compareTo(writeDown)<=0){
            throw new QuickMessageException("实发薪资为0或负值");
        }

        BigDecimal netPay=grossPay.subtract(deductions).subtract(withholdAndRemitTax);
        if(payroll.getNetPay().compareTo(netPay)!=0){
            throw new QuickMessageException("金额校验不通过");
        }

    }


    private BigDecimal calEmployeeBenefit(OfficeEmployeeBenefitRO benefit){

        BigDecimal pensionMoney=benefit.getPensionInsuranceBase().multiply(benefit.getPensionInsuranceEmployeeRate()).divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP);
        BigDecimal medicalMoney=benefit.getMedicalInsuranceBase().multiply(benefit.getMedicalInsuranceEmployeeRate()).divide(BigDecimal.valueOf(100), 2,RoundingMode.HALF_UP);
        BigDecimal unemploymentMoney=benefit.getUnemploymentInsuranceBase().multiply(benefit.getUnemploymentInsuranceEmployeeRate()).divide(BigDecimal.valueOf(100), 2,RoundingMode.HALF_UP);
        BigDecimal injuryMoney=benefit.getOccupationalInjuryInsuranceBase().multiply(benefit.getOccupationalInjuryInsuranceEmployeeRate()).divide(BigDecimal.valueOf(100), 2,RoundingMode.HALF_UP);
        BigDecimal birthMoney=benefit.getBirthInsuranceBase().multiply(benefit.getBirthInsuranceEmployeeRate()).divide(BigDecimal.valueOf(100), 2,RoundingMode.HALF_UP);
        BigDecimal housingMoney=benefit.getHousingProvidentFundsBase().multiply(benefit.getHousingProvidentFundsEmployeeRate()).divide(BigDecimal.valueOf(100), 2,RoundingMode.HALF_UP);

        return pensionMoney.add(medicalMoney).add(unemploymentMoney).add(injuryMoney).add(birthMoney).add(housingMoney);
    }

    @NotNull
    private static BigDecimal validateOasisAndCalNetPay(TeamOfficeGivePerkDTO dto, Oasis oasis) {
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(oasis ==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        BigDecimal writeDown = dto.getDeduction().add(dto.getWithholdAndRemitTax());
        if(dto.getGrossAmount().compareTo(writeDown)<=0){
            throw new QuickMessageException("实发薪资为0或负值");
        }
        return dto.getGrossAmount().subtract(writeDown);
    }

    private String matchUserPayrollRole(TeamOfficeFetchPayrollInfoRO ro){
        String authentication="illegal_user";
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Oasis oasis = teamOasisMapper.selectById(ro.getOasisId());
        if(ro.getEmployeeBrandId().equals(currentBrandId)){
            authentication="employee";
        }
        if(oasis.getInitiatorId().equals(currentBrandId)){
            authentication="admin";
        }
        return authentication;
    }


    private void validateUserIsAdmin(String oasisId){
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // role check,only admin can create
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
    }
}
