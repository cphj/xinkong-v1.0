package xk.baseinfo.enmu;

public enum TureFalseEnum {

    TURE_VALUE(true),
    FALSE_VALUE(false);

    private boolean value;

    TureFalseEnum(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
