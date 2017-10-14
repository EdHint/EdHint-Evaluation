import sketch4j.executor.ExecutorType;
import sketch4j.executor.SketchExecutor;
import sketch4j.request.invocation.BatchInvocationRequest;

public class Main {

    private static BatchInvocationRequest request = new BatchInvocationRequest();



    public static org.apache.commons.math3.complex.Complex[] findRoots(org.apache.commons.math3.analysis.polynomials.PolynomialFunction arg0, double arg1) {
        return (org.apache.commons.math3.complex.Complex[]) request.newBatchInvocation(0)
                 .addDeclaringClassesFromPrefix("org.apache.commons.math3")
                 .addDeclaringClasses(org.apache.commons.math3.analysis.polynomials.PolynomialFunction.class, org.apache.commons.math3.complex.Complex[].class)
                 .addArgument(org.apache.commons.math3.analysis.polynomials.PolynomialFunction.class, "arg0", arg0)
                 .addArgument(double.class, "arg1", arg1)
                 .setReturnType(org.apache.commons.math3.complex.Complex[].class)
                 .setMaxSearchDepth(3)
                 .invoke();
    }


    public static boolean test() throws Throwable {
        double init = 0.0;
        final double[] coeff = { 1, -2, 1 }; 
    
        org.apache.commons.math3.analysis.polynomials.PolynomialFunction pf = new org.apache.commons.math3.analysis.polynomials.PolynomialFunction(coeff);
        org.apache.commons.math3.complex.Complex[] comp = findRoots(pf, init);
        boolean flag = ((comp.length == 2) && (comp[0].getReal() == 1.0) && (comp[0].getImaginary() == 0.0));
        
        if(flag) return true;
        else return false;
    }
     

    public static void main(String[] args) {
        int maxTime = Integer.parseInt(args[0]);
        SketchExecutor.setType(ExecutorType.JUZI);
        boolean found = false;
        boolean timeout = false;
        int i = 0;
        long begin = System.currentTimeMillis();
        long checkpoint = 0;
        do {
            if(System.currentTimeMillis() - begin >= maxTime * 1000) {
                timeout = true;
                break;
            }
            i ++;
            if(System.currentTimeMillis() - checkpoint >= 5000) {
                System.out.println(i + " executions tried!");
                checkpoint = System.currentTimeMillis();
            }
            request.reset();
            try {
                if(!test()) {
                    throw new RuntimeException("****TEST FAILURE");
                }
                found = true;
                break;
            } catch (Throwable e) {
               // e.printStackTrace();
            }
        } while (SketchExecutor.incrementCounter());
        if(found) {
            System.out.println("Found solution:");
            System.out.println("----------------------------------------------------------------");
            System.out.println(request.getCandidateBatchInvocation(0));
            System.out.println("----------------------------------------------------------------");
        } else if(timeout) {
            System.out.println("Timeout! Max execution time: " + maxTime + " seconds.");
        } else {
            System.out.println("No solution!");
        }
        
        System.out.println("Number of invocations tried: " + request.getNumBatchInvocationsTried(0));
        System.out.println("Search depth: " + request.getCurrentSearchDepth(0));
        System.out.println("Number of executions: " + i);
    }

}
