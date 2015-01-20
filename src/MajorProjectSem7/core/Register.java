package majorprojectsem7.core;

/**
 *
 * @author Rajat Bothra
 */
public class Register {

    private Object address;
    private byte value[];
    int endianness = BIG_ENDIAN;

    public int size = 32;
    public static final int BIG_ENDIAN = 1000;
    public static final int LITTLE_ENDIAN = 3000;

    public Register() {
        value = new byte[getArchitectureVersion(System.getProperty("os.arch"))];
    }

    public Register(int bits) {
        size = bits;
        value = new byte[bits];
    }

    public Register(int bits, int endian) {
        size = bits;
        value = new byte[bits];
        endianness = endian;
    }

    public void setValue(byte[] val) {
        value = val;
    }

    private int getArchitectureVersion(String arch) {
        return 32;
    }

    public byte[] getHigh() {
        byte b[] = new byte[(size >> 1)];
        System.arraycopy(value, 0, b, 0, (size >> 1));
        return b;
    }

    public byte[] getLow() {
        byte b[] = new byte[(size >> 1)];
        System.arraycopy(value, (size >> 1), b, 0, (size >> 1));
        return b;
    }

    public byte[] getValue() {
        return value;
    }

}
