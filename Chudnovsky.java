import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import com.google.common.math.BigIntegerMath;

public class Chudnovsky {

    private final MathContext MC;
    private final BigDecimal c;
    private BigDecimal sum;
    private int q;
    static private final BigInteger X_C = new BigInteger("-262537412640768000");

    public BigDecimal getSum() {
        return this.sum;
    }

    public int nextQ() {
        return q;
    }

    public BigDecimal getC() {
        return c;
    }

    public MathContext getMC() {
        return MC;
    }
    
    Chudnovsky(MathContext MC) {
        this.MC = MC;
        this.c = 
                BigDecimal.valueOf(426880)
            .multiply(
                BigDecimal.valueOf(10005).sqrt(MC)
            );
        this.sum = BigDecimal.ZERO;
        this.q = 0;
    }

    public void iterate() {
        this.sum = this.sum.add((new BigDecimal(M(this.q).multiply(L(this.q)))).divide(new BigDecimal(X(this.q)), this.MC));
        this.q++;
    }

    public void iterate(int iterations) {
        for (int i = 0; i < iterations; i++) this.iterate();
    }
    
    static BigInteger M(int q){
        return 
                BigIntegerMath.factorial(6*q)
            .divide(
                    BigIntegerMath.factorial(3*q)
                .multiply(
                    BigIntegerMath.factorial(q).pow(3)
                )
            );
    }

    static BigInteger L(int q){
        return 
                    BigInteger.valueOf(545140134)
                .multiply(
                    BigInteger.valueOf(q)
                )
            .add(
                BigInteger.valueOf(13591409)
            );
    }

    static BigInteger X(int q){
        return X_C.pow(q);
    }

    public BigDecimal getPI(){
        try {
            return this.getC().multiply((BigDecimal.ONE.divide(this.getSum(), this.MC)));
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return "Chudnovsky iterator:\n\t" + this.MC.toString() + "\n\t" + "q=" + (this.nextQ() - 1);
    }
}
