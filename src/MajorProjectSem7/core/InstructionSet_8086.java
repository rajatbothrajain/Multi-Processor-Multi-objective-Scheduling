package majorprojectsem7.core;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Rajat Bothra
 */
public class InstructionSet_8086 {

    HashMap<String, String> map = new HashMap<>();

    public static final int REG_SZ = 16;

    public static final int SEG_SZ = (int) Math.pow(2, REG_SZ);
    /**
     * General Purpose Registers for the 8086 Instruction Set.
     * <br><b>AX</b> - the accumulator register (divided into AH / AL)
     * <br><b>BX</b> - the base address register (divided into BH / BL)
     * <br><b>CX</b> - the count register (divided into CH / CL)
     * <br><b>DX</b> - the data register (divided into DH / DL)
     * <br><b>SI</b> - source index register
     * <br><b>DI</b> - destination index register
     * <br><b>SP</b> - stack pointer CS - points at the segment containing the
     * current program.
     * <br><b>DS</b> - generally points at segment where variables are defined.
     * <br><b>ES</b> - extra segment register, it's up to a coder to define its
     * usage.
     * <br><b>SS</b> - points at the segment containing the stack.
     */
    public static final Register AX = new Register(REG_SZ);
    public final Register BX = new Register(REG_SZ);
    static final Register CX = new Register(REG_SZ);
    static final Register DX = new Register(REG_SZ);
    static final Register SI = new Register(REG_SZ);
    static final Register DI = new Register(REG_SZ);
    static final Register BP = new Register(REG_SZ);
    static final Register SP = new Register(REG_SZ);
    Register CS[] = new Register[SEG_SZ];
    Register DS[] = new Register[SEG_SZ];
    Register ES[] = new Register[SEG_SZ];
    Register SS[] = new Register[SEG_SZ];
    static final Register IP = new Register(REG_SZ);
    public static final Register FLAGS = new Register(REG_SZ);

    public InstructionSet_8086() {
        for (int i = 0; i < SEG_SZ; i++) {
            CS[i] = new Register(REG_SZ);
        }
        for (int i = 0; i < SEG_SZ; i++) {
            DS[i] = new Register(REG_SZ);
        }
        for (int i = 0; i < SEG_SZ; i++) {
            ES[i] = new Register(REG_SZ);
        }
        for (int i = 0; i < SEG_SZ; i++) {
            SS[i] = new Register(REG_SZ);
        }
        map.put("ADD", "ADD");
        map.put("MUL", "MUL");
        map.put("AND", "AND");
        map.put("DIV", "DIV");
    }

    /**
     * FLAG-BITS defined for the FLAG RGISTER.Flags Register - determines the
     * current state of the processor. They are modified automatically by CPU
     * after mathematical operations, this allows to determine the type of the
     * result, and to determine conditions to transfer control to other parts of
     * the program. Generally you cannot access these registers directly
     */
    public int CF = 0;//Carry Flag (CF)- this flag is set to 1 when there is an unsigned overflow. For example when you add bytes 255 + 1 (result is not in range 0...255). When there is no overflow this flag is set to 0.
    public int PF = 1;//Parity Flag (PF) - this flag is set to 1 when there is even number of one bits in result, and to 0 when there is odd number of one bits. 
    public int AF = 2;//Auxiliary Flag (AF) - set to 1 when there is an unsigned overflow for low nibble (4 bits).
    public int ZF = 3;//Zero Flag (ZF) - set to 1 when result is zero. For non-zero result this flag is set to 0.
    public int SF = 4;//Sign Flag (SF) - set to 1 when result is negative. When result is positive it is set to 0. (This flag takes the value of the most significant bit.)
    public int TF = 5;//Trap Flag (TF) - Used for on-chip debugging.
    public int IF = 6;//Interrupt enable Flag (IF) - when this flag is set to 1 CPU reacts to interrupts from external devices.
    public int DF = 7;//Direction Flag (DF) - this flag is used by some instructions to process data chains, when this flag is set to 0 - the processing is done forward, when this flag is set to 1 the processing is done backward.
    public int OF = 8;//Overflow Flag (OF) - set to 1 when there is a signed overflow. For example, when you add bytes 100 + 50 (result is not in range -128...127). 

    public void execute(String instr, String args) throws Exception {
        if (!map.containsKey(instr)) {
            throw new Exception("Instruction not vailable in the instruction set");
        }
        try {
            Method method = InstructionSet_8086.class.getMethod(map.get(instr), String.class);
            verboseln("instr : " + method.toString());
            method.invoke(this, args);
        } catch (NoSuchMethodException | SecurityException ex) {
            //verboseln(Arrays.toString(ex.getStackTrace()));
        }

    }

    public static final int REGISTER = 0;
    public static final int REGISTER_INDIRECT = 3;
    public static final int IMMEDIATE = 1;
    public static final int MEMORY = 2;

    @SuppressWarnings("Unchecked") // suppress the warnings for the Netbeans IDE using annotation
    public int getTypeOfOperand(String op) {
        if ("".equals(op)) {
            throw new Error("Undefined operand !!! The program is CORRUPT");
        }
        if (op.contains("][")) {
            op = op.replace("][", "+");
        }
        op = op.trim();
        if (op.startsWith("[") && op.endsWith("]") && op.contains("+")) {
            return REGISTER_INDIRECT;
        }

        if (op.startsWith("0x")) {
            try {
                Integer.parseInt(op.substring(2), REG_SZ);
            } catch (Exception ex) {
                throw new Error("Undefined operand !!! The program is CORRUPT");
            }
            return MEMORY;

        }
        try {
            Integer.parseInt(op);
        } catch (Exception ex) {
            return REGISTER;
        }
        return IMMEDIATE;

    }

    boolean signed = false;

    public byte[] getValue(String s) throws UnsupportedEncodingException {
        int l = getTypeOfOperand(s);
        verboseln("type of operand for " + s + " : " + l);

        if (l == MEMORY) {
            return fetchFromMemory(s);
        } else if (l == IMMEDIATE) {
            if (Integer.parseInt(s) < 0) {
                signed = true;
            }
            char c[] = s.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] >= '0' && c[i] <= '9') {
                    c[i] -= '0';
                }
            }
            s = new String(c);
            byte[] b = s.getBytes("US-ASCII");

            return b;
        } else if (l == REGISTER) {
            switch (s) {
                case "AX":
                    return AX.getValue();
                case "AL":
                    return AX.getLow();
                case "AH":
                    return AX.getHigh();
                case "BX":
                    return BX.getValue();
                case "CX":
                    return CX.getValue();
                case "DX":
                    return DX.getValue();
                case "SI":
                    return SI.getValue();
                case "DI":
                    return DI.getValue();
                case "CS":
                    return CS[0].getValue();
                case "DS":
                    return DS[0].getValue();
                case "ES":
                    return ES[0].getValue();
                case "SS":
                    return SS[0].getValue();
            }
        } else if (l == REGISTER_INDIRECT) {
            if (s != null && !s.contains("::")) {
                s = "DS::" + s;
            }
            String[] x = s.split("::");
            String[] y = x[1].split("\\+");
            BigInteger b = new BigInteger("0");
            for (String y1 : y) {
                BigInteger b1 = new BigInteger(getValue(y1.replace("[", "").replace("]", "")));
                b = b.add(b1);
            }
            return fetchRegisterIndirectValue(s, b.intValue());
        }
        return null;
    }

    public void ADD(String args) throws UnsupportedEncodingException {
        verboseln("Called Routine ADD");
        signed = false;
        String s[] = args.split(",");
        if (s.length != 2) {
            throw new Error("Corrupt Program");
        }
        verboseln("operands are " + Arrays.toString(s));

        if (getTypeOfOperand(s[0]) != REGISTER && getTypeOfOperand(s[0]) != MEMORY) {
            throw new Error("Operation not supported by Instruction set");
        }

        BigInteger op1 = new BigInteger(getValue(s[0]));
        BigInteger op2 = new BigInteger(getValue(s[1]));

        String prev = op2.toString();
        op2 = op1.add(op2);

        verboseln(op1 + " + " + prev + " = " + op2);
        byte[] b = op2.toByteArray();
        /*for (int i = 0; i < b.length; i++) {
            if (b[i] >= 0 && b[i] <= 9) {
                b[i] += (byte) '0';
            }
        }*/
        verboseln("before flag is calculated, ans : " + Arrays.toString(b));
        b = calculateFlags(b, signed);
        verboseln("after flag is calculated, ans : " + Arrays.toString(b));
        verboseln("ended routine ADD");
        AX.setValue(b);
    }

    public void MUL(String args) {
        signed = false;
        String s[] = args.split(",");
        if (s.length != 2) {
            throw new Error("Corrupt Program");
        }
        if (getTypeOfOperand(s[0]) != REGISTER && getTypeOfOperand(s[0]) != MEMORY) {
            throw new Error("Operation not supported by Instruction set");
        }
        BigInteger op1 = new BigInteger(s[0]);
        BigInteger op2 = new BigInteger(s[1]);
        op2 = op1.multiply(op2);
        byte[] b = op2.toByteArray();
        b = calculateFlags(b, signed);
        AX.setValue(b);
    }

    public void DIV(String args) {
        signed = false;
        String s[] = args.split(",");
        if (s.length != 2) {
            throw new Error("Corrupt Program");
        }
        if (getTypeOfOperand(s[0]) == IMMEDIATE) {
            throw new Error("Operation not supported by Instruction set");
        }
        if (getTypeOfOperand(s[0]) == REGISTER || getTypeOfOperand(s[0]) == MEMORY) {
            throw new Error("Operation not supported by Instruction set");
        }
        BigInteger op1 = new BigInteger(s[0]);
        BigInteger op2 = new BigInteger(s[1]);
        op2 = op1.divide(op2);
        byte[] b = op2.toByteArray();
        b = calculateFlags(b, signed);
        AX.setValue(b);
    }

    public void XOR(String args) {
        signed = false;
        String s[] = args.split(",");
        if (s.length != 2) {
            throw new Error("Corrupt Program");
        }
        if (getTypeOfOperand(s[0]) == IMMEDIATE) {
            throw new Error("Operation not supported by Instruction set");
        }
        if (getTypeOfOperand(s[0]) == REGISTER || getTypeOfOperand(s[0]) == MEMORY) {
            throw new Error("Operation not supported by Instruction set");
        }
        BigInteger op1 = new BigInteger(s[0]);
        BigInteger op2 = new BigInteger(s[1]);
        op2 = op1.xor(op2);
        byte[] b = op2.toByteArray();
        b = calculateFlags(b, signed);
        AX.setValue(b);
    }

    public void OR(String args) {
        signed = false;
        String s[] = args.split(",");
        if (s.length != 2) {
            throw new Error("Corrupt Program");
        }
        if (getTypeOfOperand(s[0]) == IMMEDIATE) {
            throw new Error("Operation not supported by Instruction set");
        }
        if (getTypeOfOperand(s[0]) == REGISTER || getTypeOfOperand(s[0]) == MEMORY) {
            throw new Error("Operation not supported by Instruction set");
        }
        BigInteger op1 = new BigInteger(s[0]);
        BigInteger op2 = new BigInteger(s[1]);
        op2 = op1.or(op2);
        byte[] b = op2.toByteArray();
        b = calculateFlags(b, signed);
        AX.setValue(b);
    }

    public void AND(String args) {
        signed = false;
        String s[] = args.split(",");
        if (s.length != 2) {
            throw new Error("Corrupt Program");
        }
        if (getTypeOfOperand(s[0]) == IMMEDIATE) {
            throw new Error("Operation not supported by Instruction set");
        }
        if (getTypeOfOperand(s[0]) == REGISTER || getTypeOfOperand(s[0]) == MEMORY) {
            throw new Error("Operation not supported by Instruction set");
        }
        BigInteger op1 = new BigInteger(s[0]);
        BigInteger op2 = new BigInteger(s[1]);
        op2 = op1.and(op2);
        byte[] b = op2.toByteArray();
        b = calculateFlags(b, signed);
        AX.setValue(b);
    }

    private byte[] fetchFromMemory(String x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public byte[] fetchRegisterIndirectValue(String reg, int index) {
        switch (reg) {
            case "CS":
                return CS[index].getValue();
            case "DS":
                return CS[index].getValue();
            case "ES":
                return CS[index].getValue();
            case "SS":
                return CS[index].getValue();
        }
        return null;

    }

    private byte[] calculateFlags(byte[] b, boolean flag) {
        boolean negative = false, zero = false;
        byte prev[] = FLAGS.getValue();
        if (prev == null) {
            prev = new byte[REG_SZ];
        }
        String string = new String(b);

        char c[] = string.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 0 && c[i] <= 9) {
                c[i] += '0';
            }
        }
        string = new String(c);
        if (string.startsWith("-")) {
            negative = true;
        }
        BigInteger bigInteger = new BigInteger(string);
        if (bigInteger.compareTo(BigInteger.ZERO) == 0) {
            zero = true;
        }
        boolean parity = calculateEvenParity(bigInteger);
        byte[] flags = Arrays.copyOf(prev, REG_SZ);
        if (negative) {
            flags[SF] = 1;
        }
        if (parity) {
            flags[PF] = 1;
        } else {
            flags[PF] = 0;
        }

        if (zero) {
            flags[ZF] = 1;
        } else {
            flags[ZF] = 0;
        }

        if (flag && b.length > AX.size) {
            flags[OF] = 1;
        } else {
            flags[OF] = 0;
        }

        if (!flag && b.length > AX.size) {
            flags[CF] = 1;
        } else {
            flags[CF] = 0;
        }

        FLAGS.setValue(flags);
        return Arrays.copyOfRange(b, Math.max(0, b.length - REG_SZ), b.length);
    }

    private boolean calculateEvenParity(BigInteger bigInteger) {
        int parity = 0;
        BigInteger two = new BigInteger("2");
        while (bigInteger.compareTo(BigInteger.ZERO) == 0) {
            if (bigInteger.mod(two).compareTo(BigInteger.ONE) == 0) {
                parity++;
            }
            bigInteger = bigInteger.divide(two);
        }
        return parity % 2 == 0;
    }

    private boolean verbosity = false;

    private void verboseln(String string) {
        if (verbosity) {
            System.out.println(string);
        }
    }

}
