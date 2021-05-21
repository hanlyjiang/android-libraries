package cn.hanlyjiang.apf_library.utils.ref;

/**
 * @author hanlyjiang 5/21/21 11:23 AM
 * @version 1.0
 */
public class RefTestClazz {
    public static final String sStaticValue_DEFAULT_VALUE = "no value";

    private static String sStaticValue = sStaticValue_DEFAULT_VALUE;

    public static String doNoParamsCall() {
        System.out.println("doNoParamsCall is Called!");
        return "doNoParamsCall";
    }

    public static void setStaticValue(String value) {
        RefTestClazz.sStaticValue = value;
    }

    public static String getStaticValue() {
        return sStaticValue;
    }

    private String constructorType;
    private String value = "no value";


    public RefTestClazz() {
        constructorType = "public_no_params";
    }

    private RefTestClazz(String value) {
        constructorType = "private_with_one_params";
        this.value = value;
    }

    public RefTestClazz(String constructorType, String value) {
        this.constructorType = constructorType;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConstructorType() {
        return constructorType;
    }
}
