package com.norm.timemall.app.base.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class EnumCheckValidator implements ConstraintValidator<EnumCheck, Object> {

    private EnumCheck enumCheck;

    /**
     * 初始化验证消息，可以得到配置的注解内容
     *
     * @param enumCheck 需要验证的数据
     */
    @Override
    public void initialize(EnumCheck enumCheck) {
        this.enumCheck = enumCheck;
    }

    /**
     * 执行验证方法，用来验证业务逻辑，需要继承 ConstraintValidator 接口
     *
     * @param value                      注解表明
     * @param constraintValidatorContext 继承 ConstraintValidator 接口
     * @return 验证结果
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        // 注解表明为必选项 则不允许为空，否则可以为空
        if (value == null) {
            return !this.enumCheck.required();
        }

        Boolean result = Boolean.FALSE;
        Class<?> valueClass = value.getClass();
        try {
            // 通过反射执行枚举类中validation方法
            Method method = this.enumCheck.enumClass().getMethod(this.enumCheck.enumMethod(), valueClass);
            result = (Boolean) method.invoke(null, value);
            if (result == null) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("custom EnumCheckValidator error\n" + e);
        }
        return result;
    }
}
