public enum CarClass {
    HY(0), LMP2_ELMS(1), LMP2_WEC(2), LMP3(3), GTE(4), GT3(5);
    int value;

    CarClass(int value) {
        this.value = value;
    }

    public int compare(CarClass other) {
        return Integer.compare(this.value, other.value);
    }
}
